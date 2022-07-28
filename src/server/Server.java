package server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
public class Server {

    public static void main(String[] args) {
        ArrayList<Socket> sockets = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(9446); //можно в википедии посмотреть какие порты не заняты
            System.out.println("сервер запущен:");
            while (true) { //бесконечный цикл чтобы сервер не отключался и ждал подключения клиентов
                Socket socket = serverSocket.accept();//то же что и scanner.nextLine
                User user = new User(socket);
                sockets.add(socket);
                System.out.println("клиент подключился"); //в командной строке пишем telnet и айпишник в одной сети с вами и сработает эта строка кода
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DataInputStream is = new DataInputStream(socket.getInputStream());
                            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                            out.writeUTF("Введите имя:");
                            user.setName(is.readUTF());
                            while (true) {
                                String message = is.readUTF();
                                for (Socket s : sockets) {
                                    DataOutputStream out1 = new DataOutputStream(s.getOutputStream());
                                    if (s == user.getSocket()){
                                        continue;
                                    }
                                    out1.writeUTF(user.getName() + "#" + user.getUuid() + " сообщает: " + message);
                                }
                                System.out.println(message);
                            }
                        } catch (IOException e) {
                            sockets.remove(socket);
                            System.out.println(user.getName() + "#" + user.getUuid() + " вышел из чата");
                            for (Socket s : sockets) {
                                try {
                                    DataOutputStream out1 = new DataOutputStream(s.getOutputStream());
                                    out1.writeUTF(user.getName() + "#" + user.getUuid() + " вышел из чата");
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
}

