package demo.com.wechatui.Views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import demo.com.wechatui.R;

public class TabView extends FrameLayout {

    private ImageView mIvIcon, mIvIconSelected;
    private TextView mTvTtile;

    private static final int COLOR_DEFAULT = Color.parseColor("#ff000000");
    private static final int COLOR_SELECTED = Color.parseColor("#ff1afa29");


    public TabView(Context context) {
        super(context,null);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.tab_view,this);

        mIvIcon = findViewById(R.id.iv_icon);
        mIvIconSelected = findViewById(R.id.iv_icon_selected);
        mTvTtile = findViewById(R.id.tv_title);

        setProgress(0);
    }

    public void setIconAndText(int icon, int iconSelected, String title){
        mIvIcon.setImageResource(icon);
        mIvIconSelected.setImageResource(iconSelected);
        mTvTtile.setText(title);

    }

    public void setProgress(float progress){
        mIvIcon.setAlpha(1 - progress);
        mIvIconSelected.setAlpha(progress);

        int titleColor = evaluate(progress,COLOR_DEFAULT,COLOR_SELECTED);
        mTvTtile.setTextColor(titleColor);
    }

    public int evaluate(float fraction, int startValue, int endValue) {
        int startInt = startValue;
        float startA = ((startInt >> 24) & 0xff) / 255.0f;
        float startR = ((startInt >> 16) & 0xff) / 255.0f;
        float startG = ((startInt >> 8) & 0xff) / 255.0f;
        float startB = ((startInt) & 0xff) / 255.0f;

        int endInt = endValue;
        float endA = ((endInt >> 24) & 0xff) / 255.0f;
        float endR = ((endInt >> 16) & 0xff) / 255.0f;
        float endG = ((endInt >> 8) & 0xff) / 255.0f;
        float endB = ((endInt) & 0xff) / 255.0f;

        // convert from sRGB to linear
        startR = (float) Math.pow(startR, 2.2);
        startG = (float) Math.pow(startG, 2.2);
        startB = (float) Math.pow(startB, 2.2);

        endR = (float) Math.pow(endR, 2.2);
        endG = (float) Math.pow(endG, 2.2);
        endB = (float) Math.pow(endB, 2.2);

        // compute the interpolated color in linear space
        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);

        // convert back to sRGB in the [0..255] range
        a = a * 255.0f;
        r = (float) Math.pow(r, 1.0 / 2.2) * 255.0f;
        g = (float) Math.pow(g, 1.0 / 2.2) * 255.0f;
        b = (float) Math.pow(b, 1.0 / 2.2) * 255.0f;

        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }
}
