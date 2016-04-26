package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 新闻bean
 * Created by sunshine on 15/11/8.
 */
public class NewsBean extends BaseBean{
    /** tid*/
    private String tid;
    /** father id*/
    private String fid;
    /** authorid*/
    private String authorid;
    /** 作者*/
    private String author;
    /** 标题*/
    private String subject;
    /** 点击数*/
    private String hits;
    /** 回复数*/
    private String replies;
    /** 图片地址*/
    private String image;
    /** 描述*/
    private String desc;
    /** 分享连接*/
    private String shareUrl;


    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

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

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        if("null".equals(image)){
            return "";
        }

        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setTid(jSon.optString("tid"));
        setFid(jSon.optString("fid"));
        setAuthorid(jSon.optString("authorid"));
        setAuthor(jSon.optString("author"));
        setSubject(jSon.optString("subject"));
        setHits(jSon.optString("hits"));
        setReplies(jSon.optString("replies"));
        setImage(jSon.optString("image"));
        setDesc(jSon.optString("desc"));
    }
}
