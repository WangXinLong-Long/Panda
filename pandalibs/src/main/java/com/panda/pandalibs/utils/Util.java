package com.panda.pandalibs.utils;

import android.app.Activity;
import android.graphics.Rect;

import java.lang.reflect.Field;

/**
 * Created by wangxinlong on 2017/8/29.
 */

public class Util {

    public static int getTop(Activity activity) {
        Rect rectF = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectF);
        if (rectF.top == 0) {
            try {
                Class aClass = Class.forName("com.android.internal.R$dimen");
                Object o = aClass.newInstance();
                Field status_bar_height = aClass.getField("status_bar_height");
                int x = Integer.parseInt(status_bar_height.get(o).toString());
                return activity.getResources().getDimensionPixelOffset(x);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return rectF.top;
    }
}
