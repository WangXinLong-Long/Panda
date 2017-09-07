package com.panda.pandalibs.utils.cache;

import android.content.Context;
import android.text.TextUtils;

import com.panda.pandalibs.App;
import com.panda.pandalibs.utils.tools.Utils;


/**
 * Created by xiaoyu.zhang on 2016/11/7 13:56
 */
public class OtherDataExtra extends OtherDataProvider {

    /**
     * 是否第一次打开应用
     *
     * @return
     */
    public static boolean isFirstOpenApp() {
        String txt = queryByTitle(App.getContext(), ISFIRSTOEPNAPP_KEY);
        if (TextUtils.isEmpty(txt)) {
            return true;
        }
        return false;
    }

    public static void saveFirstOpenApp(Context context) {
        delete(context, ISFIRSTOEPNAPP_KEY);
        insertUpDate(context, ISFIRSTOEPNAPP_KEY, "true");
    }

    public static void saveLastAppVersion() {
        delete(App.getContext(), LAST_APP_VERSION_KEY);
        insertUpDate(App.getContext(), LAST_APP_VERSION_KEY, String.valueOf(Utils.getVersionCode(App.getContext())));
    }

    public static int getLastAppVersion() {
        String txt = queryByTitle(App.getContext(), LAST_APP_VERSION_KEY);
        if (TextUtils.isEmpty(txt)) {
            return 0;
        }
        return Integer.parseInt(txt);
    }

    public static boolean isJustStartHightLoadService() {
        String str = queryByTitle(App.getContext(), IS_JUST_START_HIGHTLOADSERVICE);
        return TextUtils.isEmpty(str);
    }

    public static void saveJustStartHightLoadService(String str) {
        delete(App.getContext(), IS_JUST_START_HIGHTLOADSERVICE);
        insertUpDate(App.getContext(), IS_JUST_START_HIGHTLOADSERVICE, str == null ? "" : str);
    }


    /**
     * 是否第一次启动
     *
     * @param context
     * @return
     */
    public static boolean isFirstLaunched(Context context) {
        String txt = queryByTitle(context, IS_ALREADY_LAUNCH);
        if (TextUtils.isEmpty(txt)) {
            return false;
        }
        return true;
    }

    /**
     * 保存apk路径
     *
     * @param context
     * @param path
     */
    public static void saveDownloadApk(Context context, String path) {
        delete(context, DOWNLOADAPK_KEY);
        insertUpDate(context, DOWNLOADAPK_KEY, path);
    }

    public static String getDownloadApk(Context context) {
        return queryByTitle(context, DOWNLOADAPK_KEY);
    }

    public static void saveDownloadApkId(Context context, long id) {
        delete(context, DOWNLOADAPKID_KEY);
        insertUpDate(context, DOWNLOADAPKID_KEY, String.valueOf(id));
    }

    public static long getDownloadApkId(Context context) {
        String id = queryByTitle(context, DOWNLOADAPKID_KEY);
        return Utils.isNumber(id) ? Long.parseLong(id) : -1;
    }

    public static void saveJoinForground(Context context, boolean isForground) {
        String value = isForground ? "1" : "0";
        delete(context, IS_JOIN_FORGROUND_KEY);
        insertUpDate(context, IS_JOIN_FORGROUND_KEY, value);
    }

    public static boolean isJoinForground(Context context) {
        String value = queryByTitle(context, IS_JOIN_FORGROUND_KEY);
        return TextUtils.equals(value, "1");
    }

    public static void saveKeyBoradHeight(int height) {
        delete(App.getContext(), KEYBOARD_HEIGHT_KEY);
        insertUpDate(App.getContext(), KEYBOARD_HEIGHT_KEY, String.valueOf(height));
    }

    public static int getKeyBroadHeight() {
        String value = queryByTitle(App.getContext(), KEYBOARD_HEIGHT_KEY);
        return Utils.isNumber(value) ? Integer.parseInt(value) : 0;
    }

    public static String getQRCodeFilePath(String qrcode) {
        return queryByTitle(App.getContext(), qrcode + QRCODE_PATH_KEY);
    }

    public static void saveQRCodeFilePath(String qrcode, String path) {
        delete(App.getContext(), qrcode + QRCODE_PATH_KEY);
        insertUpDate(App.getContext(), qrcode + QRCODE_PATH_KEY, path);
    }

    public static void saveIMPushHasSound(boolean b) {
        delete(App.getContext(), SPreference.getUserId() + IM_PUSH_HAS_SOUND_KEY);
        insertUpDate(App.getContext(), SPreference.getUserId() + IM_PUSH_HAS_SOUND_KEY, b ? "true" : "false");
    }

    public static boolean getIMPushHasSound() {
        String value = queryByTitle(App.getContext(), SPreference.getUserId() + IM_PUSH_HAS_SOUND_KEY);
        if (TextUtils.isEmpty(value)) {
            saveIMPushHasSound(true);
            return true;
        }
        return TextUtils.equals(value, "true");
    }

    public static void saveIMPushHasVibration(boolean b) {
        delete(App.getContext(), SPreference.getUserId() + IM_PUSH_HAS_VIBRATION_KEY);
        insertUpDate(App.getContext(), SPreference.getUserId() + IM_PUSH_HAS_VIBRATION_KEY, b ? "true" : "false");
    }

    public static boolean getIMPushHasVibration() {
        String value = queryByTitle(App.getContext(), SPreference.getUserId() + IM_PUSH_HAS_VIBRATION_KEY);
        if (TextUtils.isEmpty(value)) {
            saveIMPushHasVibration(true);
            return true;
        }
        return TextUtils.equals(value, "true");
    }
}
