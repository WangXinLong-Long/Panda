package com.panda.mvp.ui.test;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.widget.RemoteViews;

import com.panda.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wxl
 * @date on 2017/11/20.
 */

public class MyAppWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "MyAppWidgetProvider";
    private static final String CLICK_ACTION = "click.action";

    public MyAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onReceive: ");

        if (intent.getAction().equals(CLICK_ACTION)){

            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_contact_add);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    for (int i = 0; i < 37; i++) {
                        float degree = (i*10)/360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);
                        remoteViews.setImageViewBitmap(R.id.image_view,rotateBitmap(degree,bitmap,context));
                        Intent clickIntent = new Intent();
                        clickIntent.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,clickIntent,0);
                        remoteViews.setOnClickPendingIntent(R.id.image_view,pendingIntent);
                        appWidgetManager.updateAppWidget(new ComponentName(context,MyAppWidgetProvider.class),remoteViews);
                    }
                }
            });
        }
    }

    private Bitmap rotateBitmap(float degree, Bitmap bitmap, Context context) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap destBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return destBitmap;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        int length = appWidgetIds.length;
        for (int i = 0; i < length; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(appWidgetId,context,appWidgetManager);
        }
    }

    private void onWidgetUpdate(int appWidgetId,Context context,AppWidgetManager appWidgetManager) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);
        Intent clickIntent = new Intent();
        clickIntent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,clickIntent,0);
        remoteViews.setOnClickPendingIntent(R.id.image_view,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
    }
}
