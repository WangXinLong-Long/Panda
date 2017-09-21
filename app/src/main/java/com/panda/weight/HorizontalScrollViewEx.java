package com.panda.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by wangxinlong on 2017/9/20.
 */

public class HorizontalScrollViewEx extends ViewGroup {
    private int mChildrenWidth;
    private int mChildrenIndex;
    private int mChildrenSize;


    private Scroller scroller;
    private VelocityTracker tracker;
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;
    private int mLastX = 0;
    private int mLastY = 0;

    public HorizontalScrollViewEx(Context context) {
        super(context);
        init();
    }

    private void init() {
        scroller = new Scroller(getContext());
        tracker = VelocityTracker.obtain();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                intercept = false;
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                    intercept = true;
                }
            }
            break;

            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) - Math.abs(deltaY) > 0) {
                    intercept = true;
                } else {
                    intercept = false;
                }
            }
            break;
            case MotionEvent.ACTION_UP: {
                intercept = false;
            }
            break;
        }
        mLastXIntercept = x;
        mLastX = x;
        mLastYIntercept = y;
        mLastY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        tracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                scrollBy(deltaX, 0);
            }
            break;
            case MotionEvent.ACTION_UP: {
                int scrollX = getScrollX();
                int scrollToChildIndex = scrollX / mChildrenWidth;
                tracker.computeCurrentVelocity(1000);
                float xVelocity = tracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50) {
                    mChildrenIndex = xVelocity > 0 ? mChildrenIndex - 1 : mChildrenIndex + 1;
                } else {
                    mChildrenIndex = (scrollX + mChildrenWidth / 2) / mChildrenWidth;
                }
                mChildrenIndex = Math.max(0,Math.min(mChildrenIndex,mChildrenSize));
                int dx = mChildrenIndex * mChildrenWidth - scrollX;
                smoothScrollBy(dx,0);
                tracker.clear();
            }
            break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measuewHeight = 0;
        int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (childCount == 0){
            setMeasuredDimension(0,0);
        }else if (heightSpecMode == MeasureSpec.AT_MOST){
            View childView = getChildAt(0);
            measuewHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthMeasureSpec,childView.getMeasuredWidth());
        }
    }

    private void smoothScrollBy(int dx, int i) {
        scroller.startScroll(getScrollX(), 0, dx, i, 500);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
