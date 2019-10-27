package demo.com.download;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mEtUrl, mEtPath;
    private Button mBtnStart, mBtnDownloadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mEtUrl = findViewById(R.id.et_url);
        mEtPath = findViewById(R.id.et_path);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnDownloadList = findViewById(R.id.btn_downloadinglist);
    }
}
