package com.cloud9.cloud9.bean.event;

import com.cloud9.cloud9.dao.entity.UserInfo;

/**
 * author: xb.Zou
 * date: 2018/9/9 0009
 **/
public class SampleUserInfo {
    public static final int INSERT = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int QUERY = 3;

    private int type;
    private UserInfo userInfo;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
