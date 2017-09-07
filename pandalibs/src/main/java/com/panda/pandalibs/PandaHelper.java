package com.panda.pandalibs;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.panda.pandalibs.utils.utils.IMClient;

/**
 * Created by wangxinlong on 2017/9/4.
 */

public class PandaHelper {
    private static PandaHelper instance;
    private String username;
    private DemoModel demoModel = null;

    public synchronized static PandaHelper getInstance() {
        if (instance == null) {
            instance = new PandaHelper();
        }
        return instance;
    }

    public void init(Context context) {
        demoModel = new DemoModel(context);
        IMClient.imInit(context);
    }

    public boolean isLoggedIn() {
        if (EMClient.getInstance() != null) {
            boolean loggedInBefore = EMClient.getInstance().isLoggedInBefore();
            return loggedInBefore;
        }
        return true;
    }

    /**
     * set current username
     *
     * @param username
     */
    public void setCurrentUserName(String username) {
        this.username = username;
        demoModel.setCurrentUserName(username);
    }
}
