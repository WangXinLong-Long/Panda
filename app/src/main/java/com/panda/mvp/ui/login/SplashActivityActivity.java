package com.panda.mvp.ui.login;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.panda.R;
import com.panda.mvp.contract.login.SplashActivityContract;
import com.panda.mvp.presenter.login.SplashActivityPresenter;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;

import butterknife.BindView;

public class SplashActivityActivity extends BaseActivity<SplashActivityPresenter> implements SplashActivityContract.View {
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
        return R.layout.activity_splashactivity;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        userToolbar(mToolbar, "测试");
    }

    @Override
    protected SplashActivityPresenter createPresenter() {
        return new SplashActivityPresenter(this, this);
    }

}
