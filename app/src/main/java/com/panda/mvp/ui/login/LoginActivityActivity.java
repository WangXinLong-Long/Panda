package com.panda.mvp.ui.login;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.panda.R;
import com.panda.mvp.contract.login.LoginActivityContract;
import com.panda.mvp.presenter.login.LoginActivityPresenter;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;

import butterknife.BindView;

public class LoginActivityActivity extends BaseActivity<LoginActivityPresenter> implements LoginActivityContract.View {
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
        return R.layout.activity_loginactivity;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        userToolbar(mToolbar, R.string.loginactivity_toolbar_title);
    }

    @Override
    protected LoginActivityPresenter createPresenter() {
        return new LoginActivityPresenter(this, this);
    }

}
