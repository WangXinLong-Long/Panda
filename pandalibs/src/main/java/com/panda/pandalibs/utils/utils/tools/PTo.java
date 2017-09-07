package com.panda.pandalibs.utils.utils.tools;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by wangxinlong on 2017/9/6.
 */

public class PTo {
    private Toast toast = null;
    private static PTo instance;

    public static PTo get() {
        if (instance == null) {
            synchronized (PTo.class) {
                if (instance == null) {
                    instance = new PTo();
                }
            }
        }
        return instance;
    }

    public void show(Context context, int s) {
        show(context, context.getString(s));
    }

    public void show(Context context, String s) {
        if (TextUtils.isEmpty(s) || context == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(s);
            toast.show();
        }
    }
}
