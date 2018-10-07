package com.cloud9.cloud9.util.logger;

import android.util.Log;

/**
 * author: xb.Zou
 * date: 2018/9/3 0003
 **/
public class Logger {
    private static String mTag ;

    /**
     * 初始化Log工具
     * @param obj 对应类
     */
    public static void initLog(Object obj){
        mTag = getTAG(obj);

    }

    /**
     * 设置自定义TAG
     * @param tag 自定义TAG
     */
    public static void tag(String tag){
        mTag = tag;
    }

    public static void i(String msg){
        Log.i(mTag, msg);
    }

    public static void i(int flag, String msg){
        Log.i(mTag, flag + " : " + msg);
    }

    public static void d(String msg){
        Log.d(mTag, msg);
    }

    public static void e(String errMsg, Throwable throwable){
        Log.e(mTag, errMsg, throwable);
    }

    public static void e(String errMsg){
        Log.e(mTag, errMsg);
    }

    public static void e(int code, String errMsg){
        Log.e(mTag, code + " : " + errMsg);
    }

    public static void w(String warnMsg){
        Log.w(mTag, warnMsg);
    }

    /**
     * 获取TAG
     * @param object 以对应类的类名为TAG
     * @return
     */
    private static String getTAG(Object object){
        return object.getClass().getSimpleName();
    }
}
