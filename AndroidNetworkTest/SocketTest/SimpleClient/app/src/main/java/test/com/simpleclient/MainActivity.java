package test.com.simpleclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    TextView mTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvShow = findViewById(R.id.tv_show);
        new Thread(){
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("10.200.27.68",38888);
                    socket.setSoTimeout(10000);
                    InputStream inputStream = socket.getInputStream();
                    byte[] bytes = new byte[100];
                    int len = inputStream.read(bytes);
                    String s = new String(bytes, 0, len,"utf-8");
                    mTvShow.setText("连接成功: " + s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
