package com.panda.mvp.ui.test;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.panda.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wxl
 * @date on 2017/11/20.
 */

public class MyAppWidgetProvider extends AppWidgetProvider {

    public static final String click_action = "com.ryg.chapter_5.action.CLICK";
    public MyAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(click_action)){
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_things_add_member);
                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    for (int i = 0; i < 37; i++) {
                        float degree= (i * 10) % 360;

                    };
                }
            });
        }else {

        }

    }
}
