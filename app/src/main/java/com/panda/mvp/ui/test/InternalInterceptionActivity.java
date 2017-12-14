package com.panda.mvp.ui.test;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.panda.R;
import com.panda.mvp.contract.test.InternalInterceptionContract;
import com.panda.mvp.presenter.test.InternalInterceptionPresenter;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;

import butterknife.BindView;

public class InternalInterceptionActivity extends BaseActivity<InternalInterceptionPresenter> implements InternalInterceptionContract.View {
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
        return R.layout.activity_internalinterception;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        userToolbar(mToolbar, R.string.internalinterception_toolbar_title);
    }

    @Override
    protected InternalInterceptionPresenter createPresenter() {
        return new InternalInterceptionPresenter(this, this);
    }

}
