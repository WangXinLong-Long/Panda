package com.example.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookProvider extends ContentProvider {

    private SQLiteDatabase db;
    public static final String AUTHORITY = "com.ryg.chapter_2.book.provider";
    public static final String BOOK_CONTENT_URL = "content://" + AUTHORITY + "/book";
    public static final String USER_CONTENT_URL = "content://" + AUTHORITY + "/user";

    public static final int BOOK_CONTENT_CODE = 1;
    public static final int USER_CONTENT_CODE = 2;
    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "book", BOOK_CONTENT_CODE);
        uriMatcher.addURI(AUTHORITY, "user", USER_CONTENT_CODE);
    }

    @Override
    public boolean onCreate() {
        initContentProvider();
        return false;
    }

    private void initContentProvider() {
        db = new DBOpenHelper(getContext()).getWritableDatabase();
        db.execSQL("delete from " + DBOpenHelper.USER_TABLE_NAME);
        db.execSQL("delete from " + DBOpenHelper.BOOK_TABLE_NAME);
        db.execSQL("insert into book values(3,'Android');");
        db.execSQL("insert into book values(4,'IOS');");
        db.execSQL("insert into book values(5,'html');");
        db.execSQL("insert into user values(3,'JAKE',2);");
        db.execSQL("insert into user values(4,'jobs',0);");
    }

    public String getTableName(Uri uri) {
        String name = null;
        int match = uriMatcher.match(uri);
        switch (match) {
            case BOOK_CONTENT_CODE:
                name = DBOpenHelper.BOOK_TABLE_NAME;
                break;

            case USER_CONTENT_CODE:
                name = DBOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;

        }
        return name;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getTableName(uri);
        if (tableName == null){
            throw new IllegalArgumentException("md");
        }
        Cursor query = db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
        return query;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableName(uri);
        if (tableName == null){
            throw new IllegalArgumentException("md");
        }
        long insert = db.insert(tableName, null, values);
        if (insert>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return uri;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (tableName == null){
            throw new IllegalArgumentException("md");
        }
        int delete = db.delete(tableName, selection, selectionArgs);
        if (delete>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return delete;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (tableName == null){
            throw new IllegalArgumentException("md");
        }
        int update = db.update(tableName, values, selection, selectionArgs);
        if (update>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return update;
    }


//    public static final String AUTHORITY = "com.ryg.chapter_2.book.provider";
//    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
//    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");
//    public static final int BOOK_URI_CODE = 0;
//    public static final int USER_URI_CODE = 1;
//    public static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
//    private Context context;
//    private SQLiteDatabase mDB;
//
//    static {
//        matcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
//        matcher.addURI(AUTHORITY, "user", USER_URI_CODE);
//
//    }
//
//    private String getTableName(Uri uri) {
//        String tableName = null;
//        switch (matcher.match(uri)) {
//            case BOOK_URI_CODE:
//                tableName = DBOpenHelper.BOOK_TABLE_NAME;
//                break;
//            case USER_URI_CODE:
//                tableName = DBOpenHelper.USER_TABLE_NAME;
//                break;
//            default:
//
//                break;
//        }
//        return tableName;
//    }
//
//    public BookProvider() {
//    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        // Implement this to handle requests to delete one or more rows.
//        String tableName = getTableName(uri);
//        if (tableName == null) {
//            throw new IllegalArgumentException("wo.cao");
//        }
//        int delete = mDB.delete(tableName, selection, selectionArgs);
//        if (delete > 0) {
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
//        return 0;
//    }
//
//    @Override
//    public String getType(Uri uri) {
//        // at the given URI.
//        return null;
//    }
//
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        String tableName = getTableName(uri);
//        if (tableName == null) {
//            throw new IllegalArgumentException("wo.cao");
//        }
//        mDB.insert(tableName, null, values);
//        context.getContentResolver().notifyChange(uri, null);
//        return uri;
//    }
//
//    @Override
//    public boolean onCreate() {
//        context = getContext();
//        initProviderData();
//        return false;
//    }
//
//    private void initProviderData() {
//        mDB = new DBOpenHelper(context).getWritableDatabase();
////        ExecutorService executorService = Executors.newCachedThreadPool();
////        executorService.execute(new Runnable() {
////            @Override
////            public void run() {
//
//        mDB.execSQL("delete from " + DBOpenHelper.BOOK_TABLE_NAME);
//        mDB.execSQL("delete from " + DBOpenHelper.USER_TABLE_NAME);
//        mDB.execSQL("insert into book values(3,'android');");
//        mDB.execSQL("insert into book values(4,'IOS');");
//        mDB.execSQL("insert into book values(5,'HTML5');");
//        mDB.execSQL("insert into user values(1,'Jack',1);");
//        mDB.execSQL("insert into user values(2,'Jobs',0);");
////            }
////        });
//
//    }
//
//    private static final String TAG = "BookProvider";
//
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection,
//                        String[] selectionArgs, String sortOrder) {
//        Log.i(TAG, "query: " + Thread.currentThread().getName());
//        String tableName = getTableName(uri);
//        if (tableName == null) {
//            throw new IllegalArgumentException("wo.cao");
//        }
//        return mDB.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection,
//                      String[] selectionArgs) {
//        String tableName = getTableName(uri);
//        if (tableName == null) {
//            throw new IllegalArgumentException("wo.cao");
//        }
//        int update = mDB.update(tableName, values, selection, selectionArgs);
//        if (update > 0) {
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
//        return update;
//    }
}
