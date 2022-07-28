package client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9446); // подключение к серверу (вместо local host можно текущий ip компа или 127.0.0.1)
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());//"поток исходящих сообщений"
            DataInputStream is = new DataInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String response = null;
                    try {
                        while (true) {
                            response = is.readUTF();//сохраняем ответ в переменную
                            System.out.println(response);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
            thread.start();
            while (true) {
                String message = scanner.nextLine();
                out.writeUTF(message);//в кодировке UTF
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
