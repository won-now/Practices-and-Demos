package test.com.myclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Socket socket = new Socket("100.200.27.68",38888);
    }
}
