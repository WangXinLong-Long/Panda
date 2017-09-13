package com.panda.mvp.ui.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.panda.R;
import com.panda.mvp.contract.test.TestContract;
import com.panda.mvp.presenter.test.TestPresenter;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;

import butterknife.BindView;

public class TestActivity extends BaseActivity<TestPresenter> implements TestContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ball)
    Button ball;

    @Override
    protected void after() {
        super.after();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected int layoutID() {
        return R.layout.activity_test;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        userToolbar(mToolbar, "测试");
    }

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter(this, this);
    }

    public void onClick(View view) {
        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(3000);
        animator.setObjectValues(new PointF(0, 0));
        animator.setInterpolator(new LinearInterpolator());
        animator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF pointF = new PointF();
                pointF.setX(200 * fraction * 3);
                pointF.setY(0.5f * 200 * (fraction * 3) * (fraction * 3));
                return pointF;
            }
        });
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                ball.setX(pointF.getX());
                ball.setY(pointF.getY());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    public void turn(View view) {
        Intent intent = new Intent();
        intent.setClass(this, Test2Activity.class);
        startActivity(intent);
    }
}
