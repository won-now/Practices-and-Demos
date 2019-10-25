package demo.com.wechatui.Transformer;

import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class ScaleTransFormer implements ViewPager.PageTransformer {

    private static final float SCALING = 0.75f;
    private static final float ALPHA = 0.3f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        //left->right  pos left:0.0 -> -1.0  right:1.0->0
        //right->left  pos left:-1.0 -> 0   right:0 -> 1.0
        //规律：左边的都小于0，右边的都大于0
//        Log.d("transform","page :" + page.getTag()+ "  pos: " + position);
        //(1,++)
        if(position > 1){
            page.setScaleX(SCALING);
            page.setScaleY(SCALING);
            page.setAlpha(ALPHA);
            //[0,1]
        }else if (position >= 0){
            //right
            //left->right : 放大 0.75 + 0.25 * (1-position)
            //right->left : 缩小 0.75 + 0.25 * (1-position)
            float scaleA = SCALING + (1 - SCALING) * (1 - position);
            //left->right : 0.5 + 0.5 * (1 - position)
            float alphaA = ALPHA + (1 - ALPHA) * (1 - position);
            page.setScaleX(scaleA);
            page.setScaleY(scaleA);
            page.setAlpha(alphaA);
            //[-1,0)
        }else if (position >= -1){
            //left
            //left->right:缩小 0.75 + 0.25 * (1 + position)
            //right->left:放大 0.75 + 0.25 * (1 + position)
            float scaleB = SCALING + (1 - SCALING) * (1 + position);
            page.setScaleX(scaleB);
            page.setScaleY(scaleB);
            float alphaB = ALPHA + (1 - ALPHA) * (1 + position);
            page.setAlpha(alphaB);
            //(-1,--)
        }else if(position <-1){
            page.setScaleX(SCALING);
            page.setScaleY(SCALING);
            page.setAlpha(ALPHA);
        }
    }
}
