package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author liangxg
 * @description
 * @date 2015/12/24
 * @modifier
 */
public class UploadImgBean extends BaseBean {
    private String aid;
    private String img;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }



    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setAid(jSon.optString("aid"));
        setImg(jSon.optString("img"));
    }
}
