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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import imooc.com.downloaddemo.db.ThreadDAO;
import imooc.com.downloaddemo.db.ThreadDAOImpl;
import imooc.com.downloaddemo.entities.FileInofo;
import imooc.com.downloaddemo.entities.ThreadInfo;

public class DownloadTask {

    private Context mContext;
    private FileInofo mFileInfo;
    private ThreadDAO mDao;
    private long mFinished = 0;
    private int mThreadCount = 1;
    private List<DownloadThread> mThreadList;
    public static ExecutorService sExecutorService =
                                Executors.newCachedThreadPool();

    public boolean isPause = false;


    public DownloadTask(Context context, FileInofo mFileInfo, int threadCount) {
        this.mContext = context;
        this.mFileInfo = mFileInfo;
        this.mThreadCount = threadCount;
        mDao = new ThreadDAOImpl(context);
    }

    public void download(){
        List<ThreadInfo> threads = mDao.getThreads(mFileInfo.getUrl());
        int partSize = (int) (mFileInfo.getLength() / mThreadCount);
        if(threads.size() == 0){
            ThreadInfo threadInfo = null;
            for(int i = 0; i < mThreadCount; i++){
                 threadInfo = new ThreadInfo(0,mFileInfo.getUrl(),
                         i * partSize, (i+1) * partSize - 1, 0);
                if(i == mThreadCount - 1){
                    threadInfo.setEnd(mFileInfo.getLength());
                }
                threads.add(threadInfo);
                mDao.insertThread(threadInfo);
            }
        }

        mThreadList = new ArrayList<>();
        for(ThreadInfo info : threads){
            DownloadThread thread = new DownloadThread(info);
//            thread.start();
            DownloadTask.sExecutorService.execute(thread);
            mThreadList.add(thread);
        }
//        ThreadInfo threadInfo = null;
//        if(threadInfos.size() == 0){
//            threadInfo = new ThreadInfo(0, mFileInfo.getUrl(), 0, mFileInfo.getLength(), 0 );
////            mDao.insertThread(threadInfo);
//        }else {
//            threadInfo = threadInfos.get(0);
//            Log.d("threadInfo","11111111111");
//        }
//        new DownloadThread(threadInfo).start();
    }

    private synchronized void checkAllThreadsFinished(){
        boolean allFinished = true;
        for(DownloadThread thread : mThreadList){
            if(!thread.isFinished){
                allFinished = false;
                break;
            }
        }
        if(allFinished){
            mDao.deleteThread(mFileInfo.getUrl());
            Intent intent = new Intent(DownloadService.ACTION_FINISHED);
            intent.putExtra("fileInfo",mFileInfo);
            mContext.sendBroadcast(intent);
        }
    }

    class DownloadThread extends Thread{

        private ThreadInfo mThreadInfo;
        public boolean isFinished = false;

        public DownloadThread(ThreadInfo mThreadInfo) {
            this.mThreadInfo = mThreadInfo;
        }

        @Override
        public void run() {
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
                        mThreadInfo.setFinished(mThreadInfo.getFinished() + len);
                        if(System.currentTimeMillis() - currentTime > 1000){
                            currentTime = System.currentTimeMillis();
                            intent.putExtra("finished", (int)(mFinished * 100 / mFileInfo.getLength()));
                            intent.putExtra("id",mFileInfo.getId());
//                            Log.d("intent", mFinished+"");
                            mContext.sendBroadcast(intent);
                        }
                        if(isPause){
                            mDao.updateThread(mThreadInfo.getUrl(), mThreadInfo.getId(), mFinished);
//                            mFileInfo.setFinished(mFinished);
                            return;
                        }
                    }
                    isFinished = true;
                    Log.d("DELETE THREAD","delete!!!");
                    checkAllThreadsFinished();
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    buffer.close();
                    input.close();
                    raf.close();
                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
