package com.dssps.discrollview;

/**
 * 作者：潘跃瑞
 * 时间：2016/11/24
 * 功能：滑动监听器
 */

public interface DiscrollListener {

    /**
     * 当滑动时执行该方法
     * @param ratio:0~1滑动百分比
     */
    public void onDiscroll(float ratio);

    /**
     * 重置动画让View恢复原来的属性
     */
    public void onResetDiscroll();
}
