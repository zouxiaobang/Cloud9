package com.cloud9.cloud9.app;

import android.content.Context;


import java.util.ArrayList;
import java.util.WeakHashMap;

import okhttp3.Interceptor;
/**
 * author: xb.Zou
 * date: 2018/9/4 0004
 * 进行配置文件的存储及获取
 **/
public class Configurator {

    private static final WeakHashMap<String, Object> LATTE_CONFIGS = new WeakHashMap<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        //参数尚未初始化
        LATTE_CONFIGS.put(ConfigKey.CONFIG_READY.name(), false);
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final WeakHashMap<String, Object> getLatteConfigs(){
        return LATTE_CONFIGS;
    }


    //----------------------------------------------------------------------------------------
    /**
     * 参数配置完成
     */
    public final void configure() {
        LATTE_CONFIGS.put(ConfigKey.CONFIG_READY.name(), true);
    }

    /**
     * 配置全局上下文
     * 只在本包中使用
     * 即只提供给Configuration调用
     * @param context
     * @return
     */
    final Configurator withContext(Context context){
        LATTE_CONFIGS.put(ConfigKey.APPLICATION_CONTEXT.name(), context);
        return this;
    }

    /**
     * 配置网络域名
     * @param host
     * @return
     */
    public final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigKey.API_HOST.name(), host);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKey.INTERCEPTOR.name(), INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptor(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKey.INTERCEPTOR.name(), INTERCEPTORS);
        return this;
    }





    /**
     * 检测是否参数已经配置完成
     * 该方法在获取参数的时候才使用
     */
    private void checkConfiguration(){
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKey.CONFIG_READY.name());

        if (!isReady){
            throw new RuntimeException("The configuration is not ready now, please call the Configurator.configure() method");
        }
    }

    public final <T> T getConfiguration(Enum<ConfigKey> key){
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key.name());
    }
}
