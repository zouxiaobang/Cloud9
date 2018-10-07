package com.cloud9.cloud9.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloud9.cloud9.R;
import com.cloud9.cloud9.mvp.login.LoginActivity;
import com.cloud9.cloud9.util.dialog.ReyDialogUtil;
import com.rey.material.app.Dialog;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * author: xb.Zou
 * date: 2018/9/3 0003
 **/
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IView {
    /**
     * Presenter层
     */
    protected P mPresenter;

    /**
     * Fragment绑定的视图
     */
    protected View mMainView;

    /**
     * Fragment是否已显示
     */
    private boolean isViewShown = false;

    /**
     * Fragment是否已创建完成
     */
    private boolean isBuild = false;

    /**
     * 对话框
     */
    protected Dialog mDialog;

    /**
     * 传递过来的数据
     */
    protected Bundle mBundle;

    /**
     * 绑定Layout
     * @return Layout的ID
     */
    protected abstract Object setLayout();

    /**
     * 初始化操作
     */
    protected abstract void initialize();

    /**
     * 初始化Presenter
     */
    protected abstract void initPresenter();

    /**
     * 当前视图可见
     */
    protected abstract void onVisibled();

    /**
     * 当前视图不可见
     */
    protected abstract void onInvisibled();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取传递过来的数据
        mBundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置视图
        if (setLayout() != null){
            if (setLayout() instanceof Integer){
                mMainView = inflater.inflate((Integer) setLayout(), null);
            } else if (setLayout() instanceof View){
                mMainView = (View) setLayout();
            }
        }

        //黄油刀绑定
        ButterKnife.bind(this, mMainView);

        //初始化操作
        initialize();
        initPresenter();

        //创建完毕
        isBuild = true;

        //检查准备动作
        checkPrepare();

        return mMainView;
    }

    /**
     * 检查是否准备完毕，如果准备好了，就回调显示的方法。
     */
    private void checkPrepare() {
        if (!isBuild){
            return;
        }

        if (isViewShown){
            onVisibled();
        } else {
            onInvisibled();
        }
    }

    @Override
    public void onDestroyView() {
        //取消EvnentBus的注册
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }

        //取消Presenter对View、Model层的绑定
        if (mPresenter != null){
            mPresenter.detachView();
            mPresenter.detachBiz();
            mPresenter.cancel();
        }

        isBuild = false;

        super.onDestroyView();
    }

    /**
     * 在onCreateView之前回调
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isViewShown = getUserVisibleHint();
        checkPrepare();
    }

    // ----------------------------------- 公共方法 -----------------------------------
    public void toBack(){
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.anim_push_in, R.anim.anim_push_out);
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
        Intent intent = new Intent(getActivity(), cls);

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

        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    // ------------------------------------ 回调方法 -------------------------------------

    @Override
    public void onError(int code, String errorMsg) {

    }

    @Override
    public void onSuccess(int flag, String msg) {

    }


    @Override
    public void showLoading() {
        mDialog = ReyDialogUtil.createLoadingDialog(getActivity());
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
        ((BaseActivity)getActivity()).notLogin();
    }
}
