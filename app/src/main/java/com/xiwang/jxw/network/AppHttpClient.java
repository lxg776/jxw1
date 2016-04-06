package com.xiwang.jxw.network;


import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import com.xiwang.jxw.config.TApplication;
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

    public static void setHttpClient(AsyncHttpClient c,Context context) {
        client = c;
        client.addHeader("Accept-Language", Locale.getDefault().toString());
//        client.addHeader("Host", HOST);
        client.addHeader("Connection", "Keep-Alive");
        client.setResponseTimeout(10 * 1000);
        client.getHttpClient().getParams()
                .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
    }


    /**
     * 清除cookies
     */
    public static  void clearCookie(){
        PersistentCookieStore cookieStore = new PersistentCookieStore(TApplication.context);
        cookieStore.clear();
    }


//    /**
//     * 获取标准 Cookie
//     */
//    public static String getCookieText(Activity activity) {
//        PersistentCookieStore myCookieStore = new PersistentCookieStore(activity);
//        List<Cookie> cookies =  myCookieStore.getCookies();
//        CookieStore cookies2 = (CookieStore) client.getHttpContext().getAttribute(ClientContext.COOKIE_STORE);//获取AsyncHttpClient中的
//
//        Log.d("cookie", "cookies.size() = " + cookies.size());
//        if(null!=cookies2&&null!=cookies2.getCookies()){
//            Log.d("cookie2", "cookies.size() = " + cookies2.getCookies().size());
//        }
//
//         //Util.setCookies(cookies);
//        for (Cookie cookie : cookies) {
//            Log.d("cookie", cookie.getName() + " = " + cookie.getValue());
//        }
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < cookies.size(); i++) {
//            Cookie cookie = cookies.get(i);
//            String cookieName = cookie.getName();
//            String cookieValue = cookie.getValue();
//            if (!TextUtils.isEmpty(cookieName)
//                    && !TextUtils.isEmpty(cookieValue)) {
//                sb.append(cookieName + "=");
//                sb.append(cookieValue + ";");
//            }
//        }
//        Log.e("cookie", sb.toString());
//        return sb.toString();
//    }

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
