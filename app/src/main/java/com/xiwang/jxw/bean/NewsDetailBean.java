package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * @author liangxg
 * @description
 * @date 2015/11/10
 * @modifier
 */
public class NewsDetailBean extends BaseBean {

    /**
     * 标题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;
    /**
     * 帖子id
     */
    private String tid;
    /**
     * 发布日期
     */
    private String postdate;
    /**
     * 作者
     */
    private String author;
    /**
     * 回复数
     */
    private String replies;
    /**
     * 浏览数
     */
    private String hits;
    /** 点赞数*/
    private String dig;
    /** 点差数*/
    private String poor;
    /** 电话号码查看数（意向数）*/
    private String telhits;
     /** 电话号码*/
    private String phone;
    /** 联系人*/
    private String cname;

    /**
     * 分享id
     */
    private String sid;
    /**
     * QQ号
     */
    private String qq;

    private String fromtype;
    /**
     * 作者头像
     */
    private String face;


    public String getDig() {
        return dig;
    }

    public void setDig(String dig) {
        this.dig = dig;
    }

    public String getPoor() {
        return poor;
    }

    public void setPoor(String poor) {
        this.poor = poor;
    }

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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getFromtype() {
        return fromtype;
    }

    public void setFromtype(String fromtype) {
        this.fromtype = fromtype;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    private ArrayList<NewsDetailCommentBean> commentList;
    private String total;
    private String page;
    private String pages;
    private String shareurl;

    public ArrayList<NewsDetailCommentBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<NewsDetailCommentBean> commentList) {
        this.commentList = commentList;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getShareurl() {
        return shareurl;
    }

    public void setShareurl(String shareurl) {
        this.shareurl = shareurl;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setDig(jSon.optString("dig"));
        setPoor(jSon.optString("poor"));
        setFace(jSon.optString("face"));
        setPhone(jSon.optString("phone"));
        setFromtype(jSon.optString("fromtype"));
        setSid(jSon.optString("sid"));
        setTelhits(jSon.optString("telhits"));
        setQq(jSon.optString("qq"));
        setCname(jSon.optString("cname"));
        setContent(jSon.optString("content"));
        setAuthor(jSon.optString("author"));
        setPostdate(jSon.optString("postdate"));
        setTid(jSon.optString("tid"));
        setReplies(jSon.optString("replies"));
        setHits(jSon.optString("hits"));
        setSubject(jSon.optString("subject"));
        setShareurl(jSon.optString("shareurl"));
        setPages(jSon.optString("pages"));
        setPage(jSon.optString("page"));
        setTotal(jSon.optString("total"));
        setCommentList((ArrayList<NewsDetailCommentBean>) BaseBean.toModelList(jSon.optString("list"),NewsDetailCommentBean.class));
    }
}
