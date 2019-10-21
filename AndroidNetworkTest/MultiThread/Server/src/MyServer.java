import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MyServer {

    public static ArrayList<Socket> sockets = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(38888);
            while (true){
                Socket skt = ss.accept();
                sockets.add(skt);
                new Thread(new ServerThread(skt)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
