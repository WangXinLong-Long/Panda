package com.panda.mvp.presenter.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.panda.mvp.contract.login.SplashActivityContract;
import com.panda.pandalibs.base.mvp.presenter.impl.BasePresenterImpl;

public class SplashActivityPresenter extends BasePresenterImpl<SplashActivityContract.View> implements SplashActivityContract.Presenter {

    public SplashActivityPresenter(@NonNull Context context, @NonNull SplashActivityContract.View view) {
        super(context, view);
    }
}
