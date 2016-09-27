package com.xiwang.jxw.bean;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 详情评论实体
 * @author liangxg
 * @description
 * @date 2015/11/10
 * @modifier
 */
public class NewsDetailCommentBean extends BaseBean {

    private String aid;
    private String pid;
    private String subject;
    private String authorid;
    private String content;
    /** 发帖日期*/
    private String postdate;

    private String ifshield;
    private String groupid;
    /** 某楼，如三楼、四楼*/
    private String id;
    /** 评论者*/
    private String author;
    /** 头像*/
    private String face;
    /** 是否匿名*/
    private String anonymous;

    private AuthorBean userinfo;


    private List<String> images;


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public AuthorBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(AuthorBean userinfo) {
        this.userinfo = userinfo;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
            setAid(jSon.optString("aid"));
            setPid(jSon.optString("pid"));
            setSubject(jSon.optString("subject"));

            setContent(jSon.optString("content"));
            setPostdate(jSon.optString("postdate"));
            setAnonymous(jSon.optString("anonymous"));
            setIfshield(jSon.optString("ifshield"));
            setGroupid(jSon.optString("groupid"));
            setId(jSon.optString("id"));

            String imagesString = jSon.optString("images");
            if(!TextUtils.isEmpty(imagesString)){
               JSONArray imagesArray = jSon.optJSONArray("images");
                images = new ArrayList<>();
               for(int i=0;i<imagesArray.length();i++){
                   String string = imagesArray.optString(i);
                   images.add(string);
               }
            }

            setUserinfo((AuthorBean) BaseBean.newInstance(AuthorBean.class, jSon.optString("userinfo")));
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getIfshield() {
        return ifshield;
    }

    public void setIfshield(String ifshield) {
        this.ifshield = ifshield;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
