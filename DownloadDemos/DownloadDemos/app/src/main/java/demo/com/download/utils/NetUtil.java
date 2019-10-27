package demo.com.download.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetUtil {
    public static InputStream getInputStream(String url){
        URL downloadUrl = null;
        HttpURLConnection conn = null;
        InputStream in = null;
        FileUtil fileUtil = new FileUtil();
        try {
            downloadUrl = new URL(url);
            conn = (HttpURLConnection) downloadUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Accept",
//                    "image/gif, image/jpeg, image/pjpeg, application/x-shockwave-flash" +
//                    "application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap" +
//                    "application/x-ms-application, application/vnd.ms-excel");
            in = conn.getInputStream();
            fileUtil.setFileSize(conn.getContentLength());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
            Log.d("NETexception","连接异常");
        }finally {
            try {
                conn.disconnect();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return in;
    }
}
