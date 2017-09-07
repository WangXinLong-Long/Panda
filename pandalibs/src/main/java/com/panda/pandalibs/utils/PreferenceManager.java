package com.panda.pandalibs.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wangxinlong on 2017/9/6.
 */

public class PreferenceManager {
    public static final String PREFERENCE_NAME = "saveInfo";
    private static String SHARED_KEY_CURRENTUSER_USERNAME = "SHARED_KEY_CURRENTUSER_USERNAME";
    private static PreferenceManager mPreferenceManager;
    private static SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    private PreferenceManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public static synchronized void init(Context context) {
        if (mPreferenceManager == null) {
            mPreferenceManager = new PreferenceManager(context);
        }
    }

    public static PreferenceManager getInstance() {
        if (mPreferenceManager == null) {
            throw new RuntimeException("please init first!");
        }
        return mPreferenceManager;

    }

    public void setCurrentUserName(String userName) {
        editor.putString(SHARED_KEY_CURRENTUSER_USERNAME, userName);
        editor.apply();
    }
     public String getCurrentUserName() {
        return mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_USERNAME, null);
    }

}
