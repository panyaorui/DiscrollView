package com.dssps.discrollview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 作者：潘跃瑞
 * 时间：2016/11/24
 * 功能：自定义FrameLayout
 */

public class DiscrollFrameLayout extends FrameLayout implements DiscrollListener{

    /** 动画执行顺序 */
    private static final int TRANSLATION_FROM_TOP = 0x01;
    private static final int TRANSLATION_FROM_BOTTON = 0x02;
    private static final int TRANSLATION_FROM_LEFT = 0x04;
    private static final int TRANSLATION_FROM_RIGHT = 0x08;

    //颜色估值器
    private static ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    /** 自定义属性接受**/
    private int mDiscrollveFromBgColor;//背景颜色变化开始值
    private int mDiscrollveToBgColor; //背景颜色变化结束值
    private int mDiscrollverTransltion;//平移值
    private boolean mDiscrollverScaleX;//是否进行x轴缩放
    private boolean mDiscrollverScaleY;//是否进行y轴缩放
    private boolean mDiscrollveAlpha;//是否有透明度动画

    private int mWidht; //view宽度
    private int mHeight; //view高度

    public void setmDiscrollveFromBgColor(int mDiscrollveFromBgColor) {
        this.mDiscrollveFromBgColor = mDiscrollveFromBgColor;
    }

    public void setmDiscrollveToBgColor(int mDiscrollveToBgColor) {
        this.mDiscrollveToBgColor = mDiscrollveToBgColor;
    }

    public void setmDiscrollverTransltion(int mDiscrollverTransltion) {
        this.mDiscrollverTransltion = mDiscrollverTransltion;
    }

    public void setmDiscrollverScaleX(boolean mDiscrollverScaleX) {
        this.mDiscrollverScaleX = mDiscrollverScaleX;
    }

    public void setmDiscrollverScaleY(boolean mDiscrollverScaleY) {
        this.mDiscrollverScaleY = mDiscrollverScaleY;
    }

    public void setmDiscrollveAlpha(boolean mDiscrollveAlpha) {
        this.mDiscrollveAlpha = mDiscrollveAlpha;
    }

    public DiscrollFrameLayout(Context context) {
        super(context);
    }


    public DiscrollFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscrollFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidht = w;
        mHeight = h;
        onResetDiscroll();
    }

    @Override
    public void onDiscroll(float ratio) {
        if(mDiscrollveAlpha){ //判断是否有透明度动画
            setAlpha(ratio);
        }
        if(mDiscrollverScaleX){
            setScaleX(ratio); //判断是否有x缩放动画
        }
        if(mDiscrollverScaleY){//判断是否有Y缩放动画
            setScaleY(ratio);
        }
        /** 平移动画 */
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_BOTTON)){ //从底部出来动画
              //Hight -->到0（表示原来的位置）
              setTranslationY(mHeight * (1 - ratio));
        }
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_TOP)){//从头部出来动画
               //-Hight -->到0（表示原来的位置）
            setTranslationY(-mHeight * (1 - ratio));
        }
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_LEFT)){//从左侧出来动画
            //-WIDTH -->到0（表示原来的位置）
            setTranslationX(-mWidht * (1 - ratio));
        }
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_RIGHT)){//从右侧出来动画
            //WIDTH -->到0（表示原来的位置）
            setTranslationX(mWidht * (1 - ratio));
        }
        if(mDiscrollveFromBgColor != -1 && mDiscrollveToBgColor != -1){
            setBackgroundColor((int) argbEvaluator.evaluate(ratio,mDiscrollveFromBgColor,mDiscrollveToBgColor));
        }
    }

    @Override
    public void onResetDiscroll() {
        if(mDiscrollveAlpha){ //判断是否有透明度动画
            setAlpha(0);
        }
        if(mDiscrollverScaleX){
            setScaleX(0); //判断是否有x缩放动画
        }
        if(mDiscrollverScaleY){//判断是否有Y缩放动画
            setScaleY(0);
        }
        /** 平移动画 */
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_BOTTON)){ //从底部出来动画
            //Hight -->到0（表示原来的位置）
            setTranslationY(mHeight);
        }
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_TOP)){//从头部出来动画
            //-Hight -->到0（表示原来的位置）
            setTranslationY(-mHeight);
        }
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_LEFT)){//从左侧出来动画
            //-WIDTH -->到0（表示原来的位置）
            setTranslationX(-mWidht);
        }
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_RIGHT)){//从右侧出来动画
            //WIDTH -->到0（表示原来的位置）
            setTranslationX(mWidht);

        }
    }

    /**
     * 判断是否有改数值
     * @param mask
     * @return
     */
    private boolean isDiscrollTranslationFrom(int mask){

          if(mDiscrollverTransltion == -1){
              return false;
          }
        //fromLeft|fromBottom  & fromLeft = fromLeft;//计算公式
        //或运算的结果对其中一个数值进行余运算结果还是等于改数值
        return (mDiscrollverTransltion & mask) == mask;
    }
}
