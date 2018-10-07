package com.cloud9.cloud9.base;

/**
 * author: xb.Zou
 * date: 2018/9/3 0003
 **/
public interface IBiz {
    /**
     * 当Model层还在处理数据时，View被取消绑定，此时需要将该处理逻辑停止
     */
    void cancel();
}
