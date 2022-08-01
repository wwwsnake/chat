package server;
//ДЗ: решить и обосновать какие гет и сет нужны этому классу, сделать чтобы сообщение не получал отправитель
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class User {
    private String name;
    private UUID uuid; //создает id пользователя

    public DataOutputStream getOut() {
        return out;
    }

    private Socket socket;
    private DataInputStream is;
    private DataOutputStream out;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Socket getSocket() {
        return socket;
    }


    public DataInputStream getIs() {
        return is;
    }






    public User(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new DataOutputStream(socket.getOutputStream());
        this.is = new DataInputStream(socket.getInputStream());
        this.uuid = UUID.randomUUID();//генерирует уникальный id каждому пользователю
    }
}


