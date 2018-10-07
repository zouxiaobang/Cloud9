package com.cloud9.cloud9.widget.loading;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;
/**
 * author: xb.Zou
 * date: 2018/9/4 0004
 **/
public class LoaderCreator {
    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();
    private static final String TAG = "LoaderCreator";

    static AVLoadingIndicatorView create(Context context, String type){
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);

        //进行缓存机制的设置
        if (LOADING_MAP.get(type) == null){
            Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type, indicator);
        }

        Log.d(TAG, "create: indicator == " + LOADING_MAP.get(type));
        //从缓存中获取indicator，并设置为一个Loading控件
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    private static Indicator getIndicator(String name){
        if (TextUtils.isEmpty(name)){
            return null;
        }

        final StringBuilder drawableClassName = new StringBuilder();
        //如果传入的只是类名，则要拼接成一个包名加类名
        if (!name.contains(".")){
            //com.wang.avi
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")      //所有的控件都在该包下
                    .append(".");
        }
        //eg: com.wang.avi.indicators.BallPulseIndicator
        drawableClassName.append(name);
        Log.d(TAG, "getIndicator: name = " + drawableClassName.toString());

        try {
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
