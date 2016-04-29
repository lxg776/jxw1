package com.xiwang.jxw.bean;

import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 分享实体
 * @author liangxg
 * @description
 * @date 2016/1/4
 * @modifier
 */
public class ShareBean  implements Serializable {
    /** 平台标识*/
    private SHARE_MEDIA platform;
    /** 路径*/
    private String  showText;
    /** 应用名称*/
    private String  appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    private static final long serialVersionUID = 36112240749933232L;

    public SHARE_MEDIA getPlatform() {
        return platform;
    }

    public void setPlatform(SHARE_MEDIA platform) {
        this.platform = platform;
    }

    public String getShowText() {
        return showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }
}
