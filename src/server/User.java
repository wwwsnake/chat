package server;
//ДЗ: решить и обосновать какие гет и сет нужны этому глассу, сделать чтобы сообщение не получал отправитель
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class User {
    private String name;
    private UUID uuid; //создает id пользователя
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream out;

    public User(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new DataOutputStream(socket.getOutputStream());
        this.is = new DataInputStream(socket.getInputStream());
        this.uuid = UUID.randomUUID();//генерирует уникальный id каждому пользователю
    }
}


