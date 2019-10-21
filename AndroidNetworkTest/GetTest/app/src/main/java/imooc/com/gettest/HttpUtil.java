package imooc.com.gettest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static String sendUrl(String mUrl){
        HttpURLConnection connection = null;
        InputStream input = null;
        BufferedReader br = null;
        StringBuilder output = null;
        try {
            URL url = new URL(mUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            input = url.openStream();
//            input = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
            output = new StringBuilder();
            String temp;
            while ((temp = br.readLine()) != null){
                output.append(temp);
            }
            br.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
