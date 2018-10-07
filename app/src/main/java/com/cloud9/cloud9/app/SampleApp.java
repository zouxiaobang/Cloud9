package com.cloud9.cloud9.app;

import android.app.Application;

import com.cloud9.cloud9.util.logger.Logger;
import com.cloud9.cloud9.util.logger.Toaster;

/**
 * author: xb.Zou
 * date: 2018/9/4 0004
 **/
public class SampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化 -- 必须
        Toaster.initToast(this);
        Logger.initLog(this);
        Cloud9.init(this)
                .withApiHost("http://127.0.0.1/")
                .configure();

        //初始化数据库
        Cloud9.initDao("sample_db");


        Logger.d("APP >> onCreate() >>> the host is "
                + Cloud9.getConfigurator().getConfiguration(ConfigKey.API_HOST));
    }
}
