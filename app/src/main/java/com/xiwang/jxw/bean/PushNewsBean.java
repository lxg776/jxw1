package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 新闻bean
 * Created by sunshine on 15/11/8.
 */
public class PushNewsBean extends BaseBean{
    /** tid*/
    private String tid;
    /** father id*/
    private String fid;
    /** 标题*/
    private String subject;
    /** 描述*/
    private String desc;

    /** 图片地址*/
    private String image;
    /** 推送类型*/
    private String type;
    /** 推送连接*/
    private String linkUrl;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setTid(jSon.optString("tid"));
        setFid(jSon.optString("fid"));
        setSubject(jSon.optString("subject"));
        setImage(jSon.optString("image"));
        setType(jSon.optString("type"));
        setLinkUrl(jSon.optString("linkUrl"));
    }
}
