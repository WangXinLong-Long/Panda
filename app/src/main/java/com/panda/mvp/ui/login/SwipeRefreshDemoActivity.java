package com.panda.mvp.ui.login;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.refreshview.CustomRefreshView;
import com.panda.R;
import com.panda.mvp.contract.login.SwipeRefreshDemoContract;
import com.panda.mvp.presenter.login.SwipeRefreshDemoPresenter;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;

import butterknife.BindView;

public class SwipeRefreshDemoActivity extends BaseActivity<SwipeRefreshDemoPresenter> implements SwipeRefreshDemoContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private CustomRefreshView refreshView;

    @Override
    protected void after() {
        super.after();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected int layoutID() {
        return R.layout.activity_swiperefreshdemo;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        userToolbar(mToolbar, R.string.swiperefreshdemo_toolbar_title);
        refreshView = (CustomRefreshView) findViewById(R.id.swipe_refresh);
        initSwipeRefresh();
    }

    private void initSwipeRefresh() {
        refreshView.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {

                //下拉刷新，添加你刷新后的逻辑

                //加载完成时，隐藏控件下拉刷新的状态
                refreshView.complete();
            }

            @Override
            public void onLoadMore() {
                //上拉加载更多，添加你加载数据的逻辑

                //加载完成时，隐藏控件上拉加载的状态
                refreshView.complete();
            }
        });
        refreshView.setRefreshing(true);
    }

    @Override
    protected SwipeRefreshDemoPresenter createPresenter() {
        return new SwipeRefreshDemoPresenter(this, this);
    }

}
