package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liangxg on 2016/2/22.
 */
public class DigUserBean extends BaseBean{

    /**用户id*/
    private  String uid;
    /**用户名称*/
    private  String username;
    /**头像*/
    private  String face;

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

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        uid=jSon.optString("uid");
        username=jSon.optString("username");
        face=jSon.optString("face");
    }
}
