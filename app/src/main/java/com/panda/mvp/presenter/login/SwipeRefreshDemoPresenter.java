package com.panda.mvp.presenter.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.panda.mvp.contract.login.SwipeRefreshDemoContract;
import com.panda.pandalibs.base.mvp.presenter.impl.BasePresenterImpl;

public class SwipeRefreshDemoPresenter extends BasePresenterImpl<SwipeRefreshDemoContract.View> implements SwipeRefreshDemoContract.Presenter {

    public SwipeRefreshDemoPresenter(@NonNull Context context, @NonNull SwipeRefreshDemoContract.View view) {
        super(context, view);
    }
}
