package com.panda.mvp.presenter.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.panda.mvp.contract.login.RegisterContract;
import com.panda.pandalibs.base.mvp.presenter.impl.BasePresenterImpl;

public class RegisterPresenter extends BasePresenterImpl<RegisterContract.View> implements RegisterContract.Presenter {

    public RegisterPresenter(@NonNull Context context, @NonNull RegisterContract.View view) {
        super(context, view);
    }
}
