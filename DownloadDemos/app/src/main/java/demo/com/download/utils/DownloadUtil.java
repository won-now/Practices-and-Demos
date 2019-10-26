package demo.com.download.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class DownloadUtil {
    private int downThreadNum;
    private String url;
    private String name;
    private DownLoadThread[] threads;
    private int threadsNum;

    private static final String dirName =  Environment.getExternalStorageState()
            + File.separator + "DownloadFiles";

    public DownloadUtil(int downThreadNum, String url, String name) {
        this.downThreadNum = downThreadNum;
        this.url = url;
        this.name = dirName + File.separator + name;
    }

    public void download(){
        try {
            File dir = new File(dirName);
            if(!dir.exists()){
                if(!dir.mkdir())
                    Log.d("DownloadUtil","目录创建失败");
            }
            RandomAccessFile file = new RandomAccessFile(name, "rw");
            FileUtil fileUtil = new FileUtil();

            int partSize = fileUtil.getFileSize() / threadsNum;

            for(int i = 0; i < threadsNum; i++){
                //ArrayList
                HashMap
            }

            InputStream inputStream = NetUtil.getInputStream(url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private class DownLoadThread extends Thread {
        int startPos;


        @Override
        public void run() {
            super.run();
        }
    }
}
