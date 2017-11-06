package com.panda.mvp.ui.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.R;
import com.panda.mvp.contract.test.TestContract;
import com.panda.mvp.presenter.test.TestPresenter;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;
import com.panda.pandalibs.utils.MyUtils;
import com.panda.weight.HorizontalScrollViewEx;

import java.util.ArrayList;

import butterknife.BindView;

public class TestActivity extends BaseActivity<TestPresenter> implements TestContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
//    @BindView(R.id.ball)
//    Button ball;
    LinearLayout linearLayout;
    private HorizontalScrollViewEx mListContainer;

    //    private HorizontalScrollViewEx mListContainer;
    @Override
    protected void after() {
        super.after();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void before() {
        super.before();
        setmIsNeedGoneNavigationBar(false);
    }

    @Override
    protected int layoutID() {
        return R.layout.demo_1;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        userToolbar(mToolbar, "测试");
//        Glide.with(this).load(url).into();
//        setContentView(R.layout.demo_1);
//        Log.d(TAG, "onCreate");
        initView();
    }
    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = (HorizontalScrollViewEx) findViewById(R.id.container);
        final int screenWidth = MyUtils.getScreenMetrics(this).widthPixels;
        final int screenHeight = MyUtils.getScreenMetrics(this).heightPixels;
        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(
                    R.layout.content_layout, mListContainer, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            mListContainer.addView(layout);
        }
    }
    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(TestActivity.this, "click item",
                        Toast.LENGTH_SHORT).show();

            }
        });
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
//                ball.setX(pointF.getX());
//                ball.setY(pointF.getY());
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
