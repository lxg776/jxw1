package com.xiwang.jxw.bean;

import com.xiwang.jxw.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**用户实体
 * Created by sunshine on 15/12/27.
 */
public class UserInfoBean extends BaseBean{
    /**用户id*/
    private String uid;
    /**用户昵称*/
    private String username;
    /**用户昵称*/
    private String face;
    /**注册时间*/
    private String regdate;
    /**个性签名*/
    private String signature;
    /**自我简介*/
    private String introduce;
    /**新短消息数*/
    private String newpm;


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

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getNewpm() {
        return newpm;
    }

    public void setNewpm(String newpm) {
        this.newpm = newpm;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setUid(jSon.optString("uid"));
        setUsername(jSon.optString("username"));
        setFace(jSon.optString("face"));
        setRegdate(jSon.optString("regdate"));
        setSignature(jSon.optString("signature"));
        setIntroduce(jSon.optString("introduce"));
        setNewpm(jSon.optString("newpm"));
    }
}
