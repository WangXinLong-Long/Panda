package com.panda.pandalibs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.panda.pandalibs.App;

import static com.panda.pandalibs.utils.constant.Constant.USER_SETTING;

/**
 * Created by wangxinlong on 2017/9/5.
 */

public class SPreference {
    public static boolean getOpenJsonLog() {
        return getBoolean("couldOpenJsonLog");
    }

    public static void saveOpenJsonLog(boolean b) {
        putBoolean("couldOpenJsonLog", b);
    }

    private static boolean getBoolean(@NonNull String key) {
        return getBase().getBoolean(key, false);
    }

    private static void putBoolean(@NonNull String s, @NonNull boolean b) {
        SharedPreferences.Editor edit = getBase().edit();
        edit.putBoolean(s, b);
        edit.apply();
    }

    private static SharedPreferences getBase() {
        return App.getContext().getApplicationContext().getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
    }
}
