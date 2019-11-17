package demo.com.wechatui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import demo.com.wechatui.R;

public class SplashFragment extends Fragment {

    private ImageView mIvPicture;
    private int mResId;

    private static final String BUNDLE_KEY_RES_ID = "bundle_key_res_id";

    public static SplashFragment newInstance(int resId){
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_RES_ID, resId);
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            mResId = arguments.getInt(BUNDLE_KEY_RES_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIvPicture = view.findViewById(R.id.iv_picture);
        mIvPicture.setImageResource(mResId);

        mIvPicture.setTag(mResId);
    }


}
