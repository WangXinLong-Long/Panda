package com.panda.pandalibs.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;

import com.panda.pandalibs.App;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wangxinlong on 2017/8/29.
 */

public class Util {

    public static int getTop(Activity activity) {
        Rect rectF = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectF);
        if (rectF.top == 0) {
            try {
                Class aClass = Class.forName("com.android.internal.R$dimen");
                Object o = aClass.newInstance();
                Field status_bar_height = aClass.getField("status_bar_height");
                int x = Integer.parseInt(status_bar_height.get(o).toString());
                return activity.getResources().getDimensionPixelOffset(x);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return rectF.top;
    }

    public static boolean isSeviceRunning(Context context, String serviceName) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (runningServices == null || runningServices.size() == 0) {
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            if (runningServices.get(i).service.getClassName().equals(serviceName)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    public static String getAppName(int myUid) {
        String processName = null;
        ActivityManager activityManager = (ActivityManager) App.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        Iterator<ActivityManager.RunningAppProcessInfo> infoIterator = runningAppProcesses.iterator();
        while (infoIterator.hasNext()) {
            ActivityManager.RunningAppProcessInfo next = infoIterator.next();
            try {
                if (next.pid == myUid){
                    processName =next.processName;
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
