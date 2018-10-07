package com.cloud9.cloud9.mvp.sample;

import android.content.Context;

import com.cloud9.cloud9.base.BasePresenter;
import com.cloud9.cloud9.bean.event.SampleEvent;
import com.cloud9.cloud9.bean.event.SampleResponseEvent;
import com.cloud9.cloud9.bean.event.SampleUserInfo;
import com.cloud9.cloud9.dao.entity.UserInfo;
import com.cloud9.cloud9.util.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * author: xb.Zou
 * date: 2018/9/3 0003
 **/
public class SamplePresenter extends BasePresenter<ISampleView, SampleBiz> {
    public SamplePresenter(){
        attachBiz(new SampleBiz());

        //注册EventBus
        EventBus.getDefault().register(this);
    }

    /**
     * 开始进行计算
     * @param i 计算所需参数
     */
    void realCal(int i) {
        mBiz.realCal(i);
    }

    /**
     * 连接百度
     */
    void connectBaidu(Context context) {
        mBiz.connect(context, "https://www.baidu.com/");
    }

    void insertData() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("sample name");
        userInfo.setAgo(20);
        userInfo.setSex(1);
        mBiz.insertData(userInfo);
    }

    void deleteData() {

    }

    void updateData() {

    }

    void queryData() {

    }






    /**
     * 接收计算返回的事件
     * @param event 计算的事件
     */
    @Subscribe
    public void onCalResult(SampleEvent event){
        Logger.i(event.getNum()+"");
        mView.onSuccess(1, event.getNum() + "");
    }

    /**
     * 接收网络连接返回的事件
     * @param event 网络返回事件
     */
    @Subscribe
    public void onResponse(SampleResponseEvent event){
        Logger.i((event.getFlag() == 200) + "");
        if (event.getFlag() == 200){
            //成功
            mView.onSuccess(200, event.getContent());
        } else {
            if (event.getCode() == 1000){
                mView.onError(event.getCode(), "连接失败");
            } else {
                mView.onError(event.getCode(), event.getErrMsg());
            }
        }
    }


    @Subscribe
    public void onUserInfoDbOpera(SampleUserInfo sampleUserInfo){
        if (sampleUserInfo.getType() == SampleUserInfo.INSERT){
            mView.displayDbResult("name: " + sampleUserInfo.getUserInfo().getName());
        }
    }

}
