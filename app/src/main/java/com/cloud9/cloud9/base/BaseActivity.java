package com.cloud9.cloud9.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.cloud9.cloud9.R;
import com.cloud9.cloud9.mvp.login.LoginActivity;
import com.cloud9.cloud9.util.dialog.ReyDialogUtil;
import com.cloud9.cloud9.util.logger.Logger;
import com.cloud9.cloud9.util.permission.PermissionManager;
import com.rey.material.app.Dialog;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * author: xb.Zou
 * date: 2018/9/3 0003
 **/
public abstract class BaseActivity<P extends BasePresenter>
        extends AppCompatActivity implements IView {

    /**
     * Presenter层
     */
    protected P mPresenter;

    /**
     * 对话框
     */
    protected Dialog mDialog;

    /**
     * 设置视图ID
     * @return 视图Id
     */
    protected abstract Integer setLayout();

    /**
     * 进行一些初始化操作
     */
    protected abstract void initialize();

    /**
     * 初始化Presenter层
     */
    protected abstract void initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置视图
        Integer contentViewId = setLayout();
        if (contentViewId != null){
            setContentView(contentViewId);
        }

        //绑定黄油刀
        ButterKnife.bind(this);
        Logger.tag(this.getClass().getSimpleName());

        //初始化操作
        initialize();
        initPresenter();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //取消注册EventBus -- 注册EventBus的动作由各个子类自己注册
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onDestroy() {
        //取消对View、Model层的绑定
        if (mPresenter != null){
            mPresenter.detachView();
            mPresenter.detachBiz();
        }

        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionManager.onRequestPermissionResult(this,
                requestCode,
                permissions,
                grantResults);
    }

    // --------------------------------------- 公共方法 --------------------------------------

    /**
     * 结束当前Activity
     */
    public void toBack(){
        finish();
        overridePendingTransition(R.anim.anim_push_in, R.anim.anim_push_out);
    }

    /**
     * 前往指定Activity
     * @param cls 指定的Activity
     */
    public void toNext(Class<?> cls){
        toNext(cls, false);
    }

    /**
     * 前往指定Activity
     * @param cls 指定的Activity
     * @param isFinish 是否结束当前Activity
     */
    private void toNext(Class<?> cls, boolean isFinish) {
        toNext(cls, false, null);
    }

    /**
     * 前往指定Activity
     * @param cls 指定的Activity
     * @param isFinish 是否结束当前Activity
     * @param requestCode 返回码
     */
    private void toNext(Class<?> cls, boolean isFinish, Integer requestCode) {
        toNext(cls, isFinish, requestCode, null);
    }

    /**
     * 前往指定Activity
     * @param cls 指定的Activity
     * @param isFinish 是否结束当前Activity
     * @param requestCode 返回码
     * @param data 传递的数据
     */
    private void toNext(Class<?> cls, boolean isFinish, Integer requestCode, Object data) {
        toNext(cls, isFinish, requestCode, data, null);
    }

    /**
     * 前往指定Activity
     * @param cls 指定的Activity
     * @param isFinish 是否结束当前Activity
     * @param requestCode 返回码
     * @param data 传递的数据
     * @param key 传递数据的key
     */
    private void toNext(Class<?> cls, boolean isFinish, Integer requestCode, Object data, String key) {
        Intent intent = new Intent(this, cls);

        if (data != null){
            if (key == null || TextUtils.isEmpty(key.trim())){
                if (data instanceof Serializable){
                    intent.putExtra(key, (Serializable)data);
                } else if (data instanceof Parcelable){
                    intent.putExtra(key, (Parcelable) data);
                } else if (data instanceof Bundle){
                    intent.putExtras((Bundle) data);
                }
            }
        }

        if (requestCode != null){
            //需要返回值
            startActivityForResult(intent, requestCode);
        } else {
            //不需要返回值
            startActivity(intent);

            //关闭当前Activity
            if (isFinish){
                toBack();
            }
        }

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    // -------------------------------------- 回调方法 ------------------------------------------

    @Override
    public void onError(int code, String errorMsg) {

    }

    @Override
    public void onSuccess(int flag, String msg) {

    }

    @Override
    public void showLoading() {
        mDialog = ReyDialogUtil.createLoadingDialog(this);
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mDialog != null){
            ReyDialogUtil.dismiss(mDialog);
        }
    }

    @Override
    public void notLogin() {
        toNext(LoginActivity.class, true);
    }
}
