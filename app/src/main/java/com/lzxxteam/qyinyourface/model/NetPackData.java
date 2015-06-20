package com.lzxxteam.qyinyourface.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 数据返回包
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetPackData {

    /**
     * 返回操作为成功
     */
    public final static int STATUS_SUCC = 101;

    /**
     * 服务器收不到数据
     */
    public final static int STATUS_ERROR_SERVER_NOT_GET = 201;
    /**
     * 返回操作为失败,未知失败
     */
    public final static int STATUS_ERROR_UNKNOW = 202;

    //-----关于注册的状态值

    /**
     * 用户名重复
     */
    public final static int STATUS_ERROR_REG_ACCOUNT = 203;

    /**
     * 该email已被注册
     */
    public final static int STATUS_ERROR_REG_EMAIL = 204;


    //------关于登陆的状态值


    /**
     * 账户名不存在
     */
    public final static  int STATUS_ERROR_LOGIN_ACCOUNT = 205;
    /**
     * 密码错误
     */
    public final static int STATUS_ERROR_LOGIN_PSW = 206;



    public final static  int TYPE_FIGHT_LIST = 6;
    public final static  int TYPE_GYM_LIST = 3;
    public final static  int TYPE_GYM_DETAIl = 4;




    private int status;
    private String headOtherData;
    private int type;
    private Object data;

    @JsonProperty("status")
    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }


    @JsonProperty("other")
    public void setHeadOtherData(String otherData) {
        this.headOtherData = otherData;
    }
    public String getHeadOtherData() {
        return headOtherData;
    }


    @JsonProperty("type")
    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
