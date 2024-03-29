package imooc.com.downloaddemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import imooc.com.downloaddemo.entities.FileInofo;

public class DownloadService extends Service {

    public static final String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/downloads/";
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_FINISHED = "ACTION_FINISHED";
    public static final int MSG_INIT = 0;

    private InitThread mInitThread;
    private Map<Integer,DownloadTask> mTasks = new LinkedHashMap<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(ACTION_START.equals(intent.getAction())){
            FileInofo fileInofo = (FileInofo) intent.getSerializableExtra("fileInfo");
            mInitThread =  new InitThread(fileInofo);
//            Log.i("test","Start:" + fileInofo.toString());
            DownloadTask.sExecutorService.execute(mInitThread);
        }else if(ACTION_STOP.equals(intent.getAction())){
            FileInofo fileInofo = (FileInofo) intent.getSerializableExtra("fileInfo");
//            Log.i("test","Stop:" + fileInofo.toString());
            DownloadTask task = mTasks.get(fileInofo.getId());

            if(task != null){
                task.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case MSG_INIT:
                    FileInofo fileInofo = (FileInofo) msg.obj;
                    Log.i("test","Init:" + fileInofo);
                    DownloadTask task = new DownloadTask(DownloadService.this, fileInofo,3);
                    mTasks.put(fileInofo.getId(), task);
                    task.download();

                    break;
            }
        }
    };

    /**
     * 初始化子线程
     */
    class InitThread extends Thread{
        private FileInofo mFileInfo = null;

        public InitThread(FileInofo mFileInfo) {
            this.mFileInfo = mFileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                URL url = new URL(mFileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                int length = -1;
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    length = conn.getContentLength();
                }
                if (length <= 0){
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if(!dir.exists()){
                    dir.mkdir();
                }
                File file = new File(dir,mFileInfo.getFileName());
                raf = new RandomAccessFile(file,"rwd");
                raf.setLength(length);
                mFileInfo.setLength(length);
                mHandler.obtainMessage(MSG_INIT,mFileInfo).sendToTarget();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    if(raf != null)
                        raf.close();
                    if(conn != null)
                        conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
