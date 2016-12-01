package com.example.qwe.yunifang.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by qwe on 2016/11/28.
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 不消费
     * @param ev
     * @return
     */

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    /**
     * 不阻断
     * @param event
     * @return
     */

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return false;
    }
}
