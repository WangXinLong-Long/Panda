package com.panda.pandalibs.base.mvp.presenter.impl;

import android.content.Context;

import com.panda.pandalibs.base.mvp.view.BaseView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by wangxinlong on 2017/8/28.
 */

public class BasePresenterImpl<V extends BaseView> {
    private V view;
    private Context mContext;

    private WeakReference<CompositeDisposable> mCompositeDisposable;

    public BasePresenterImpl(Context mContext, V view) {
        this.view = view;
        this.mContext = mContext;
        mCompositeDisposable = new WeakReference<>(new CompositeDisposable());
    }

    public void detachView() {
        this.view = null;
        onUSubscribe();
    }

    private void onUSubscribe() {
        if (mCompositeDisposable != null && mCompositeDisposable.get() != null && !mCompositeDisposable.get().isDisposed()) {
            mCompositeDisposable.get().dispose();
            mCompositeDisposable.clear();
        }
        mCompositeDisposable = null;
        mContext = null;
    }
}
