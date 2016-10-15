package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liangxg on 2016/10/14.
 */
public class CommentImagesBean extends BaseBean {

    /**缩略图*/
    String img_thumb;
    /**高清图*/
    String img_hd;


    public String getImg_thumb() {
        return img_thumb;
    }

    public void setImg_thumb(String img_thumb) {
        this.img_thumb = img_thumb;
    }

    public String getImg_hd() {
        return img_hd;
    }

    public void setImg_hd(String img_hd) {
        this.img_hd = img_hd;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        img_thumb = jSon.optString("img_thumb");
        img_hd = jSon.optString("zoomfile");
    }
}
