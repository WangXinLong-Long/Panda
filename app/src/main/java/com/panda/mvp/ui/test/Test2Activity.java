package com.panda.mvp.ui.test;

import android.animation.LayoutTransition;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;

import com.panda.R;
import com.panda.mvp.contract.test.Test2Contract;
import com.panda.mvp.presenter.test.Test2Presenter;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;

import butterknife.BindView;

public class Test2Activity extends BaseActivity<Test2Presenter> implements Test2Contract.View,
        CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ViewGroup viewGroup;
    private GridLayout mGridLayout;
    private int mVal;
    private LayoutTransition mTransition;

    private CheckBox mAppear, mChangeAppear, mDisAppear, mChangeDisAppear;

    @Override
    protected void after() {
        super.after();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected int layoutID() {
        return R.layout.activity_test2;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        userToolbar(mToolbar, R.string.test2_toolbar_title);
        viewGroup = (ViewGroup) findViewById(R.id.id_container);

        mAppear = (CheckBox) findViewById(R.id.id_appear);
        mChangeAppear = (CheckBox) findViewById(R.id.id_change_appear);
        mDisAppear = (CheckBox) findViewById(R.id.id_disappear);
        mChangeDisAppear = (CheckBox) findViewById(R.id.id_change_disappear);

        mAppear.setOnCheckedChangeListener(this);
        mChangeAppear.setOnCheckedChangeListener(this);
        mDisAppear.setOnCheckedChangeListener(this);
        mChangeDisAppear.setOnCheckedChangeListener(this);

        mGridLayout = new GridLayout(this);
        mGridLayout.setColumnCount(4);
        viewGroup.addView(mGridLayout);
        mTransition = new LayoutTransition();
        mGridLayout.setLayoutTransition(mTransition);

    }

    @Override
    protected Test2Presenter createPresenter() {
        return new Test2Presenter(this, this);
    }

    /**
     * 添加按钮
     *
     * @param view
     */
    public void addBtn(View view) {
        Button button = new Button(this);
        button.setText((++mVal) + "");
        mGridLayout.addView(button, Math.min(1, mGridLayout.getChildCount()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridLayout.removeView(v);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mTransition.setAnimator(LayoutTransition.APPEARING,mAppear.isChecked()?mTransition.getAnimator(LayoutTransition.APPEARING):null);
        mTransition.setAnimator(LayoutTransition.CHANGE_APPEARING,mChangeAppear.isChecked()?mTransition.getAnimator(LayoutTransition.CHANGE_APPEARING):null);
        mTransition.setAnimator(LayoutTransition.DISAPPEARING,mDisAppear.isChecked()?mTransition.getAnimator(LayoutTransition.DISAPPEARING):null);
        mTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,mChangeDisAppear.isChecked()?mTransition.getAnimator(LayoutTransition.CHANGE_DISAPPEARING):null);
        mGridLayout.setLayoutTransition(mTransition);
    }
}
