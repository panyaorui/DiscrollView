package com.dssps.discrollview;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

/**
 * 作者：潘跃瑞
 * 时间：2016/11/24
 * 功能：自定义ScrollView，动画最外层容器
 */
public class DiscrollScrollView extends ScrollView{

    private static final String TAG = "DiscrollScrollView";

    private DiscrollLinearLayout mConentView = null;


    public DiscrollScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 加载视图结束
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = getChildAt(0);
        if(view instanceof DiscrollLinearLayout){
            mConentView = (DiscrollLinearLayout)view;
        }
    }

    /**
     * ScrollView滑动监听
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //遍历mConentView所有子控件
        if(mConentView == null) return;

        View childView;
        for (int i = 0; i < mConentView.getChildCount(); i++) {
            childView = mConentView.getChildAt(i);
            if(!(childView instanceof DiscrollListener)){
                  continue;
            }
            DiscrollListener listener = (DiscrollListener) childView;
            //scrollview可见高度
            int scrollHeight = getHeight();
            int top = childView.getTop();
            float height = childView.getHeight();
            //1.得到Scroll画出的高度 就是t
            //2.计算控件到达屏幕顶部的高度
             int absoluteTop = top - t;
            Log.e(TAG, "tag:"+childView.getTag()+"  scrollHeight："+scrollHeight+" top:"+top
                    +" height"+height+"  AbsoluteTop:"+absoluteTop);
            int visibleHegith = scrollHeight   - absoluteTop; //计算可见高度
            if(visibleHegith >= 0){ //可见
                float ratio =  visibleHegith / height ;
                if(ratio > 1){
                    ratio = 1;
                }
                listener.onDiscroll(ratio);
            }else{//不可见
               listener.onResetDiscroll();
            }
        }
    }
}
