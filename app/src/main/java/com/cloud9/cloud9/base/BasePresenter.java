package com.cloud9.cloud9.base;

import com.cloud9.cloud9.util.logger.Logger;

/**
 * author: xb.Zou
 * date: 2018/9/3 0003
 **/
public abstract class BasePresenter<V extends IView, M extends IBiz> implements IPresenter<V, M> {
    /**
     * View层
     */
    protected V mView;

    /**
     * Model层
     */
    protected M mBiz;

    @Override
    public void attachView(V view) {
        Logger.tag(this.getClass().getSimpleName());
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void attachBiz(M biz) {
        mBiz = biz;
    }

    @Override
    public void detachBiz() {
        cancel();
        mBiz = null;
    }

    @Override
    public void cancel() {
        if (mBiz != null){
            mBiz.cancel();
        }
    }
}
