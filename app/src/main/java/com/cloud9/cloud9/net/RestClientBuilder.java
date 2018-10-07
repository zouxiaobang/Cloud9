package com.cloud9.cloud9.net;

import android.content.Context;


import com.cloud9.cloud9.net.callback.IError;
import com.cloud9.cloud9.net.callback.IFailure;
import com.cloud9.cloud9.net.callback.IRequest;
import com.cloud9.cloud9.net.callback.ISuccess;
import com.cloud9.cloud9.widget.loading.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author: xb.Zou
 * date: 2018/9/4 0004
 **/
public class RestClientBuilder {

    private String mUrl;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private IRequest mRequest;
    private ISuccess mSuccess;
    private IFailure mFailure;
    private IError mError;
    private RequestBody mBody;
    private Context mContext;
    private LoaderStyle mStyle;
    private File mFile;
    private String mDownloadDir;
    private String mExtension;
    private String mFileName;

    RestClientBuilder(){

    }

    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(Map<String, Object> params){
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value){
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder raw(String raw){
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest request){
        this.mRequest = request;
        return this;
    }

    public final RestClientBuilder success(ISuccess success){
        this.mSuccess = success;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure){
        this.mFailure = failure;
        return this;
    }

    public final RestClientBuilder error(IError error){
        this.mError = error;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle style){
        this.mContext = context;
        this.mStyle = style;
        return this;
    }

    public final RestClientBuilder loader(Context context){
        this.mContext = context;
        this.mStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    /**
     * 用于上传的文件
     * @param file
     * @return
     */
    public final RestClientBuilder file(File file){
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String filePath){
        file(new File(filePath));
        return this;
    }

    public final RestClientBuilder dir(String dir){
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extension){
        this.mExtension = extension;
        return this;
    }

    /**
     * 下载的文件名
     * @param fileName
     * @return
     */
    public final RestClientBuilder fileName(String fileName){
        this.mFileName = fileName;
        return this;
    }


    public final RestClient build(){
        return new RestClient(mUrl, PARAMS, mDownloadDir, mExtension, mFileName, mRequest, mSuccess, mFailure, mError, mBody, mFile, mContext, mStyle);
    }
}
