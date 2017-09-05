package com.panda.mvp.ui.conversation;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.panda.R;
import com.panda.mvp.contract.conversation.VideoCallContract;
import com.panda.mvp.presenter.conversation.VideoCallPresenter;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;

import butterknife.BindView;

public class VideoCallActivity extends BaseActivity<VideoCallPresenter> implements VideoCallContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void after() {
        super.after();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected int layoutID() {
        return R.layout.activity_videocall;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        userToolbar(mToolbar, R.string.videocall_toolbar_title);
    }

    @Override
    protected VideoCallPresenter createPresenter() {
        return new VideoCallPresenter(this, this);
    }

}
