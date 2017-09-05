package com.panda.pandalibs.utils.utils.tools;

import com.panda.pandalibs.App;
import com.panda.pandalibs.utils.SPreference;
import com.panda.pandalibs.utils.net.NetConfig;

/**
 * Created by wangxinlong on 2017/9/5.
 */

public class Utils {
    public static boolean isDebug() {
        if (App.getContext() != null) {
            boolean isCouldOpen = SPreference.getOpenJsonLog();
            if (isCouldOpen) {
                return true;
            } else if (NetConfig.isLocal) {
                return true;
            }
        }
        return false;
    }
}
