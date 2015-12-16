package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sunshine on 15/12/14.
 */
public class ContactBean  extends BaseBean{

    /** 座机*/
    String telhits;
    /** 手机*/
    String phone;
    /** 联系名*/
    String cname;
    /** qq*/
    String qq;

    public String getTelhits() {
        return telhits;
    }

    public void setTelhits(String telhits) {
        this.telhits = telhits;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setCname(jSon.optString("cname"));
        setQq(jSon.optString("qq"));
        setTelhits(jSon.optString("telhits"));
        setQq(jSon.optString("qq"));

    }
}
