package demo.com.wechatui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import demo.com.wechatui.Transformer.ScaleTransFormer;
import demo.com.wechatui.fragment.SplashFragment;

public class BannerActivity extends AppCompatActivity {

    private ViewPager mVpShow;

    private int[] mResIds = new int[]{
            R.drawable.lu,
            R.drawable.shizi,
            R.drawable.ying
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        mVpShow = findViewById(R.id.vp_show);
        mVpShow.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager(), 1) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return SplashFragment.newInstance(mResIds[position]);
            }

            @Override
            public int getCount() {
                return mResIds.length;
            }
        });

        mVpShow.setPageTransformer(true, new ScaleTransFormer());
    }
}
