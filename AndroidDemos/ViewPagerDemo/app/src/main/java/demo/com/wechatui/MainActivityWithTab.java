package demo.com.wechatui;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import demo.com.wechatui.Views.TabView;
import demo.com.wechatui.fragment.TabFragment;

public class MainActivityWithTab extends AppCompatActivity {

    private ViewPager mVpMain;
    private List<String> mTitles =
            new ArrayList<>(Arrays.asList("微信","通讯录","发现","我"));

    private TabView mTabWechat;
    private TabView mTabContacts;
    private TabView mTabDisover;
    private TabView mTabMe;

    private List<TabView> mTabs = new ArrayList<>();

    private SparseArray<TabFragment> mFragments = new SparseArray<>();

    private static final String BUNDLE_KEYS_POS = "bundle_keys_pos";
    private int mCurTabPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        if(savedInstanceState != null){
            mCurTabPos = savedInstanceState.getInt(BUNDLE_KEYS_POS, 0);
        }

        initViews();

        initViewPagerAdapter();

        initEvents();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(BUNDLE_KEYS_POS, mVpMain.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    private void initEvents() {
        for(int i = 0; i < mTabs.size(); i++){
            final TabView tabView = mTabs.get(i);
            final int finalI = i;
            tabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVpMain.setCurrentItem(finalI, false);
                    setCurrentTab(finalI);
                }
            });
        }
    }

    private void initViewPagerAdapter() {
        mVpMain.setOffscreenPageLimit(mTitles.size());
        mVpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), 1) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                TabFragment fragment = TabFragment.newInstance(mTitles.get(position));

//                if(position == 0){
//                    fragment.setOnTitleClickListener(new TabFragment.OnTitleClickListener() {
//                        @Override
//                        public void onClick(String title) {
//                            changeWeChatTab(title);
//                        }
//                    });
//                }
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
                    //left -> right   offset:0->1
                    //right -> left   offset:1->0
                //left: progress 0~1 right progress:1~0
//                Log.d("onPageScrolled","position:" + position + " offset:" + positionOffset);
                if(positionOffset > 0){
                    TabView left = mTabs.get(position);
                    TabView right = mTabs.get(position + 1);
                    left.setProgress(1-positionOffset);
                    right.setProgress(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
//                Log.d("onPageSelected","position:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //state 0:IDLE 1:DRAGGING 2:SETTLING 0
//                Log.d("onPageScrollStateChange","state: " + state);
            }
        });
    }

    private void initViews() {
        mVpMain = findViewById(R.id.vp_main);
        mTabWechat = findViewById(R.id.tab_wechat);
        mTabContacts = findViewById(R.id.tab_contacts);
        mTabDisover = findViewById(R.id.tab_discover);
        mTabMe = findViewById(R.id.tab_me);

        mTabWechat.setIconAndText(R.drawable.wechat1,R.drawable.wechat2,"微信");
        mTabContacts.setIconAndText(R.drawable.contact1,R.drawable.contact2,"通讯录");
        mTabDisover.setIconAndText(R.drawable.discover1,R.drawable.discover2,"发现");
        mTabMe.setIconAndText(R.drawable.me1,R.drawable.me2,"我");

        mTabs.add(mTabWechat);
        mTabs.add(mTabContacts);
        mTabs.add(mTabDisover);
        mTabs.add(mTabMe);

//        mTabWechat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TabFragment tabFragment = mFragments.get(0);
//                if(tabFragment != null){
//                    tabFragment.changeTitle("微信 changed!");
//                }
//            }
//        });
        setCurrentTab(mCurTabPos);
    }

    public void setCurrentTab(int pos){
        for(int i = 0; i < mTabs.size(); i++){
            if(pos == i){
                mTabs.get(i).setProgress(1);
            }else {
                mTabs.get(i).setProgress(0);
            }
        }
    }
}
