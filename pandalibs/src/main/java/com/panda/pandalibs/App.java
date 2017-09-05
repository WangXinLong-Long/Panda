package com.panda.pandalibs;


import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.support.multidex.MultiDexApplication;

import com.panda.pandalibs.utils.utils.IMClient;

import java.util.Iterator;
import java.util.List;

/**
 * Created by wangxinlong on 2017/8/28.
 */

public class App extends MultiDexApplication {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
//        context = getApplicationContext();
        initIMClient();
    }

    private void initIMClient() {
        int myPid = Process.myPid();
        String processAppName = getAppName(myPid);
        String packageCodePath = getPackageName();
        if ( !processAppName.equalsIgnoreCase(packageCodePath)){
            return;
        }
        if (processAppName == null ) {
            return;
        }
        IMClient.imInit(this);
    }
    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     *
     * @param pid 进程的id
     * @return 返回进程的名字
     */
    private String getAppName(int pid) {
        String processName = null;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    processName = info.processName;
                    // 返回当前进程名
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }

    public static Context getContext() {
        return context;
    }
}
