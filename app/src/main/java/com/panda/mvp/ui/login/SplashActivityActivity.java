package com.panda.mvp.ui.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.hyphenate.chat.EMClient;
import com.hyphenate.util.EasyUtils;
import com.panda.R;
import com.panda.mvp.contract.login.SplashActivityContract;
import com.panda.mvp.presenter.login.SplashActivityPresenter;
import com.panda.mvp.ui.conversation.VideoCallActivity;
import com.panda.mvp.ui.conversation.VoiceCallActivity;
import com.panda.mvp.ui.main.MainActivity;
import com.panda.mvp.ui.test.TestActivity;
import com.panda.pandalibs.PandaHelper;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;

import java.util.Random;

import io.reactivex.disposables.Disposable;

public class SplashActivityActivity extends BaseActivity<SplashActivityPresenter> implements SplashActivityContract.View {

    private static final long sleepTime = 500;

    private Disposable disposable;

    @Override
    protected void before() {
        super.before();
        setmIsNeedGoneNavigationBar(true);
    }

    private int getCount() {

        return new Random().nextInt(100);
    }

    @Override
    protected void after() {
        super.after();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected int layoutID() {
        return R.layout.activity_splashactivity;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        new Thread(() -> {
            if (PandaHelper.getInstance().isLoggedIn()) {
                long start = System.currentTimeMillis();
                EMClient.getInstance().chatManager().loadAllConversations();
                EMClient.getInstance().groupManager().loadAllGroups();
                long costTime = System.currentTimeMillis() - start;
                if (sleepTime - costTime > 0) {
                    if (disposable == null || disposable.isDisposed())
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    String topActivityName = EasyUtils.getTopActivityName(EMClient.getInstance().getContext());
                    if (topActivityName != null && (topActivityName.equals(VideoCallActivity.class.getName()) || topActivityName.equals(VoiceCallActivity.class.getName()))) {
                        // nop
                        // avoid main screen overlap Calling Activity
                    } else {
                        //enter main screen
                        startActivity(new Intent(SplashActivityActivity.this, MainActivity.class));
                    }
                    finish();
                }
            } else {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
                startActivity(new Intent(SplashActivityActivity.this, LoginActivityActivity.class));
                finish();
            }
        }).start();


    }

    @Override
    protected SplashActivityPresenter createPresenter() {
        return new SplashActivityPresenter(this, this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }
}
