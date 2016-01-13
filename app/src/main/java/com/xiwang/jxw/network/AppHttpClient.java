package com.xiwang.jxw.network;

import android.content.Context;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiwang.jxw.util.Log;

import org.apache.http.client.params.ClientPNames;

import java.util.Locale;



/**
 * @author qiufg1
 * @description
 * @date 2015/6/23 15:56
 * @modifier
 */
public class AppHttpClient {

    private static final String HOST = "www.weather.com.cn";
    //    private static final String HOST = "http://wthrcdn.etouch.cn/";
    public static AsyncHttpClient client;

    public AppHttpClient() {
    }

    public static AsyncHttpClient getHttpClient() {
        return client;
    }

    public static void setHttpClient(AsyncHttpClient c) {
        client = c;
        client.addHeader("Accept-Language", Locale.getDefault().toString());
//        client.addHeader("Host", HOST);
        client.addHeader("Connection", "Keep-Alive");
        client.setResponseTimeout(10 * 1000);
        client.getHttpClient().getParams()
                .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);


    }

    public static void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        client.get(url, responseHandler);



        Log.i(new StringBuilder("GET ").append(url).toString());
    }

    public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
        Log.i(new StringBuilder("GET ").append(url).append(params).toString());
    }

    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
        client.post(url, responseHandler);
        Log.i(new StringBuilder("POST ").append(url).toString());
    }

    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
        Log.i(new StringBuilder("POST ").append(url).append(params).toString());
    }

    public static void put(String url, AsyncHttpResponseHandler responseHandler) {
        client.put(url, responseHandler);
        Log.i(new StringBuilder("PUT ").append(url).toString());
    }

    public static void put(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.put(url, params, responseHandler);
        Log.i(new StringBuilder("PUT ").append(url).append(params).toString());
    }



}
