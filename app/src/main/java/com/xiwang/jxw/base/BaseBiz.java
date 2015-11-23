package com.xiwang.jxw.base;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.xiwang.jxw.R;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.executor.RequestExecutor;
import com.xiwang.jxw.network.AppHttpClient;

/**
 * 基本的网络事务
 * @author liangxg
 * @description
 * @date 2015/10/29
 * @modifier
 */
public class BaseBiz {


    public static final String SUCCESS_CODE="200";


    /**
     * 发送http get请求
     * @param url
     * @param params
     */
    public static void getRequest(final String url, final RequestParams params, final RequestHandle handle){

                ResponseBean cacheData = handle.getRequestCache();
                if (cacheData != null) {
                    handle.onRequestCache(cacheData);
                }
                AppHttpClient.get(ServerConfig.SERVER_API_URL + url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String responseStr = new String(responseBody);
                        ResponseBean responseBean = new ResponseBean();
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            responseBean.setInfo(jsonObject.optString("msg"));
                            responseBean.setStatus(jsonObject.optString("code"));
                            responseBean.setObject(jsonObject.optString("data"));
                        } catch (JSONException e) {
                            responseBean.setStatus(ServerConfig.JSON_DATA_ERROR);
                            responseBean.setInfo(TApplication.context.getResources().getString(R.string.exception_local_json_message));
                            handle.onFail(responseBean);
                        }

                        if(isSuccess(responseBean.getStatus())){
                            handle.onSuccess(responseBean);
                        }else
                        {
                            handle.onSuccess(responseBean);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        ResponseBean responseBean = new ResponseBean();
                        responseBean.setStatus(statusCode + "");
                        if(null!=responseBody){
                            responseBean.setInfo(new String(responseBody));
                        }
                        handle.onFail(responseBean);
                    }
                });


    }


    /**
     * 发送http请求
     * @param url
     * @param params
     */
    public static void postRequest(final String url, final RequestParams params, final RequestHandle handle){
        RequestExecutor.addTask(new Runnable() {
            @Override
            public void run() {
                ResponseBean cacheData = handle.getRequestCache();
                if (cacheData != null) {
                    handle.onRequestCache(cacheData);
                }
                AppHttpClient.post(ServerConfig.SERVER_API_URL + url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String responseStr = new String(responseBody);
                        ResponseBean responseBean = new ResponseBean();
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            responseBean.setInfo(jsonObject.optString("info"));
                            responseBean.setStatus(jsonObject.optString("status"));
                            responseBean.setObject(jsonObject.optString("data"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        ResponseBean responseBean = new ResponseBean();
                        responseBean.setStatus(statusCode + "");
                        responseBean.setInfo(new String(responseBody));
                        handle.onFail(responseBean);
                    }
                });
            }
        });
    }


    public static boolean isSuccess(String code){
        if(SUCCESS_CODE.equals(code)){
            return true;
        }else
        {
            return  false;
        }
    }




    public interface RequestHandle{
        public void onSuccess(ResponseBean responseBean);
        public void onFail(ResponseBean responseBean);
        public ResponseBean getRequestCache();
        public  void onRequestCache(ResponseBean result);
    }
}
