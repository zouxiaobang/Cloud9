package com.cloud9.cloud9.base;

/**
 * author: xb.Zou
 * date: 2018/9/3 0003
 * View层
 **/
public interface IView {
    /**
     * 成功回调信息
     * @param flag 成功回调的类型
     * @param msg 详细信息
     */
    void onSuccess(int flag, String msg);

    /**
     * 失败回调信息
     * @param code 失败的返回码
     * @param errorMsg 失败的描述信息
     */
    void onError(int code, String errorMsg);

    /**
     * 显示进度条
     */
    void showLoading();

    /**
     * 隐藏进度条
     */
    void hideLoading();

    /**
     * 没有登录，则前往登录界面
     */
    void notLogin();
}
