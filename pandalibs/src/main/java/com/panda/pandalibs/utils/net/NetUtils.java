package com.panda.pandalibs.utils.net;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.panda.pandalibs.App;
import com.panda.pandalibs.utils.receiver.NetListenerReceiver;
import com.panda.pandalibs.utils.tools.Utils;


/**
 * 获取网络连接状态
 * Created by xiaoyu.zhang on 2016/12/6 13:22
 * Email:zhangxyfs@126.com
 *  
 */
public class NetUtils {
    private static NetListenerReceiver netListenerReceiver;
    private static boolean isRegister = true;

    public static NetState getNetState() {
        NetState stateCode = NetState.NET_NO;
        ConnectivityManager cm = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {
            switch (ni.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    stateCode = NetState.NET_WIFI;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    switch (ni.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS: //联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: //电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: //移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            stateCode = NetState.NET_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: //电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            stateCode = NetState.NET_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            stateCode = NetState.NET_4G;
                            break;
                        default:
                            stateCode = NetState.NET_UNKNOWN;
                    }
                    break;
                default:
                    stateCode = NetState.NET_UNKNOWN;
            }

        }
        return stateCode;
    }

    public enum NetState {
        NET_NO,//无网络
        NET_2G,
        NET_3G,
        NET_4G,
        NET_WIFI,
        NET_UNKNOWN//未知
    }

    public static void netWorkListener(Context context, NetCallback callback) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP && !Build.MANUFACTURER.contains("360")) {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onAvailable(Network network) {
                        super.onAvailable(network);
                        callback.onAvailable();
                    }

                    @Override
                    public void onLost(Network network) {
                        super.onLost(network);
                        callback.onLost();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (netListenerReceiver == null) {
                netListenerReceiver = new NetListenerReceiver();
                IntentFilter mFilter = new IntentFilter();
                mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
                context.registerReceiver(netListenerReceiver, mFilter);
                isRegister = true;
            }
        }
    }

    public static void unnetWorkListener(Context context) {
        if (netListenerReceiver != null && context != null && isRegister)
            try {
                context.unregisterReceiver(netListenerReceiver);
                netListenerReceiver = null;
                isRegister = false;
            } catch (Exception e) {
                Utils.log("tag", "Receiver not registered:");
            }
    }

    public interface NetCallback {
        void onAvailable();
        void onLost();
    }

    // 是否没有网络
    public static boolean isNoNetState() {
        return NetUtils.NetState.NET_NO == getNetState();
    }

}
