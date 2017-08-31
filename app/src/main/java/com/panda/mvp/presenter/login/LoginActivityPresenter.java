package com.panda.mvp.presenter.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.panda.mvp.contract.login.LoginActivityContract;
import com.panda.pandalibs.base.mvp.presenter.impl.BasePresenterImpl;

public class LoginActivityPresenter extends BasePresenterImpl<LoginActivityContract.View> implements LoginActivityContract.Presenter {

    public LoginActivityPresenter(@NonNull Context context, @NonNull LoginActivityContract.View view) {
        super(context, view);
    }
}
