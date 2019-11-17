package test.com.myclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private EditText mEtContent;
    private Button mBtnSend;
    private TextView mTvShow;
    private ClientThread clientThread;

    static class ShowHandler extends Handler{

        private WeakReference<MainActivity> mainActivity;

        public ShowHandler(WeakReference<MainActivity> mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 0x123){
                mainActivity.get().mTvShow.append("\n" + msg.obj.toString());
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtContent = findViewById(R.id.et_content);
        mBtnSend = findViewById(R.id.btn_send);
        mTvShow = findViewById(R.id.tv_show);

        ShowHandler showHandler = new ShowHandler(new WeakReference<MainActivity>(this));
        clientThread = new ClientThread(showHandler);
        new Thread(clientThread).start();
        Log.d("--------Thread------",Thread.currentThread().getName());
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("--------Thread------",Thread.currentThread().getName());
                Message msg = new Message();
                msg.what = 0x345;
                msg.obj = mEtContent.getText().toString();
                clientThread.sendHandler.sendMessage(msg);
                mEtContent.setText("");
            }
        });
    }
}
