package com.panda.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * @author wxl
 * @date on 2017/11/7.
 */

public class HorizontalScrollViewEx2 extends ViewGroup {
    private int mLastX;
    private int mLastY;
    private Scroller mScroller;

    public HorizontalScrollViewEx2(Context context) {
        super(context);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    public HorizontalScrollViewEx2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalScrollViewEx2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            mLastX = x;
            mLastY = y;
            if (!mScroller.isFinished()){
                mScroller.abortAnimation();
                return  true;
            }
            return false;
        }else {
            return true;
        }

    }
}
