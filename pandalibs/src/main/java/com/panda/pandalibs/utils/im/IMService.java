package com.panda.pandalibs.utils.im;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.panda.pandalibs.App;
import com.panda.pandalibs.utils.Util;

/**
 * Created by wangxinlong on 2017/9/5.
 */

public class IMService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void start(Context context) {
        if (!Util.isSeviceRunning(App.getContext(), IMService.class.getName())) {
            context.startService(new Intent(context, IMService.class));
        }
    }
}
