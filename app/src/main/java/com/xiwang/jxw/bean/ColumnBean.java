package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 栏目实体
 * 用于首页的新闻栏目
 * @author liangxg
 * @description
 * @date 2015/11/5
 * @modifier
 */
public class ColumnBean extends BaseBean{
    /** 名次*/
    private String name;
    /** 数据链接*/
    private String dataUrl;
    /** 显示类型*/
    private String showType;
    /** 排序*/
    private int index;

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
