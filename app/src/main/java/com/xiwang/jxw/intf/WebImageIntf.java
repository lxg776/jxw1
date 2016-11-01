package com.xiwang.jxw.intf;

import android.webkit.JavascriptInterface;

/**
 * Created by liangxg on 2016/11/1.
 */
public interface WebImageIntf {

    @JavascriptInterface
    public void toImages(String url);
}
