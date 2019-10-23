package demo.com.wechatui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import demo.com.wechatui.fragment.TabFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager mVpMain;
    private List<String> mTitles =
            new ArrayList<>(Arrays.asList("微信","通讯录","发现","我"));

    private Button mBtnWechat;
    private Button mBtnContacts;
    private Button mBtnDisover;
    private Button mBtnMe;

    private SparseArray<TabFragment> mFragments = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        initViewPagerAdapter();
    }

    private void initViewPagerAdapter() {
        mVpMain.setOffscreenPageLimit(mTitles.size());
        mVpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), 1) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                TabFragment fragment = TabFragment.newInstance(mTitles.get(position));

                if(position == 0){
                    fragment.setOnTitleClickListener(new TabFragment.OnTitleClickListener() {
                        @Override
                        public void onClick(String title) {
                            changeWeChatTab(title);
                        }
                    });
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TabFragment fragment = (TabFragment) super.instantiateItem(container, position);
                mFragments.put(position, fragment);
                return fragment;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                mFragments.remove(position);
                super.destroyItem(container, position, object);
            }
        });

        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews() {
        mVpMain = findViewById(R.id.vp_main);
        mBtnWechat = findViewById(R.id.btn_wechat);
        mBtnContacts = findViewById(R.id.btn_contacts);
        mBtnDisover = findViewById(R.id.btn_discover);
        mBtnMe = findViewById(R.id.btn_me);

        mBtnWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabFragment tabFragment = mFragments.get(0);
                if(tabFragment != null){
                    tabFragment.changeTitle("微信 changed!");
                }
            }
        });
    }

    public void changeWeChatTab(String title){
        mBtnWechat.setText(title);
    }
}
