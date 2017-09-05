package com.panda.mvp.presenter.conversation;

import android.content.Context;
import android.support.annotation.NonNull;

import com.panda.mvp.contract.conversation.VideoCallContract;
import com.panda.pandalibs.base.mvp.presenter.impl.BasePresenterImpl;

public class VideoCallPresenter extends BasePresenterImpl<VideoCallContract.View> implements VideoCallContract.Presenter {

    public VideoCallPresenter(@NonNull Context context, @NonNull VideoCallContract.View view) {
        super(context, view);
    }
}
