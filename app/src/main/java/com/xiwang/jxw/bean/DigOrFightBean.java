package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liangxg on 2016/1/27.
 */
public class DigOrFightBean extends  BaseBean{
    /**点赞次数*/
    String dig;

    public String getDig() {
        return dig;
    }

    public void setDig(String dig) {
        this.dig = dig;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
                dig=jSon.getString("dig");
    }
}
