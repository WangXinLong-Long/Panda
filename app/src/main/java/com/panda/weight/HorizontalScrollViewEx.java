package com.panda.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by wangxinlong on 2017/9/20.
 */

public class HorizontalScrollViewEx extends ViewGroup {


    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    private int mLastY;
    private int mLastX;
    private int mInterceptX;
    private int mInterceptY;

    private int mChildIndex = 0;
    private int mChildCount;
    private int mChildWidth;

    private int measuedWidth;
    private int measuedHeight;


    public HorizontalScrollViewEx(Context context) {
        super(context);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
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
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = true;
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mInterceptX;
                int deltaY = y - mInterceptY;
                if (Math.abs(deltaX)>Math.abs(deltaY)){
                    intercepted = true;
                }else {
                    intercepted = false;
                }
                break;

            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        mInterceptX = x;
        mInterceptY = y;
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                scrollTo(deltaX,0);
                break;

            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                int xVelocity = ((int) mVelocityTracker.getXVelocity());
                if (Math.abs(xVelocity)>=50){
                    mChildIndex = mChildIndex>0?xVelocity-1:xVelocity+1;
                }else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildIndex;
                    int dx = mChildIndex * mChildWidth - scrollX;
                    smoothScrollTo(dx,0);
                    mVelocityTracker.clear();
                }
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollTo(int dx, int dy) {
        mScroller.startScroll(getScrollX(),0,dx,dy);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (childCount == 0){
            setMeasuredDimension(0,0);
        }else if (heightMode == MeasureSpec.AT_MOST){
            View childAt = getChildAt(0);
            measuedHeight = childAt.getMeasuredHeight();
            setMeasuredDimension(widthSize,measuedHeight);
        }else if (widthMode == MeasureSpec.AT_MOST){
            View childAt = getChildAt(0);
            measuedWidth = childAt.getMeasuredWidth()*childCount;
            setMeasuredDimension(measuedWidth,heightSize);
        }else {
            View childAt = getChildAt(0);
            measuedWidth = childAt.getMeasuredWidth()*childCount;
             measuedHeight = childAt.getMeasuredHeight();
            setMeasuredDimension(measuedWidth,measuedHeight);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        int childCount = getChildCount();
        mChildCount = childCount;
        for (int i = 0; i < mChildCount; i++) {
            View childAt = getChildAt(i);
            mChildWidth = childAt.getMeasuredWidth();
            childAt.layout(childLeft,0,childLeft+mChildWidth,childAt.getMeasuredHeight());
            childLeft+=mChildWidth;
        }
    }
}
/*

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    private int mInterceptX = 0;
    private int mInterceptY = 0;

    private int mLastX = 0;
    private int mLastY = 0;

    private int mChildIndex = 0;
    private int mChildCount = 0;
    private int mChildWidth = 0;

    public HorizontalScrollViewEx(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mInterceptX;
                int deltaY = y - mInterceptY;

                if (Math.abs(deltaX) - Math.abs(deltaY) > 0) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;

            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        mInterceptX = x;
        mInterceptY = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;

                scrollBy(-deltaX, 0);
                break;

            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.min(Math.max(0, mChildIndex), mChildCount);
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScrollTo(dx, 0);
                mVelocityTracker.clear();
                break;
            default:
                break;
        }
        mLastY = y;
        mLastX = x;
        return true;
    }

    private void smoothScrollTo(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, dy);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = 0;
        int measuredHeight = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            View childView = getChildAt(0);
            int childHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthSize, childHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            View childView = getChildAt(0);
            int childWidth = childView.getMeasuredWidth() * childCount;
            setMeasuredDimension(childWidth, heightSize);
        } else {
            View childView = getChildAt(0);
            int childHeight = childView.getMeasuredHeight();
            int childWidth = childView.getMeasuredWidth() * childCount;
            setMeasuredDimension(childWidth, childHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        int childCount = getChildCount();
        this.mChildCount = childCount;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int childViewMeasuredWidth = childView.getMeasuredWidth();
            mChildWidth = childViewMeasuredWidth;
            childView.layout(childLeft, 0, childLeft + childViewMeasuredWidth, childView.getMeasuredHeight());
            childLeft += childViewMeasuredWidth;
        }
    }
 */