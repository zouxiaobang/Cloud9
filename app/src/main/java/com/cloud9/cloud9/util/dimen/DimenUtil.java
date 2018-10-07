package com.cloud9.cloud9.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.cloud9.cloud9.app.Cloud9;

/**
 * author: xb.Zou
 * date: 2018/9/4 0004
 **/
public class DimenUtil {

    /**
     * 获取屏幕的宽度
     * @return
     */
    public static int getScreenWidth() {
        final Resources resources = Cloud9.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(){
        final Resources resources = Cloud9.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
