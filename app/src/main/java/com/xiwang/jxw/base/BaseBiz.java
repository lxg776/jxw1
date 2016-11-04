package com.xiwang.jxw.base;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;


import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.umeng.analytics.MobclickAgent;
import com.xiwang.jxw.R;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.fragment.SignatureUtil;
import com.xiwang.jxw.network.AppHttpClient;
import com.xiwang.jxw.util.DateUtil;
import com.xiwang.jxw.util.Log;
import com.xiwang.jxw.util.LogUtil;
import com.xiwang.jxw.util.ProcessDialogUtil;
import android.content.DialogInterface.OnDismissListener;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

/**
 * 基本的网络事务
 * @author liangxg
 * @description
 * @date 2015/10/29
 * @modifier
 */
public class BaseBiz {

    /**是否测试*/
    public static final boolean isDebug=true;
    public static final String SUCCESS_CODE="200";


    /**
     * 发送http get请求
     * @param url
     * @param params
     */
    protected static void getRequest(final String url, final RequestParams params, final RequestHandle handle){

                ResponseBean cacheData = handle.getRequestCache();
                if (cacheData != null) {
                    handle.onRequestCache(cacheData);
                }

            Log.d(url);
            if(null!=params){
                Log.d(params.toString());
            }
        AsyncHttpResponseHandler handler =new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseStr = new String(responseBody);
                ResponseBean responseBean = new ResponseBean();
                LogUtil.i("Net","get_URL:"+url);
                if(null!=params){
                    LogUtil.i("Net","get_params:"+params.toString());
                }
                LogUtil.i("Net","get_Json:"+ responseStr);
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

                    handle.onFail(responseBean);
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
        };

        if(params==null){
            AppHttpClient.get(url,handler);
        }else{
            AppHttpClient.get(url, params, handler);
        }
    }


    /**
     * 发送http post请求
     * @param url
     * @param params
     */
    public static void postRequest(final Context context,String processMsg,final String url, final RequestParams params, final RequestHandle mhandle){

        ResponseBean cacheData = mhandle.getRequestCache();
        if (cacheData != null) {
            mhandle.onRequestCache(cacheData);
        }

        Log.d(url);
        if(null!=params){
            Log.d(params.toString());
        }
       final AsyncHttpResponseHandler handler =new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseStr = new String(responseBody);
                ResponseBean responseBean = new ResponseBean();


                LogUtil.i("Net","get_URL:"+url);
                if(null!=params){
                    LogUtil.i("Net","get_params:"+params.toString());
                }
                LogUtil.i("Net","get_Json:"+ responseStr);
                try {

                    JSONObject jsonObject = new JSONObject(responseStr);
                    responseBean.setInfo(jsonObject.optString("msg"));
                    responseBean.setStatus(jsonObject.optString("code"));
                    responseBean.setObject(jsonObject.optString("data"));
                } catch (JSONException e) {
                    responseBean.setStatus(ServerConfig.JSON_DATA_ERROR);
                    responseBean.setInfo(TApplication.context.getResources().getString(R.string.exception_local_json_message));
                    mhandle.onFail(responseBean);
                }

                if(isSuccess(responseBean.getStatus())){
                    mhandle.onSuccess(responseBean);
                }else
                {
                    if(isDebug){
                        MobclickAgent.reportError(
                                context,
                                getErrorMsg(url, params,
                                        responseBean.getStatus(),
                                        responseBean.getInfo()));
                    }
                    mhandle.onFail(responseBean);



                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if(isDebug){
                    if(null!=responseBody){
                        MobclickAgent.reportError(
                                context,
                                getErrorMsg(url, params, statusCode + "", new String(
                                        responseBody)));
                    }else{
                        MobclickAgent.reportError(
                                context,
                                getErrorMsg(url, params, statusCode + "", "null"));
                    }
                }


                ResponseBean responseBean = new ResponseBean();
                responseBean.setStatus(statusCode + "");
                if(null!=responseBody){
                    responseBean.setInfo(new String(responseBody));
                }
                mhandle.onFail(responseBean);
            }
        };

        if (!TextUtils.isEmpty(processMsg)&&null!=context) {
            ProcessDialogUtil.showDialog(context, processMsg, true);
            /** loading 结束取消网络请求 */
            ProcessDialogUtil.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    handler.onCancel();
                }
            });
        }


        if(params==null){
            AppHttpClient.post(url, handler);
        }else{
            AppHttpClient.post(url, params, handler);
        }




    }

    public static void postRequest(Context context,final String url, final RequestParams params, final RequestHandle mhandle){

        postRequest(context,"",url,params,mhandle);
    }



    public static boolean isSuccess(String code){
        if(SUCCESS_CODE.equals(code)){
            return true;
        }else
        {
            return  false;
        }
    }


    /**
     * 拼装自定义错误
     *
     * @param url
     * @param params
     * @param statusCode
     * @param responseBody
     * @return
     */
    public static String getErrorMsg(String url,
                                     RequestParams params , String statusCode,
                                     String responseBody) {
        StringBuffer sb = new StringBuffer();


        sb.append("URL:" + url);
//        if (params.containsKey("password")) {
//            params.remove("password");
//        }
//        if (params.containsKey("account")) {
//            params.remove("account");
//        }


        sb.append("PARAMS:" + params.toString());
        sb.append("STATUS:" + statusCode);
        sb.append("ERRORBODY:" + responseBody);

        return sb.toString();

    }




    public interface RequestHandle{
        public void onSuccess(ResponseBean responseBean);
        public void onFail(ResponseBean responseBean);
        public ResponseBean getRequestCache();
        public  void onRequestCache(ResponseBean result);
    }

    public static String ver="1.0";
    public static String platform="android";
    public static String signatureKay ="hd88d145dasg";


    public  static RequestParams getParams(){
        RequestParams params =new RequestParams();
        params.put("ver",ver);
        String timestamp = System.currentTimeMillis()+"";
        String randomString = getRandomString(5);
        params.put("apptimestamp",timestamp);
        params.put("ver",ver);
        params.put("random",randomString);
        params.put("platform",platform);
        params.put("signature", SignatureUtil.getSignature(signatureKay,timestamp,randomString));
        return  params;
    }


    /**
     * 获取随机字符
     * @param length
     * @return
     */
    public static String getRandomString(int length){ //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
