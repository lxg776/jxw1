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

    /**
     * 作者信息
     */
    private AuthorBean userinfo;

    /**
     * 联系方式
     */
    private ContactBean contact;


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

    public AuthorBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(AuthorBean userinfo) {
        this.userinfo = userinfo;
    }

    public ContactBean getContact() {
        return contact;
    }

    public void setContact(ContactBean contact) {
        this.contact = contact;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setDig(jSon.optString("dig"));
        setPoor(jSon.optString("poor"));
        setFromtype(jSon.optString("fromtype"));
        setSid(jSon.optString("sid"));
        setContent(jSon.optString("content"));

        setPostdate(jSon.optString("postdate"));
        setTid(jSon.optString("tid"));
        setReplies(jSon.optString("replies"));
        setHits(jSon.optString("hits"));
        setSubject(jSon.optString("subject"));
        setShareurl(jSon.optString("shareurl"));
        setPages(jSon.optString("pages"));
        setPage(jSon.optString("page"));
        setTotal(jSon.optString("total"));
        setUserinfo((AuthorBean) BaseBean.newInstance(AuthorBean.class, jSon.optString("userinfo")));
        setContact((ContactBean) BaseBean.newInstance(ContactBean.class,jSon.optString("contact")));


        setCommentList((ArrayList<NewsDetailCommentBean>) BaseBean.toModelList(jSon.optString("list"),NewsDetailCommentBean.class));
    }
}
