package com.panda.mvp.presenter.test;

import android.content.Context;
import android.support.annotation.NonNull;

import com.panda.mvp.contract.test.InternalInterceptionContract;
import com.panda.pandalibs.base.mvp.presenter.impl.BasePresenterImpl;

public class InternalInterceptionPresenter extends BasePresenterImpl<InternalInterceptionContract.View> implements InternalInterceptionContract.Presenter {

    public InternalInterceptionPresenter(@NonNull Context context, @NonNull InternalInterceptionContract.View view) {
        super(context, view);
    }
}
