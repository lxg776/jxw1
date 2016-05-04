package com.xiwang.jxw.biz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.LoginActivity;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.BaseBean;
import com.xiwang.jxw.bean.DigOrFightBean;
import com.xiwang.jxw.bean.DigUserBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.bean.UserInfoBean;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.event.LoginEvent;
import com.xiwang.jxw.event.UserInfoEvent;
import com.xiwang.jxw.intf.UserInfoListener;
import com.xiwang.jxw.network.AppHttpClient;
import com.xiwang.jxw.util.Log;
import com.xiwang.jxw.util.SpUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import de.greenrobot.event.EventBus;

/**
 * @author liangxg
 * @description 发布逻辑
 * @date 2015/11/23
 * @modifier
 */
public class PublishBiz {


    /**
     * 发布求职
     * @param jobs
     * @param company
     * @param ability
     * @param duty
     * @param experience
     * @param wages
     * @param workaddress
     * @param number
     * @param cname
     * @param mobile
     * @param title
     * @param content
     * @param handle
     */
    public static void publishEmploy(String jobs,String company,String ability,String duty,String experience,String wages,String workaddress,String number,String cname,String mobile,String title,String content,final BaseBiz.RequestHandle handle){
        String url=ServerConfig.GETAPP_URL;
        RequestParams params =new RequestParams();
        params.put("action","new");
        params.put("type","jobs");
        params.put("a","posts");
        params.put("jobs",jobs);
        params.put("company",company);
        params.put("ability",ability);
        params.put("duty",duty);
        params.put("experience",experience);
        params.put("wages",wages);
        params.put("workaddress",workaddress);
        params.put("number",number);
        params.put("cname",cname);
        params.put("mobile",mobile);
        params.put("title",title);
        params.put("content",content);

        BaseBiz.getRequest(url, params, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
               // BaseBean.setResponseObjectList(responseBean, DigUserBean.class, "diglist");
                handle.onSuccess(responseBean);
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                handle.onFail(responseBean);
            }

            @Override
            public ResponseBean getRequestCache() {
                return handle.getRequestCache();
            }

            @Override
            public void onRequestCache(ResponseBean result) {
                handle.onRequestCache(result);
            }
        });
    }

}
