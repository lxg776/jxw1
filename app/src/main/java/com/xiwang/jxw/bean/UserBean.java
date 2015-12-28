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
    /** 性别*/
    private String sex;
    /** 性别显示*/
    private String sexShow;
    /** 个人信息*/
    private UserInfoBean userInfoBean;

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

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

    public String getSex() {
        return sex;
    }

    public String getSexShow() {
        return sexShow;
    }

    public void setSexShow() {

        if("0".equals(sex)){
            this.sexShow="保密";
        }else if("1".equals(sex)){
            this.sexShow="男";
        }else if("2".equals(sex)){
            this.sexShow="女";
        }

    }

    //保密0、男1、女2
    public void setSex(String sex) {
        this.sex = sex;
        setSexShow();
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setUid(jSon.optString("uid"));
        setUsername(jSon.optString("username"));
        setPwd(jSon.optString("pwd"));
        setFace(jSon.optString("face"));
        setIp(jSon.optString("ip"));
        setSex(jSon.optString("sex"));
    }
}
