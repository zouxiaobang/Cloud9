package com.cloud9.cloud9.base;

/**
 * author: xb.Zou
 * date: 2018/9/3 0003
 **/
public interface IPresenter<V, M> {
    /**
     * 绑定View层 -- 由View层调用
     * @param view 被绑定的View
     */
    void attachView(V view);

    /**
     * 取消绑定View层
     */
    void detachView();

    /**
     * 绑定Model层 -- 由Presenter层调用
     * @param biz 被绑定的数据处理器
     */
    void attachBiz(M biz);

    /**
     * 取消绑定Model层
     */
    void detachBiz();

    /**
     * 手动取消逻辑
     */
    void cancel();
}
