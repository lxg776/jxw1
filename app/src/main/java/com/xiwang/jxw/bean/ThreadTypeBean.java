package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 主题分类接口
 * Created by liangxg on 2016/1/22.
 */
public class ThreadTypeBean extends BaseBean{

    private String id;
    /**fid*/
    private String fid;
    /**主题名称*/
    private String name;
    /**upid*/
    private String upid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpid() {
        return upid;
    }

    public void setUpid(String upid) {
        this.upid = upid;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setFid(jSon.optString("fid"));
        setName(jSon.optString("name"));
        setUpid(jSon.optString("upid"));
        setId(jSon.optString("id"));
    }
}
