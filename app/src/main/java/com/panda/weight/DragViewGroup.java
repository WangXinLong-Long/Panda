package com.panda.weight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ViewAnimator;

/**
 * @author wxl
 * @date on 2018/1/3.
 * @describe:
 */

public class DragViewGroup extends FrameLayout {

    private float mLastX;
    private float mLastY;

    private float mDragViewOrigX;
    private float mDragViewOrigY;
    private int mSlop;

    private View mDragView;

    enum State {
        IDLE, DRAGGING;
    }

    private State state;

    public DragViewGroup(@NonNull Context context) {
        super(context);
    }

    public DragViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSlop = ViewConfiguration.getWindowTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isPointInView(event)) {
                    state = State.DRAGGING;
                    mLastX = event.getX();
                    mLastY = event.getY();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (event.getX() - mLastX);
                int deltaY = (int) (event.getY() - mLastY);
                if ((Math.abs(deltaX) > mSlop || Math.abs(deltaY) > mSlop) && mDragView != null && state == State.DRAGGING) {
                    ViewCompat.offsetLeftAndRight(mDragView, deltaX);
                    ViewCompat.offsetTopAndBottom(mDragView, deltaY);
                    mLastX = event.getX();
                    mLastY = event.getY();
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (state == State.DRAGGING) {
                    if (mDragView != null) {
                        ValueAnimator valueAnimatorX = ValueAnimator.ofFloat(mDragView.getX(), mDragViewOrigX);
                        valueAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                mDragView.setX((Float) animation.getAnimatedValue());
                            }
                        });

                        ValueAnimator valueAnimatorY = ValueAnimator.ofFloat(mDragView.getY(), mDragViewOrigY);
                        valueAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                mDragView.setY((Float) animation.getAnimatedValue());
                            }
                        });

                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(valueAnimatorX).with(valueAnimatorY);
                        animatorSet.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                mDragView = null;
                            }
                        });
                        animatorSet.start();
                    }
                    state = State.IDLE;
                }
                break;

            default:
                break;
        }
        return true;
    }

    public boolean isPointInView(MotionEvent event) {
        boolean result = false;

        Rect rect = new Rect();
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View childView = getChildAt(i);
            rect.set((int) childView.getX(), (int) childView.getY(), (int) childView.getX() + childView.getWidth(), (int) childView.getY() + childView.getHeight());
            if (rect.contains(((int) event.getX()), ((int) event.getY()))) {
                mDragView = childView;
                result = true;
                mDragViewOrigX = event.getX();
                mDragViewOrigY = event.getY();
                break;
            }
        }

        return result && state != State.DRAGGING;
    }
}
