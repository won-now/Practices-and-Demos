import java.io.*;
import java.net.Socket;
import java.util.Iterator;

public class ServerThread implements Runnable {

    Socket socket;

    public ServerThread(Socket s){
        socket = s;
    }

    @Override
    public void run() {
        String content = null;
        while ((content = readFromClient()) != null){
            for (Iterator<Socket> it = MyServer.sockets.iterator();
                 it.hasNext(); ){
                Socket s = it.next();
                OutputStream out = null;
                try {
                    out = s.getOutputStream();
                    out.write((content + "\n").getBytes("utf-8"));
                }catch (IOException e) {
                    e.printStackTrace();
                    it.remove();
                    System.out.println(MyServer.sockets);
                }
            }
        }
    }


    private String readFromClient(){
        BufferedReader br = null;
        try {
            InputStream isr = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(isr, "utf-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            MyServer.sockets.remove(socket);
        }
        return null;
    }
}
