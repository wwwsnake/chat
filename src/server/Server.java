package server;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9445); //можно в википедии посмотреть какие порты не заняты
            System.out.println("сервер запущен:");
            serverSocket.accept(); //то же что и scanner.nextLine
            System.out.println("клиент подключился"); //в командной строке пишем telnet и айпишник в одной сети с вами и сработает эта строка кода
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
