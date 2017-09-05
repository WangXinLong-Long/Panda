package com.panda.pandalibs.utils.utils.helper;

import com.hyphenate.chat.EMClient;

/**
 * Created by wangxinlong on 2017/9/4.
 */

public class PandaHelper {
    private static PandaHelper instance;

    public synchronized static PandaHelper getInstance() {
        if (instance == null) {
            instance = new PandaHelper();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        if (EMClient.getInstance() != null) {
            boolean loggedInBefore = EMClient.getInstance().isLoggedInBefore();
            return loggedInBefore;
        }
        return true;
    }
}
