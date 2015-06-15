package com.lzxxteam.qyinyourface.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 数据返回包
 */
public class NetPackData {

    /**
     * 返回操作为成功
     */
    public final static int STATUS_SUCC = 1;

    /**
     * 返回操作为失败,未知失败
     */
    public final static int STATUS_ERROR = 2;

    //-----关于注册的状态值

    /**
     * 用户名重复
     */
    public final static int STATUS_REG_ACCOUNT_ERROR = 3;

    /**
     * 该email已被注册
     */
    public final static int STATUS_REG_EMAIL_ERROR = 4;



    //------关于登陆的状态值
    /**
     * 密码错误
     */
    public final static int STATUS_LOGIN_PSW_ERROR = 5;


    /**
     * 状态默认为失败
     */
    private int status = STATUS_ERROR;
    private String otherData;

    @JsonProperty("status")
    public void setStatus(int status) {
        this.status = status;
    }


    @JsonProperty("other")
    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }
    public String getOtherData() {
        return otherData;
    }
    public int getStatus() {
        return status;
    }
}
