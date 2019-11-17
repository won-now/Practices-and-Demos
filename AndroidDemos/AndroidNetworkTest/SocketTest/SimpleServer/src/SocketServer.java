import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(38888);
            while (true){
                System.out.println("程序运行中...");
                Socket s = ss.accept();
                OutputStream outputStream = s.getOutputStream();
                String string = "你成功连接到服务端！";
                outputStream.write(string.getBytes("utf-8"));
                outputStream.close();
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
