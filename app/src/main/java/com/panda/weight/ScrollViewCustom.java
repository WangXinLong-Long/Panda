package com.panda.weight;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by wangxinlong on 2017/9/6.
 */

public class ScrollViewCustom extends ViewGroup {
    private Scroller scroller;

    //    判断拖动的最小移动像素数
    private int mTouchSlop;

    //    左右边界
    private int leftBorder;
    private int rightBorder;

    //    手指按下时的屏幕坐标
    private float mXDown;

    //    手指当时所处的屏幕坐标
    private float mXMOve;

    //    上次触发ACTION_MOVE事件时的屏幕坐标
    private float mXLastMove;

    public ScrollViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(childCount - 1).getRight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                mXMOve = ev.getRawX();
                float diff = Math.abs(mXMOve - mXDown);
                mXLastMove = mXMOve;
                if (diff > mTouchSlop) {
                    return true;
                }
            }
            break;
        }
        return super.onInterceptTouchEvent(ev);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                mXMOve = event.getRawX();
                int scrolledX = (int) (mXLastMove - mXMOve);
            }
            break;
            case MotionEvent.ACTION_DOWN: {

            }
            break;
        }
        return super.onTouchEvent(event);

    }
}
