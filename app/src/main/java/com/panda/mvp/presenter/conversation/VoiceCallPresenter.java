package com.panda.mvp.presenter.conversation;

import android.content.Context;
import android.support.annotation.NonNull;

import com.panda.mvp.contract.conversation.VoiceCallContract;
import com.panda.pandalibs.base.mvp.presenter.impl.BasePresenterImpl;

public class VoiceCallPresenter extends BasePresenterImpl<VoiceCallContract.View> implements VoiceCallContract.Presenter {

    public VoiceCallPresenter(@NonNull Context context, @NonNull VoiceCallContract.View view) {
        super(context, view);
    }
}
