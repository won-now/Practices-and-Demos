package imooc.com.downloaddemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import imooc.com.downloaddemo.entities.FileInofo;
import imooc.com.downloaddemo.services.DownloadService;

public class MainActivity extends AppCompatActivity {

    private TextView mTvFileName;
    private ProgressBar mPbProgress;
    private Button mBtStop,mBtStart;

    private FileInofo mFileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvFileName = findViewById(R.id.tvFileName);
        mPbProgress = findViewById(R.id.pbProgress);
        mBtStart = findViewById(R.id.btStart);
        mBtStop = findViewById(R.id.btStop);

        mFileInfo = new FileInofo(0,"http://mirror.yandex.ru/mirrors/ftp.videolan.org/x264/snapshots/last_x264.tar.bz2" ,
                "last_x264.tar.bz2",0,0);

        mPbProgress.setMax(100);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        mBtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ThreadDAO mDao = new ThreadDAOImpl(MainActivity.this);
//                List<ThreadInfo> threadInfos = mDao.getThreads(mFileInfo.getUrl());
//                ThreadInfo threadInfo = null;
//                if(threadInfos.size() == 0){
//                    mPbProgress.setProgress(0);
//                }else {
//                    threadInfo = threadInfos.get(0);
//                    mPbProgress.setProgress((int) threadInfo.getFinished());
//                }
//                int progress = (int) (mFileInofo.getFinished() * 100 / mFileInofo.getLength());
//                mPbProgress.setProgress(progress);
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
//                if(DownloadService.ACTION_START.equals(intent.getAction())){
//                    return;
//                }
                intent.setAction(DownloadService.ACTION_START);
                intent.putExtra("fileInfo",mFileInfo);
                startService(intent);
            }
        });

        mBtStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
               // if(DownloadService.ACTION_START.equals(intent.getAction())){
                    intent.setAction(DownloadService.ACTION_STOP);
                    intent.putExtra("fileInfo",mFileInfo);
                    startService(intent);
                //}
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(DownloadService.ACTION_UPDATE.equals(intent.getAction())){
                int progress = intent.getIntExtra("finished", 0);
                mPbProgress.setProgress(progress);
            }
        }
    };
}
