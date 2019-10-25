package demo.com.wechatui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private ViewPager mVpShow;

    private int[] mResIds = new int[]{
            R.drawable.lu,
            R.drawable.shizi,
            R.drawable.ying
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mVpShow = findViewById(R.id.vp_show);
    }
}
