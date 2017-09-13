package com.panda.mvp.presenter.test;

import android.content.Context;
import android.support.annotation.NonNull;

import com.panda.mvp.contract.test.Test2Contract;
import com.panda.pandalibs.base.mvp.presenter.impl.BasePresenterImpl;

public class Test2Presenter extends BasePresenterImpl<Test2Contract.View> implements Test2Contract.Presenter {

    public Test2Presenter(@NonNull Context context, @NonNull Test2Contract.View view) {
        super(context, view);
    }
}
