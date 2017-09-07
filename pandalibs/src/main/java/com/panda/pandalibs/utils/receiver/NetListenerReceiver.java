package com.panda.pandalibs.utils.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

import com.panda.pandalibs.utils.constant.RxConstant;
import com.panda.pandalibs.utils.rxjava.RxBus;

/**
 * Created by win8 -1 on 2015/8/13.
 */
public class NetListenerReceiver extends BroadcastReceiver implements RxConstant {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                boolean isConnected = state == NetworkInfo.State.CONNECTED;// 当然，这边可以更精确的确定状态
                Log.e("H3c", "isConnected" + isConnected);
                if (isConnected) {
                } else {

                }

                RxBus.get().post(CHECKINGIN_WIFI_CONNECTION_OBSERVABLE, isConnected);
            }
        }
    }
}
