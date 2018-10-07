package com.cloud9.cloud9.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;


import com.cloud9.cloud9.app.Cloud9;
import com.cloud9.cloud9.net.callback.IRequest;
import com.cloud9.cloud9.net.callback.ISuccess;
import com.cloud9.cloud9.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;
/**
 * author: xb.Zou
 * date: 2018/9/4 0004
 **/
public class SaveFileTask extends AsyncTask<Object, Void, File>{

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        this.REQUEST = request;
        this.SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream is = body.byteStream();

        if (TextUtils.isEmpty(downloadDir)) {
            downloadDir = "down_loads";
        }

        if (TextUtils.isEmpty(extension)){
            extension = "";
        }

        if (TextUtils.isEmpty(name)){
            return FileUtil.write2Disk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.write2Disk(is, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);

        if (SUCCESS != null){
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null){
            REQUEST.onRequestEnd();
        }
        autoInstallApk(file);
    }

    private void autoInstallApk(File file){
        if (FileUtil.getExtension(file.getPath()).equals("apk")){
            Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Cloud9.getApplicationContext().startActivity(install);
        }
    }
}
