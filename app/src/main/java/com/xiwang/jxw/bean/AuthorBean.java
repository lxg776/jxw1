package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sunshine on 15/12/14.
 */

public class AuthorBean extends BaseBean{
    /** 作者*/
    String author;
    /** 作者id*/
    String authorid;
    /** 作者头像*/
    String face;
    /** 性别*/
    String sex;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setAuthor(jSon.optString("author"));
        setAuthorid(jSon.optString("authorid"));
        setFace(jSon.optString("face"));
        setSex(jSon.optString("sex"));
    }

}
