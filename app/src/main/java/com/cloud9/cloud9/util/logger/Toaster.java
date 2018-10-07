package com.cloud9.cloud9.util.logger;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
/**
 * author: xb.Zou
 * date: 2018/9/3 0003
 *
 * 在子线程中可以使用该Toast
 * 但必须使用安全Toast的显示方式
 **/
public class Toaster {
    private static int xOffset = 0;
    private static int yOffset = 0;

    private static Context sAppContext;
    private static Toast sToast;
    private static int sGravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private static View sCustomView;
    //设置为主线程Handler，可以在主线程中显示
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    /**
     * 初始化Toast
     * @param context
     */
    public static void initToast(Context context){
        sAppContext = context;
    }

    /**
     * 设置显示位置
     * @param gravity 位置
     */
    public static void setGravity(int gravity, int xOffset, int yOffset){
        sGravity = gravity;
        Toaster.xOffset = xOffset;
        Toaster.yOffset = yOffset;
    }

    /**
     * 设置Toast的View
     * @param layoutId 以id的形式为参数
     */
    public static void setView(@LayoutRes int layoutId){
        LayoutInflater inflater
                = (LayoutInflater) sAppContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sCustomView = inflater.inflate(layoutId, null);
    }

    /**
     * 设置Toast的View
     * @param view 以View形式为参数
     */
    public static void setView(View view){
        sCustomView = view;
    }

    /**
     * 获取Toast所使用View
     * @return
     */
    public static View getView(){
        if (sCustomView != null){
            return sCustomView;
        }
        if (sToast != null){
            return sToast.getView();
        }

        return null;
    }

    /**
     * 安全显示短Toast
     * @param text 吐司内容
     */
    public static void showShortSafe(final CharSequence text){
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全显示短Toast
     * @param stringId 吐司内容的id
     */
    public static void showShortSafe(@StringRes int stringId){
        String text = sAppContext.getResources().getString(stringId);
        showShortSafe(text);
    }

    /**
     * 显示短Toast
     * @param text 吐司内容
     */
    public static void showShort(final CharSequence text){
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短Toast
     * @param stringId 吐司内容的id
     */
    public static void showShort(@StringRes int stringId){
        String text = sAppContext.getResources().getString(stringId);
        showShort(text);
    }

    /**
     * 安全显示长Toast
     * @param text 吐司内容
     */
    public static void showLongSafe(final CharSequence text){
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全显示长Toast
     * @param stringId 吐司内容的id
     */
    public static void showLongSafe(@StringRes int stringId){
        String text = sAppContext.getResources().getString(stringId);
        showShortSafe(text);
    }

    /**
     * 显示长Toast
     * @param text 吐司内容
     */
    public static void showLong(final CharSequence text){
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示长Toast
     * @param stringId 吐司内容的id
     */
    public static void showLong(@StringRes int stringId){
        String text = sAppContext.getResources().getString(stringId);
        showShort(text);
    }


    /**
     * 显示Toast
     * @param text 显示内容
     * @param duration 显示的时间
     */
    private static void show(CharSequence text, int duration){
        cancel();
        if (sCustomView != null){
            sToast = new Toast(sAppContext);
            sToast.setView(sCustomView);
            sToast.setDuration(duration);
        } else {
            sToast = Toast.makeText(sAppContext, text, duration);
        }
        sToast.setGravity(sGravity, xOffset, yOffset);
        sToast.show();
    }

    /**
     * 取消显示
     */
    public static void cancel(){
        if (sToast != null){
            //回收
            sToast.cancel();
            sToast = null;
        }
    }
}
