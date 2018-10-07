package com.cloud9.cloud9.bean.event;

/**
 * author: xb.Zou
 * date: 2018/9/8 0008
 **/
public class SampleResponseEvent {
    private int code;
    private int flag;
    private String content;
    private String errMsg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
