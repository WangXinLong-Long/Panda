package com.panda.pandalibs.base.mvp.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.panda.pandalibs.App;
import com.panda.pandalibs.base.mvp.presenter.impl.BasePresenterImpl;
import com.panda.pandalibs.utils.Util;
import com.panda.pandalibs.widget.WeakHandler;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wangxinlong on 2017/8/28.
 */

public abstract class BaseActivity<P extends BasePresenterImpl> extends RxAppCompatActivity {

    private App application;
    private Unbinder bind;
    private P mPresenter;
    private WeakHandler mWeakHandler;
    private boolean mIsNeedAdapterPhone = true;
    private boolean mIsNeedGoneNavigationBar;

    protected Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            getWindow().getDecorView().setSystemUiVisibility(getHideFlags());
        }
    };

    private int getHideFlags() {
        int flags ;
        int curApiVersion = Build.VERSION.SDK_INT;
        if (curApiVersion >= Build.VERSION_CODES.KITKAT) {
            flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        } else {
            flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        return flags;
    }

    public boolean ismIsNeedGoneNavigationBar() {
        return mIsNeedGoneNavigationBar;
    }

    public void setmIsNeedGoneNavigationBar(boolean mIsNeedGoneNavigationBar) {
        this.mIsNeedGoneNavigationBar = mIsNeedGoneNavigationBar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        before();
        if (layoutID() > 0) {
            setContentView(layoutID());
        }
        after();
        init(savedInstanceState);
        data();
    }

    protected abstract void init(Bundle savedInstanceState);


    protected void data() {

    }


    protected void after() {
        bind = ButterKnife.bind(this);
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        mWeakHandler = new WeakHandler();
        if (mIsNeedAdapterPhone && !IsNeedAdapterPhone()) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {

            }
        } else {
//            android4Adapter();
        }

        if (mIsNeedGoneNavigationBar) {
            toHideNv();
        }

    }

    private void toHideNv() {
        mWeakHandler.post(mHideRunnable);
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                mWeakHandler.post(mHideRunnable);
            }
        });
    }

    private void android4Adapter() {
//                                                                                                           coordinatorLayout
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(getResources().getIdentifier("coordinatorLayout", "id", getPackageName()));
        if (coordinatorLayout != null && coordinatorLayout.getChildCount() > 0) {
            if (coordinatorLayout.getChildAt(0) instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) coordinatorLayout.getChildAt(0);

                if (appBarLayout.getChildCount() > 0 && appBarLayout.getChildAt(0) instanceof Toolbar) {
                    Toolbar toolbar = (Toolbar) appBarLayout.getChildAt(0);
                    AppBarLayout.LayoutParams lp = ((AppBarLayout.LayoutParams) toolbar.getLayoutParams());
                    lp.setMargins(0, Util.getTop(this), 0, 0);
                    toolbar.setLayoutParams(lp);
                }
            }
        }
    }


    private boolean IsNeedAdapterPhone() {
        if (Build.VERSION.SDK_INT > 21 || Build.MODEL.toLowerCase().contains("vivo")) {
            return false;
        }
        return true;
    }

    protected abstract P createPresenter();


    protected void before() {
        application = (App) getApplication();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    protected abstract int layoutID();

    protected void userToolBar(Toolbar toolbar, View.OnClickListener clickListener) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (clickListener == null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        } else {
            toolbar.setNavigationOnClickListener(clickListener);
        }
    }

    protected void userToolbar(Toolbar toolbar, String title, View.OnClickListener onClickListener) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        }
        if (onClickListener == null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else {
            toolbar.setNavigationOnClickListener(onClickListener);
        }
    }

    protected void userToolbar(Toolbar toolbar, int resId, View.OnClickListener onClickListener) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(resId);
        }
        if (onClickListener == null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else {
            toolbar.setNavigationOnClickListener(onClickListener);
        }
    }

    protected void userToolbar(Toolbar toolbar, String title) {
        userToolbar(toolbar, title, null);
    }

    protected void userToolbar(Toolbar toolbar, int resId) {
        userToolbar(toolbar, resId, null);
    }

    protected void userToolbar(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }


    protected void setToolbarTitle(int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(resId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }

        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
