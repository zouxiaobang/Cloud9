package com.cloud9.cloud9.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cloud9.cloud9.dao.gen.DaoMaster;
import com.cloud9.cloud9.dao.gen.DaoSession;

/**
 * author: xb.Zou
 * date: 2018/9/4 0004
 **/
public class Cloud9 {
    private static SQLiteDatabase sDatabase;
    private static DaoSession sDaoSession;

    public static Configurator init(Context context){
        return Configurator.getInstance().withContext(context);
    }

    public static Configurator getConfigurator(){
        return Configurator.getInstance();
    }

    /**
     * 获取Application的Context
     * @return App的上下文Context
     */
    public static Context getApplicationContext(){
        return (Context) Configurator.getInstance().getConfiguration(ConfigKey.APPLICATION_CONTEXT);
    }

    /**
     * 初始化数据库
     * @param dbName 数据库名称
     */
    public static void initDao(String dbName){
        if (getApplicationContext() == null){
            throw new RuntimeException("请在初始化数据库之前先初始化Cloud9的参数（Context）");
        }

        DaoMaster.DevOpenHelper dbHelper
                = new DaoMaster.DevOpenHelper(getApplicationContext(), dbName, null);
        sDatabase = dbHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sDatabase);
        sDaoSession = daoMaster.newSession();
    }

    /**
     * 获取数据库的Session
     * @return Dao的Session
     */
    public static DaoSession getDaoSession(){
        if (sDaoSession == null){
            throw new RuntimeException("请先初始化数据库");
        }

        return sDaoSession;
    }

    /**
     * 获取数据库
     * @return 数据库
     */
    public static SQLiteDatabase getDatabase() {
        if (sDatabase == null){
            throw new RuntimeException("请先初始化数据库");
        }

        return sDatabase;
    }
}
