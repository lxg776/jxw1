package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 版本信息
 * Created by liangxg on 2016/11/11.
 */
public class VersionInfoBean extends BaseBean{

    private String id;
    private int versioncode;
     /**下载地址*/
    private String urls;
    private String uptime;
    /**更新日志*/
    private String content;
    /**最低强制更新版本*/
    private int minForceUpdateVersion;
    private String newFla;

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMinForceUpdateVersion() {
        return minForceUpdateVersion;
    }

    public void setMinForceUpdateVersion(int minForceUpdateVersion) {
        this.minForceUpdateVersion = minForceUpdateVersion;
    }

    public String getNewFla() {
        return newFla;
    }

    public void setNewFla(String newFla) {
        this.newFla = newFla;
    }


    //    "id": "2",
//            "versioncode": "300",
//            "urls": "http://m.jingxi.net/m/jxwv1.0.apk",
//            "uptime": "2015-03-16",
//            "content": "修复无法判断网络状态问题",
//            "minForceUpdateVersion": "100",
//            "new": "1"


    @Override
    protected void init(JSONObject jSon) throws JSONException {
        id = jSon.optString("id");
        versioncode= jSon.optInt("versioncode");
        urls = jSon.optString("urls");
        uptime = jSon.optString("uptime");
        content = jSon.optString("content");
        minForceUpdateVersion = jSon.optInt("minForceUpdateVersion");
    }
}
