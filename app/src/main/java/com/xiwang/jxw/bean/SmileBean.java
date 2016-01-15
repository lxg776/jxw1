package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 表情实体
 * @author liangxg
 * @description
 * @date 2016/1/4
 * @modifier
 */
public class SmileBean extends BaseBean {
    /** id标识*/
    private String id;
    /** 路径*/
    private String  path;
    /** 名称*/
    private String  name;
    /** 图片路径*/
    private String  img;


    private boolean isDeleteSimile=false;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isDeleteSimile() {
        return isDeleteSimile;
    }

    public void setIsDeleteSimile(boolean isDeleteSimile) {
        this.isDeleteSimile = isDeleteSimile;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setImg(jSon.optString("img"));
        setId(jSon.optString("id"));
        setPath(jSon.optString("path"));
        setName(jSon.optString("name"));
    }
}
