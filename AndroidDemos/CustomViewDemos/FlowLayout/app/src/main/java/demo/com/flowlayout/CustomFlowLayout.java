package demo.com.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class CustomFlowLayout extends ViewGroup {

    public CustomFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getMode(heightMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int childCount = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;
        int width = 0;
        int height = 0;

        for(int i = 0; i < childCount; i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = lp.leftMargin + lp.rightMargin + child.getMeasuredWidth();//???为什么不用lp.width
            int childHeight = lp.topMargin + lp.bottomMargin + child.getMeasuredHeight();

            if(lineWidth + childWidth > measureWidth){
                width = Math.max(childWidth, lineWidth);
                height += lineHeight;

                lineWidth = childWidth;
                lineHeight = childHeight;
            }else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if(i == childCount - 1){
                height += lineHeight;
                width = Math.max(lineWidth, width);
            }
        }
        setMeasuredDimension((measureWidthMode == MeasureSpec.EXACTLY ? measureWidth : width),
                (measureHeightMode == MeasureSpec.EXACTLY ? measureHeight : height));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0;
        int left = 0;
        int lineWidth = 0;
        int lineHeight = 0;
        int childCount = getChildCount();

        for(int i = 0; i < childCount; i++){
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childeHeight = child.getMeasuredHeight() + lp.bottomMargin + lp.topMargin;

            if(lineWidth + childWidth > getMeasuredWidth()){
                left = 0;
                top += lineHeight;

                lineWidth = childWidth;
                lineHeight = childeHeight;
            }else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childeHeight);
            }
            int lc = left + lp.leftMargin;
            int rc = lc + child.getMeasuredWidth();
            int tc = top + lp.topMargin;
            int bc = top + child.getMeasuredHeight();

            child.layout(lc, tc, rc, bc);
            left += childWidth;
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        Log.d("GLP(LP)","-----------------------");
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        Log.d("GLP(Attr)","-----------------------");
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        Log.d("GDLP()","-----------------------");
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
}
