package com.panda.mvp.ui.test;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.panda.R;
import com.panda.mvp.contract.test.TestContract;
import com.panda.mvp.presenter.test.TestPresenter;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;

import butterknife.BindView;

public class TestActivity extends BaseActivity<TestPresenter> implements TestContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

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

}
