package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 启动app图片
 * Created by sunshine on 15/11/6.
 */
public class StartAppBean extends BaseBean {
    /**图片地址*/
    String imgurl;
    String signkey;

    public String getSignkey() {
        return signkey;
    }

    public void setSignkey(String signkey) {
        this.signkey = signkey;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
            setImgurl(jSon.optString("imgurl"));
            setSignkey(jSon.optString("signkey"));
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }



}
