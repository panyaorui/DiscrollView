package com.dssps.discrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 作者：潘跃瑞
 * 时间：2016/11/24
 * 功能：为需要做动画的控件进行包装父控件，获取控件的动画属性，传入包装的父控件中，进行动画操作，
 * 主要作用在于在使用该框架是不需要自定义控件在原生控件中添加自定义属性
 */

public class DiscrollLinearLayout extends LinearLayout {

    public DiscrollLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        DiscrollParams params = new DiscrollParams(getContext(),attrs);
        return params;
    }


    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        DiscrollParams params1 = (DiscrollParams) params;
        if(!isCustom(params1)){
            super.addView(child,params);
        }else {
            DiscrollFrameLayout frameLayout = new DiscrollFrameLayout(getContext());
            frameLayout.setmDiscrollverScaleY(params1.mDiscrollverScaleY);
            frameLayout.setmDiscrollveAlpha(params1.mDiscrollveAlpha);
            frameLayout.setmDiscrollveFromBgColor(params1.mDiscrollveFromBgColor);
            frameLayout.setmDiscrollveToBgColor(params1.mDiscrollveToBgColor);
            frameLayout.setmDiscrollverTransltion(params1.mDiscrollverTransltion);
            frameLayout.setmDiscrollverScaleX(params1.mDiscrollverScaleX);
            frameLayout.addView(child);
            super.addView(frameLayout, params);
        }
    }

    private boolean isCustom(DiscrollParams params){
        return params.mDiscrollverScaleX
                || params.mDiscrollverScaleY
                || params.mDiscrollveAlpha
                || params.mDiscrollverTransltion != -1
                || (params.mDiscrollveFromBgColor != -1 && params.mDiscrollveToBgColor != -1);
    }

    private class DiscrollParams extends LinearLayout.LayoutParams{
        /** 自定义属性接受**/
        public int mDiscrollveFromBgColor;//背景颜色变化开始值
        public int mDiscrollveToBgColor; //背景颜色变化结束值
        public int mDiscrollverTransltion;//平移值
        public boolean mDiscrollverScaleX;//是否进行x轴缩放
        public boolean mDiscrollverScaleY;//是否进行y轴缩放
        public boolean mDiscrollveAlpha;//是否有透明度动画

        public DiscrollParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            TypedArray typedArray = c.obtainStyledAttributes(attrs,R.styleable.DiscrollView_LayoutParams);
            mDiscrollveFromBgColor = typedArray.getInt
                    (R.styleable.DiscrollView_LayoutParams_discroll_fromBgColor,-1);
            mDiscrollveToBgColor = typedArray.getInt
                    (R.styleable.DiscrollView_LayoutParams_discroll_toBgColor,-1);
            mDiscrollverTransltion = typedArray.getInt
                    (R.styleable.DiscrollView_LayoutParams_discroll_translation,-1);
            mDiscrollverScaleX = typedArray.getBoolean
                    (R.styleable.DiscrollView_LayoutParams_discroll_scaleX,false);
            mDiscrollverScaleY = typedArray.getBoolean
                    (R.styleable.DiscrollView_LayoutParams_discroll_scaleY,false);
            mDiscrollveAlpha = typedArray.getBoolean(R.styleable.DiscrollView_LayoutParams_discroll_alpha,false);
            typedArray.recycle();
        }
    }
}
