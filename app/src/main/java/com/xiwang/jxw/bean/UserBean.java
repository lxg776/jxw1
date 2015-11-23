package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author liangxg
 * @description 用户
 * @date 2015/11/23
 * @modifier
 */
public class UserBean extends BaseBean{
    /** 用户id*/
    private String uid;
    /** 用户名*/
    private String username;
    /** 密码*/
    private String pwd;
    /** 头像*/
    private String face;
    /** ip地址*/
    private String ip;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setUid(jSon.optString("uid"));
        setUsername(jSon.optString("username"));
        setPwd(jSon.optString("pwd"));
        setFace(jSon.optString("face"));
        setIp(jSon.optString("ip"));
    }
}
