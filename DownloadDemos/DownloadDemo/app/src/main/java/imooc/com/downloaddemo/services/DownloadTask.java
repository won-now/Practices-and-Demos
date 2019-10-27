package imooc.com.downloaddemo.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import imooc.com.downloaddemo.db.ThreadDAO;
import imooc.com.downloaddemo.db.ThreadDAOImpl;
import imooc.com.downloaddemo.entities.FileInofo;
import imooc.com.downloaddemo.entities.ThreadInfo;

public class DownloadTask {

    private Context mContext;
    private FileInofo mFileInfo;
    private ThreadDAO mDao;
    private long mFinished = 0;
    public boolean isPause = false;

    public DownloadTask(Context context, FileInofo mFileInfo) {
        this.mContext = context;
        this.mFileInfo = mFileInfo;
        mDao = new ThreadDAOImpl(context);
    }

    public void download(){
        List<ThreadInfo> threadInfos = mDao.getThreads(mFileInfo.getUrl());
        ThreadInfo threadInfo = null;
        if(threadInfos.size() == 0){
            threadInfo = new ThreadInfo(0, mFileInfo.getUrl(), 0, mFileInfo.getLength(), 0 );
//            mDao.insertThread(threadInfo);
        }else {
            threadInfo = threadInfos.get(0);
            Log.d("threadInfo","11111111111");
        }
        new DownloadThread(threadInfo).start();
    }

    class DownloadThread extends Thread{
        private ThreadInfo mThreadInfo;

        public DownloadThread(ThreadInfo mThreadInfo) {
            this.mThreadInfo = mThreadInfo;
        }

        @Override
        public void run() {
            if(!mDao.isExists(mFileInfo.getUrl(),mThreadInfo.getId())){
                mDao.insertThread(mThreadInfo);
            }

            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            BufferedInputStream buffer = null;

            try {
                URL url = new URL(mFileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                long start = mThreadInfo.getStart() + mThreadInfo.getFinished();
                conn.setRequestProperty("Range","bytes=" + start + "-" + mThreadInfo.getEnd());

                File file = new File(DownloadService.DOWNLOAD_PATH, mFileInfo.getFileName());
                raf = new RandomAccessFile(file,"rwd");
                raf.seek(start);

                Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                mFinished += mThreadInfo.getFinished();
                Log.d("intent", mFinished+"");

                if(conn.getResponseCode() == 206){
                    input = conn.getInputStream();
                    buffer = new BufferedInputStream(input,10000);
                    byte[] bytes = new byte[1024 * 4];
                    int len = 0;
                    long currentTime = System.currentTimeMillis();
                    while ((len = buffer.read(bytes)) != -1){
                        raf.write(bytes, 0 , len);
                        mFinished += len;
                        if(System.currentTimeMillis() - currentTime > 500){
                            currentTime = System.currentTimeMillis();
                            intent.putExtra("finished", (int) (mFinished * 100 / mFileInfo.getLength()));
//                            Log.d("intent", mFinished+"");
                            mContext.sendBroadcast(intent);
                        }
                        if(isPause){
                            mDao.updateThread(mThreadInfo.getUrl(), mThreadInfo.getId(), mFinished);
//                            mFileInfo.setFinished(mFinished);
                            return;
                        }
                    }
                    if (mFileInfo.getLength() == mFinished){
                        mDao.deleteThread(mThreadInfo.getUrl(), mThreadInfo.getId());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    conn.disconnect();
                    raf.close();
                    buffer.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
