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
            System.out.println(Thread.currentThread().getName());
            System.out.println("打印" + content);
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
        String s = null;
        System.out.println("进入函数readFromClient()");
        try {
            InputStream isr = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(isr, "utf-8"));
            s = br.readLine();
            System.out.println(s);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            MyServer.sockets.remove(socket);
        }
        return null;
    }
}
