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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import imooc.com.downloaddemo.adapter.FileListAdapter;
import imooc.com.downloaddemo.entities.FileInofo;
import imooc.com.downloaddemo.services.DownloadService;

public class MainActivity extends AppCompatActivity {

    private ListView mLvFile;
    private List<FileInofo> mFileList;
    private FileListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mBtStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, DownloadService.class);
//                intent.setAction(DownloadService.ACTION_START);
//                intent.putExtra("fileInfo",mFileInfo);
//                startService(intent);
//            }
//        });
//
//        mBtStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, DownloadService.class);
//                    intent.setAction(DownloadService.ACTION_STOP);
//                    intent.putExtra("fileInfo",mFileInfo);
//                    startService(intent);
//            }
//        });

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        initial();
    }

    private void initial() {
        mLvFile = findViewById(R.id.lv_file);

        mFileList = new ArrayList<>();

        FileInofo mFileInfo = new FileInofo(0,"http://mirror.yandex.ru/mirrors/ftp.videolan.org/x264/snapshots/last_x264.tar.bz2" ,
                "last_x264.tar.bz2",0,0);
        FileInofo mFileInfo1 = new FileInofo(1,"https://www.easyicon.net/download/png/1197675/102/" ,
                "Browser_Icon_102px_1197675_easyicon.net.png",0,0);
        FileInofo mFileInfo2 = new FileInofo(2,"https://android-apps.pp.cn/fs08/2019/09/29/4/120_8a3a961eea77c82f55722043ff5c1e65.apk?yingid=web_space&packageid=600841890&md5=29272040830f7b91d594565414d52ccb&minSDK=17&size=37995906&shortMd5=075d5ccce1252a5654ed6b5d87e47ecc&crc32=3023274333&did=043006dee591081d790af39bca5a163f" ,
                "tengxunyingyin.apk",0,0);
        FileInofo mFileInfo3 = new FileInofo(3,"https://oss.ucdl.pp.uc.cn/fs01/union_pack/Wandoujia_1705498_web_direct_static_brand.apk?x-oss-process=udf%2Fpp-udf%2CJjc3LiMnJ3V%2Fd3V0cA%3D%3D" ,
                "wandoujia.apk",0,0);

        mFileList.add(mFileInfo);
        mFileList.add(mFileInfo1);
        mFileList.add(mFileInfo2);
        mFileList.add(mFileInfo3);

        mAdapter = new FileListAdapter(this, mFileList);
        mLvFile.setAdapter(mAdapter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        filter.addAction(DownloadService.ACTION_FINISHED);
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
                int id = intent.getIntExtra("id",0);
                mAdapter.updateProgress(id, progress);
            }else if(DownloadService.ACTION_FINISHED.equals(intent.getAction())){
                FileInofo fileInofo = (FileInofo) intent.getSerializableExtra("fileInfo");

                mAdapter.updateProgress(fileInofo.getId(),0);
                Toast.makeText(MainActivity.this,
                        mFileList.get(fileInofo.getId()).getFileName() + "下载完毕",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
}
