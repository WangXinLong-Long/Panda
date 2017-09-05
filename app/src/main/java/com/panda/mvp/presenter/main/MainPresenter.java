package com.panda.mvp.presenter.main;

import android.content.Context;
import android.support.annotation.NonNull;

import com.panda.mvp.contract.main.MainContract;
import com.panda.pandalibs.base.mvp.presenter.impl.BasePresenterImpl;

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(@NonNull Context context, @NonNull MainContract.View view) {
        super(context, view);
    }
}
