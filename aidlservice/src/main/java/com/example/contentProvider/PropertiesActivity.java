package com.example.contentProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.aidlservice.Book;
import com.example.aidlservice.R;
import com.example.model.User;

public class PropertiesActivity extends AppCompatActivity {
    private static final String TAG = "PropertiesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);

/**
 * 这里花了2H ，原因是使用的版本号不对，而且 调用的是 getContentResolver().query(uri,null,null,null）
 * 报： java.lang.NoSuchMethodError: No virtual method query(Landroid/net/Uri;[Ljava/lang/String;Landroid/os/Bundle;Landroid/os/CancellationSignal;)Landroid/database/Cursor; in class Landroid/content/ContentResolver; or its super classes (declaration of 'android.content.ContentResolver' appears in /system/framework/framework.jar)
 * 解决办法是 getContentResolver().query(uri,null,null,null,null);，并且把版本号修正为build.gradle修正
 */
//        Uri uri = Uri.parse("content://com.ryg.chapter_2.book.provider/book");
//        getContentResolver().query(uri,null,null,null,null);
//        getContentResolver().query(uri,null,null,null,null);
//        getContentResolver().query(uri,null,null,null,null);

        Uri bookUri = Uri.parse("content://com.ryg.chapter_2.book.provider/book");
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", 6);
        contentValues.put("name", "程序设计艺术");
        getContentResolver().insert(bookUri, contentValues);

        Cursor query = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null, null);
        while (query.moveToNext()) {
            Book book = new Book();
            book.bookId = query.getInt(0);
            book.bookName = query.getString(1);
            Log.i(TAG, "query book: " + book.toString());
        }
        query.close();


        Uri userUri = Uri.parse("content://com.ryg.chapter_2.book.provider/user");

        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        while (userCursor.moveToNext()) {
            User user = new User();
            user.userId = userCursor.getInt(0);
            user.name = userCursor.getString(1);
            user.sex = userCursor.getInt(2) == 1;
            Log.i(TAG, "query user: " + user.toString());
        }
        userCursor.close();
    }
}
