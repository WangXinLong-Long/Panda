package com.panda.mvp.presenter.test;

import android.content.Context;
import android.support.annotation.NonNull;

import com.panda.mvp.contract.test.TestContract;
import com.panda.pandalibs.base.mvp.presenter.impl.BasePresenterImpl;

public class TestPresenter extends BasePresenterImpl<TestContract.View> implements TestContract.Presenter {

    public TestPresenter(@NonNull Context context, @NonNull TestContract.View view) {
        super(context, view);
    }
}
