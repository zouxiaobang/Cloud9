package com.cloud9.cloud9.mvp.sample;

import android.content.Context;
import android.util.Log;

import com.cloud9.cloud9.app.Cloud9;
import com.cloud9.cloud9.base.BaseBiz;
import com.cloud9.cloud9.bean.event.SampleEvent;
import com.cloud9.cloud9.bean.event.SampleResponseEvent;
import com.cloud9.cloud9.bean.event.SampleUserInfo;
import com.cloud9.cloud9.dao.entity.UserInfo;
import com.cloud9.cloud9.net.RestClient;
import com.cloud9.cloud9.net.callback.IError;
import com.cloud9.cloud9.net.callback.IFailure;
import com.cloud9.cloud9.net.callback.ISuccess;
import com.cloud9.cloud9.util.logger.Logger;

import org.greenrobot.eventbus.EventBus;

/**
 * author: xb.Zou
 * date: 2018/9/3 0003
 **/
public class SampleBiz extends BaseBiz {
    void realCal(int i) {
        SampleEvent event = new SampleEvent();
        event.setNum(i + 1);
        EventBus.getDefault().post(event);
    }

    void connect(Context context, String url) {
        final SampleResponseEvent event = new SampleResponseEvent();
        Logger.d("xb.zou: connect: " + url);

        RestClient.builder()
                .url("https://www.baidu.com/")
                .loader(context)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        event.setFlag(200);
                        event.setContent(response);
                        Logger.i(response);
                        EventBus.getDefault().post(event);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        event.setCode(1000);
                        Logger.i("failure");
                        EventBus.getDefault().post(event);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        event.setCode(code);
                        event.setErrMsg(msg);
                        Logger.i(code + " : " + msg);
                        EventBus.getDefault().post(event);
                    }
                })
                .build()
                .get();
    }

    void insertData(UserInfo userInfo) {
        long id = Cloud9.getDaoSession().getUserInfoDao().insert(userInfo);
        if (id < 0) {
            return;
        }
        SampleUserInfo sampleUserInfo = new SampleUserInfo();
        sampleUserInfo.setUserInfo(userInfo);
        sampleUserInfo.setType(SampleUserInfo.INSERT);
        EventBus.getDefault().post(sampleUserInfo);
    }
}
