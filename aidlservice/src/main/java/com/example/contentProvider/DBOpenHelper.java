package com.example.contentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author wxl
 * @date on 2017/12/20.
 * @describe:
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "book_provider.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";
    public static final int DB_VERSION = 1;
    private String Create_book_table = "Create table if not exists " + BOOK_TABLE_NAME + "(_id integer primary key," + "name TEXT)";
    private String Create_user_table = "Create table if not exists " + USER_TABLE_NAME + "(_id integer primary key," + "name TEXT,sex INT)";

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_book_table);
        db.execSQL(Create_user_table);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
