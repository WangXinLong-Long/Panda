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

    Scroller scroller;
    private int touchSlop;
    private int boderLeft;
    private int boderRight;
    private float mDownX;
    private float mLastMoveX;
    private float mMoveX;

    public ScrollViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        touchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
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
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
        }
        boderLeft = getChildAt(0).getLeft();
        boderRight = getChildAt(childCount - 1).getRight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mDownX = ev.getRawX();
                mLastMoveX = mDownX;
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                mMoveX = ev.getRawX();
                float diff = Math.abs(mMoveX - mLastMoveX);
                mLastMoveX = mMoveX;
                if (diff > touchSlop) {
                    return true;
                }
            }
            break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                mMoveX = event.getRawX();
                int scolledX = (int) (mLastMoveX - mMoveX);
                if (getScrollX() + scolledX < boderLeft) {
                    scrollTo(boderLeft, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scolledX > boderRight) {
                    scrollTo(boderRight - getWidth(), 0);
                    return true;
                }
                scrollBy(scolledX, 0);
                mLastMoveX = mMoveX;
            }
            break;
            case MotionEvent.ACTION_UP: {
                int index = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = index * getWidth() - getScrollX();
                scroller.startScroll(getScrollX(),0,dx,0);
                invalidate();
            }
            break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }
}
