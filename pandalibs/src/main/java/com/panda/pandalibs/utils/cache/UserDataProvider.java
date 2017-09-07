package com.panda.pandalibs.utils.cache;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.panda.pandalibs.App;
import com.panda.pandalibs.utils.tools.Base64Util;
import com.panda.pandalibs.utils.tools.Utils;

import java.util.HashMap;

/**
 * Created by win8 on 2016/4/18.
 */
public class UserDataProvider extends ContentProvider implements CPConstant {
    private DBOpenHelper dbHelper;
    // Uri工具类
    private static final UriMatcher sUriMatcher;
    // 查询、更新条件
    private static final int EMPLOYEE = 1;
    private static final int EMPLOYEE_ID = 2;
    // 查询列集合
    private static HashMap<String, String> empProjectionMap;
//    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // Uri匹配工具类
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(UserData.AUTHORITY, "user", EMPLOYEE);
        sUriMatcher.addURI(UserData.AUTHORITY, "user/#", EMPLOYEE_ID);
        // 实例化查询列集合
        empProjectionMap = new HashMap<>();
        // 添加查询列
        empProjectionMap.put(UserData.user.TITLE, UserData.user.TITLE);
        empProjectionMap.put(UserData.user.VALUE, UserData.user.VALUE);
    }


    @Override
    public boolean onCreate() {
        this.dbHelper = new DBOpenHelper(this.getContext());
        dbHelper.onCreate(dbHelper.getWritableDatabase());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            // 查询所有
            case EMPLOYEE:
                qb.setTables(DBOpenHelper.TABLE_NAME_USER);
                qb.setProjectionMap(empProjectionMap);
                break;
            // 根据ID查询
            case EMPLOYEE_ID:
                qb.setTables(DBOpenHelper.TABLE_NAME_USER);
                qb.setProjectionMap(empProjectionMap);
                qb.appendWhere(UserData.user._ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Uri错误！ " + uri);
        }
        // 获得数据库实例
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // 返回游标集合
        try {
            Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            if (c != null) {
                c.setNotificationUri(getContext().getContentResolver(), uri);
                return c;
            }
            return null;
        } catch (Exception ex) {
            Log.d("Contacts", ex.toString());
        }
        return null;

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // 获得数据库实例
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 插入数据，返回行ID
        long rowId = db.insert(DBOpenHelper.TABLE_NAME_USER, UserData.user.TITLE, values);
        // 如果插入成功返回uri
        if (rowId > 0) {
            Uri empUri = ContentUris.withAppendedId(UserData.user.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(empUri, null);
            return empUri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // 获得数据库实例
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 获得数据库实例
        int count;
        switch (sUriMatcher.match(uri)) {
            // 根据指定条件删除
            case EMPLOYEE:
                count = db.delete(DBOpenHelper.TABLE_NAME_USER, selection, selectionArgs);
                break;
            // 根据指定条件和ID删除
            case EMPLOYEE_ID:
                String noteId = uri.getPathSegments().get(1);
                count = db.delete(DBOpenHelper.TABLE_NAME_USER, UserData.user._ID + "=" + noteId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("错误的 URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // 获得数据库实例
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            // 根据指定条件更新
            case EMPLOYEE:
                count = db.update(DBOpenHelper.TABLE_NAME_USER, values, selection, selectionArgs);
                break;
            // 根据指定条件和ID更新
            case EMPLOYEE_ID:
                String noteId = uri.getPathSegments().get(1);
                count = db.update(DBOpenHelper.TABLE_NAME_USER, values, UserData.user._ID + "=" + noteId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("错误的 URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private static String query(String key) {
        // 查询列数组
        String[] PROJECTION = new String[]{
                key
        };
        String value = "";

        Cursor cursor = App.getContext().getContentResolver().query(UserData.user.CONTENT_URI, PROJECTION, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // 遍历游标
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    value = cursor.getString(0);
                }
            }
            cursor.close();
            cursor = null;
        }
        return value;
    }

    /**
     * 获取是否登陆
     *
     * @return
     */
    public static Boolean queryLoginFlag() {
        String flag = queryByTitle(USER_LOGINFLAG_KEY);
        if (flag.equals("1")) {
            return true;
        }
        return false;
    }

    public static void quitLogin() {
        delete(USER_LOGINFLAG_KEY);
        delete(USER_ID_KEY);
        delete(USER_INFO_KEY);
    }

    public static void saveUserId(Long uid) {
        delete(USER_ID_KEY);
        insertUpDate(USER_ID_KEY, String.valueOf(uid));
    }

    public static Long getUserId() {
        Long uid = null;
        String id = queryByTitle(USER_ID_KEY);
        if (!TextUtils.isEmpty(id) && Utils.isNumber(id)) {
            uid = Long.parseLong(id);
        }
        return uid;
    }

    public static void savePrivacy(String privacyLocation) {
        delete(PRIVACY_LOCATION_KEY);
        insertUpDate(PRIVACY_LOCATION_KEY, privacyLocation);
    }

    public static String getPrivacy() {
        String privacy = null;
        String privacy_ = queryByTitle(PRIVACY_LOCATION_KEY);
        if (!TextUtils.isEmpty(privacy_) && Utils.isNumber(privacy_)) {
            privacy = privacy_;
        }
        return privacy;
    }

    public static void setPictureSaveInLocal(Boolean saveOrNot) {
        delete(SAVE_PICTURE);
        insertUpDate(SAVE_PICTURE, saveOrNot ? "1" : "0");
    }

    public static boolean getPictureSaveInLocal() {
        String flag = queryByTitle(SAVE_PICTURE);
        if (flag.equals("0")) {
            return false;
        }
        return true;
    }

    public static void setVideoSaveInLocal(Boolean saveOrNot) {
        delete(SAVE_VIDEO);
        insertUpDate(SAVE_VIDEO, saveOrNot ? "1" : "0");
    }

    public static boolean getVideoSaveInLocal() {
        String flag = queryByTitle(SAVE_VIDEO);
        if (flag.equals("0")) {
            return false;
        }
        return true;
    }

    public static void saveLoginName(String loginName) {
        delete(USER_LOGIN_NAME);
        insertUpDate(USER_LOGIN_NAME, loginName);
    }

    public static void saveUserStatsMTC(String loginName) {
        delete(USER_INFO_SMTC_KEY);
        insertUpDate(USER_INFO_SMTC_KEY, loginName);
    }

    public static String getUserStatsMTC() {
        return queryByTitle(USER_INFO_SMTC_KEY);
    }

    public static String getLoginName() {
        return queryByTitle(USER_LOGIN_NAME);
    }

    public static void saveChatLoginName(String loginName) {
        delete(CHAT_LOGIN_NAME);
        insertUpDate(CHAT_LOGIN_NAME, loginName);
    }

    public static String getChatLoginName() {
        return queryByTitle(CHAT_LOGIN_NAME);
    }

    public static void saveChatToken(String token) {
        delete(CHAT_LOGIN_TOKEN);
        insertUpDate(CHAT_LOGIN_TOKEN, TextUtils.isEmpty(token) ? "" : token);
    }

    public static String getChatToken() {
        return queryByTitle(CHAT_LOGIN_TOKEN);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static String queryUserInfoData() {
        String json = queryByTitle(USER_INFO_KEY);
        if (!TextUtils.isEmpty(json)) {
            return json;
        }
        return null;
    }

    public static void saveUserInfo(String json) {
        delete(USER_INFO_KEY);
        insertUpDate(USER_INFO_KEY, json);
    }

    public static void saveLoginFlag(boolean flag) {
        delete(USER_LOGINFLAG_KEY);
        insertUpDate(USER_LOGINFLAG_KEY, flag ? "1" : "0");
    }

    public static void saveDefaultCompanyId(Long companyId) {
        delete(getUserId() + COMPANY_ID_KEY);
        insertUpDate(getUserId() + COMPANY_ID_KEY, String.valueOf(companyId));
    }

    public static void saveDefaultCompanyName(String name) {
        delete(getUserId() + COMPANY_NAME_KEY);
        insertUpDate(getUserId() + COMPANY_NAME_KEY, name);
    }

    public static Long getDefaultCompanyId() {
        String id = queryByTitle(getUserId() + COMPANY_ID_KEY);
        return TextUtils.isEmpty(id) ? -1L : Long.parseLong(Utils.isNumber(id) ? id : "-1");
    }

    public static String getDefaultCompanyName() {
        return queryByTitle(getUserId() + COMPANY_NAME_KEY);
    }


    public static void clear() {
        App.getContext().getContentResolver().delete(UserData.user.CONTENT_URI, null, null);
    }


    public static String queryByTitle(@NonNull String title) {
        String base64 = "";

        try {
            Cursor cursor = App.getContext().getContentResolver().query(UserData.user.CONTENT_URI, null, UserData.user.TITLE + "=?", new String[]{title}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    if (Build.VERSION.SDK_INT < 26) {
                        // 遍历游标
                        for (int i = 0; i < cursor.getCount(); i++) {
                            cursor.moveToPosition(i);
                            base64 = cursor.getString(0);
                        }
                    } else {
                        base64 = cursor.getString(1);
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(base64)) {
            return Base64Util.fromBase64(base64, Base64.DEFAULT);
        }
        return base64;
    }

    public static void insertUpDate(@NonNull String title, @NonNull String content) {
        String base64 = Base64Util.toBase64(content, Base64.DEFAULT);

        try {
            String value = queryByTitle(title);
            ContentValues values = new ContentValues();
            if (TextUtils.isEmpty(value)) {
                values.put(UserData.user.TITLE, title);
                values.put(UserData.user.VALUE, base64);
                // 插入
                App.getContext().getContentResolver().insert(UserData.user.CONTENT_URI, values);
            } else {
                //更新
                values.put(UserData.user.VALUE, base64);
                App.getContext().getContentResolver().update(UserData.user.CONTENT_URI, values, UserData.user.TITLE + "=?", new String[]{title});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(String title) {
        String value = queryByTitle(title);
        if (!TextUtils.isEmpty(value)) {
            try {
                App.getContext().getContentResolver().delete(UserData.user.CONTENT_URI, UserData.user.TITLE + "=?", new String[]{title});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
