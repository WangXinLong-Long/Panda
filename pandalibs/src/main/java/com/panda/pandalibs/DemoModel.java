package com.panda.pandalibs;


import android.content.Context;

import com.panda.pandalibs.utils.PreferenceManager;


/**
 * Created by wangxinlong on 2017/9/6.
 */

class DemoModel {
    protected Context context = null;

    public DemoModel(Context context) {
        this.context = context;
        PreferenceManager.init(context);
    }

    public void setCurrentUserName(String name) {
        PreferenceManager.getInstance().setCurrentUserName(name);
    }

    public String getCurrentUserName(){
        return PreferenceManager.getInstance().getCurrentUserName();
    }
}
