package com.cloud9.cloud9.net.callback;

import android.os.Handler;


import com.cloud9.cloud9.widget.loading.CloudLoader;
import com.cloud9.cloud9.widget.loading.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: xb.Zou
 * date: 2018/9/4 0004
 **/
public class RequestCallback implements Callback<String> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = new Handler();

    public RequestCallback(IRequest request,
                           ISuccess success,
                           IFailure failure,
                           IError error,
                           LoaderStyle style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            if (call.isExecuted()){
                if (SUCCESS != null){
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null){
                ERROR.onError(response.code(), response.message());
            }
        }

        stopLoading();
    }

    private void stopLoading() {
        if (LOADER_STYLE != null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    CloudLoader.stopLoading();
                }
            }, 1000);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null){
            FAILURE.onFailure();
        }

        if (REQUEST != null){
            REQUEST.onRequestEnd();
        }

        stopLoading();
    }
}
