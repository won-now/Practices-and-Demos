package imooc.com.gettest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    WebView mWebView;
    SendUrlTask mSendUrlTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = findViewById(R.id.webView);
        mSendUrlTask = new SendUrlTask("https://www.baidu.com");
        mWebView.loadUrl("https://cn.bing.com");
//        mSendUrlTask.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSendUrlTask != null){
            mSendUrlTask.cancel(true);
        }
    }

    private class  SendUrlTask extends AsyncTask<Void,Void,String>{
        String mUrl;

        public SendUrlTask(String url){
            mUrl = url;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return HttpUtil.sendUrl(mUrl);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mWebView.loadData(s,"text/html;charset=utf-8",null);
        }
    }
}


