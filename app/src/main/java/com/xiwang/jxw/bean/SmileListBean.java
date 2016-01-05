package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 表情实体
 * @author liangxg
 * @description
 * @date 2016/1/4
 * @modifier
 */
public class SmileListBean extends BaseBean {
    /** id标识*/
    private String id;
    /** 路径*/
    private String  path;
    /** 名称*/
    private String  name;
    /** 表情列表*/
    private List<SmileBean> list;

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

    public List<SmileBean> getList() {
        return list;
    }

    public void setList(List<SmileBean> list) {
        this.list = list;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setId(jSon.optString("id"));
        setPath(jSon.optString("path"));
        setName(jSon.optString("name"));
        setList((List<SmileBean>)BaseBean.toModelList(jSon.optString("list"), SmileBean.class));

    }
}
