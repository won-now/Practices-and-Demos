package test.com.myclient;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread implements Runnable {
    private BufferedReader br;
    private OutputStream os;
    private Handler showHandler;
    Handler sendHandler;

    public ClientThread(Handler showHandler) {
        this.showHandler = showHandler;
    }

    @Override
    public void run() {
        Log.d("--------Thread------",Thread.currentThread().getName());
        Socket socket = null;
        try {
            socket = new Socket("192.168.2.106",38888);
            InputStream inputStream = socket.getInputStream();
            br = new BufferedReader
                                (new InputStreamReader(inputStream, "utf-8"));
            new Thread(){
                @Override
                public void run() {
                    String content = null;
                    try {
                        while ((content = br.readLine()) != null){
                            Message msg = new Message();
                            msg.what = 0x123;
                            msg.obj = content;
                            Log.d("Content",content);
                            showHandler.sendMessage(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            os = socket.getOutputStream();
            Looper.prepare();
            sendHandler = new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if(msg.what == 0x345){
                        try {
                            String text = msg.obj.toString();
                            Log.d("TEXT",text);
                            os.write((text + "\r\n")
                              .getBytes("utf-8"));
                            os.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Looper.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
