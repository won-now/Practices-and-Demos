package demo.com.scrolldemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

public class PinnedHeaderExpandableListView extends ExpandableListView implements AbsListView.OnScrollListener {

    public interface OnHeaderUpdateListener{
        public View getPinnedHeader();

        public Void updatePinnedHeader(int firstVisibleGroupPos);
    }

    private View mHeaderView;
    private int mHeaderWidth;
    private int mHeaderHeight;

    private OnScrollListener mScrollListener;
    private OnHeaderUpdateListener mHeaderUpdateListener;

    private boolean mActionDownHappend = false;

    public PinnedHeaderExpandableListView(Context context) {
        super(context);
    }

    public PinnedHeaderExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PinnedHeaderExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        if(l != this){

        }
    }

    //一旦滑动开始就被回调
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    //滑动完后被回调
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
