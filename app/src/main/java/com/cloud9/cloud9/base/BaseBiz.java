package com.cloud9.cloud9.base;

import com.cloud9.cloud9.util.logger.Logger;

/**
 * author: xb.Zou
 * date: 2018/9/3 0003
 **/
public abstract class BaseBiz implements IBiz {
    public BaseBiz(){
        Logger.tag(this.getClass().getSimpleName());
    }

    @Override
    public void cancel() {

    }
}
