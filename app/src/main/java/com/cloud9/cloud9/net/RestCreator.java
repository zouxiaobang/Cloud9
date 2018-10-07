package com.cloud9.cloud9.net;



import com.cloud9.cloud9.app.Cloud9;
import com.cloud9.cloud9.app.ConfigKey;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
/**
 * author: xb.Zou
 * date: 2018/9/4 0004
 **/
public class RestCreator {

    private static final class ParamsHolder{
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String, Object> getParams(){
        return ParamsHolder.PARAMS;
    }

    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }


    private static final class RetrofitHolder{
        private static final String BASE_URL =
                (String) Cloud9.getConfigurator().getConfiguration(ConfigKey.API_HOST);

        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class OkHttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS
                = Cloud9.getConfigurator().getConfiguration(ConfigKey.INTERCEPTOR);

        private static final OkHttpClient.Builder addIntercceptor(){
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor: INTERCEPTORS){
                    BUILDER.addInterceptor(interceptor);
                }
            }

            return BUILDER;
        }
        private static final OkHttpClient OK_HTTP_CLIENT =
                addIntercceptor()
                        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                        .build();
    }

    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
}
