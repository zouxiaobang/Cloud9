package com.cloud9.cloud9.net;

import android.content.Context;

import com.cloud9.cloud9.net.callback.IError;
import com.cloud9.cloud9.net.callback.IFailure;
import com.cloud9.cloud9.net.callback.IRequest;
import com.cloud9.cloud9.net.callback.ISuccess;
import com.cloud9.cloud9.net.callback.RequestCallback;
import com.cloud9.cloud9.net.download.DownloadHandler;
import com.cloud9.cloud9.widget.loading.CloudLoader;
import com.cloud9.cloud9.widget.loading.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
/**
 * author: xb.Zou
 * date: 2018/9/4 0004
 **/
public class RestClient {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final Context CONTEXT;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;

    public RestClient(String url,
                      Map<String, Object> params,
                      String downloadDir,
                      String extension,
                      String fileName,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      File file,
                      Context context,
                      LoaderStyle style) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = style;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = fileName;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }


    private void request(HttpMethod method) {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null){
            CloudLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        RestService service = RestCreator.getRestService();
        Call<String> call = null;

        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody
                        = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body
                        = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
                break;
            default:
                break;
        }

        if (call != null){
            call.enqueue(getRequestCallback());
        }
    }


    private Callback<String> getRequestCallback(){
        return new RequestCallback(REQUEST, SUCCESS, FAILURE, ERROR, LOADER_STYLE);
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        if (BODY == null){
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("the params must be empty!!!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put(){
        if (BODY == null){
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("the params must be empty!!!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }

    public final void upload(){
        request(HttpMethod.UPLOAD);
    }

    public final void download(){
        new DownloadHandler(URL, REQUEST, SUCCESS, FAILURE, ERROR, DOWNLOAD_DIR, EXTENSION, NAME)
                .handleDownload();
    }
}
