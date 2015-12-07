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
    private String showType=TYPE_NEWSLIST;
    /** 排序*/
    private int index;
    /** 栏目id*/
    private String fid;



    public static String TYPE_NEWSLIST="newsList";
    public static String TYPE_WEB="newsList";



    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setFid(jSon.optString("fid"));
        setName(jSon.optString("name"));
        setDataUrl("getapp.php?a=thread&fid="+fid);
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
