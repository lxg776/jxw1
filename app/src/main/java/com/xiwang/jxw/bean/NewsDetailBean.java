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
