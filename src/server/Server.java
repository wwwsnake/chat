package server;

//ДЗ: получить список онлайн пользователей и вывести

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9446); //можно в википедии посмотреть какие порты не заняты
            System.out.println("сервер запущен:");
            while (true) { //бесконечный цикл чтобы сервер не отключался и ждал подключения клиентов
                Socket socket = serverSocket.accept();//то же что и scanner.nextLine
                User currentUser = new User(socket);
                users.add(currentUser);
                System.out.println("клиент подключился"); //в командной строке пишем telnet и айпишник в одной сети с вами и сработает эта строка кода
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            currentUser.getOut().writeUTF("Введите имя:");
                            currentUser.setName(currentUser.getIs().readUTF());
                            sendOnlineUsers();
                            while (true) {
                                String message = currentUser.getIs().readUTF();
                                for (User user : users) {
                                    if (!currentUser.getUuid().toString().equals(user.getUuid().toString())) {
                                        user.getOut().writeUTF(currentUser.getName() + "#" + currentUser.getUuid() + " сообщает: " + message);
                                    }
                                }
                                System.out.println(message);
                            }
                        } catch (IOException e) {
                            users.remove(currentUser);
                            System.out.println(currentUser.getName() + "#" + currentUser.getUuid() + " вышел из чата");
                            try {
                                sendOnlineUsers();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            for (User user : users) {
                                try {
                                    user.getOut().writeUTF(currentUser.getName() + "#" + currentUser.getUuid() + " вышел из чата");
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void sendOnlineUsers() throws IOException {
        StringBuilder onlineUserList = new StringBuilder();
        for (User user : users) {
            onlineUserList.append("users//" + user.getName() + "\n");
        }
        for (User user : users) {
            user.getOut().writeUTF(onlineUserList.toString());
        }
    }
}

