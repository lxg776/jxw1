package com.xiwang.jxw.biz;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.BaseBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.StartAppBean;
import com.xiwang.jxw.bean.VersionInfoBean;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.network.AppHttpClient;
import com.xiwang.jxw.util.HandlerUtil;
import com.xiwang.jxw.util.Log;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.VersionInfo;

/**
 * 系统逻辑
 * Created by sunshine on 15/11/6.
 */
public class SystemBiz extends BaseBiz {
        /**
         * 上传图片
         * @param path
         * @param handle
         * @throws FileNotFoundException
         */
        public static void uploadImg(final String path,final UploadImgListener handle){

            HandlerUtil.getInstence().backgroundHandler.post(new Runnable() {
                @Override
                public void run() {
                    RequestParams params= getParams();
                    params.put("type","media");
                    File file =new File(path);
                    if(!file.exists()){
                        return;
                    }
                    try {
                        params.put("file",file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    AppHttpClient.post(ServerConfig.UPLOAD_IMG, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onProgress(final long bytesWritten, final long totalSize) {
                           // super.onProgress(bytesWritten, totalSize);
                            HandlerUtil.getInstence().mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int progress= (int) (bytesWritten*100/totalSize);
                                    if(progress==100){
                                        handle.onFinish();
                                    }
                                    handle.onProgress(progress);
                                }
                            });
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                           final String responseStr = new String(responseBody);

                            final ResponseBean responseBean = new ResponseBean();

                            HandlerUtil.getInstence().mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("data:" + responseStr);
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
                                    if(BaseBiz.isSuccess(responseBean.getStatus())){
                                        handle.onSuccess(responseBean);
                                    }else
                                    {
                                        handle.onFail(responseBean);
                                    }
                                }
                            });


                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            final ResponseBean responseBean = new ResponseBean();
                            responseBean.setStatus(statusCode + "");
                            if(null!=responseBody){
                                responseBean.setInfo(new String(responseBody));
                            }
                            HandlerUtil.getInstence().mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    handle.onFail(responseBean);
                                }
                            });


                        }
                    });
                }
            });

        }


        public static void getStartAppImage(final BaseBiz.RequestHandle handle){

            BaseBiz.getRequest(ServerConfig.START_APP_IMG, new RequestParams(), new BaseBiz.RequestHandle() {

                @Override
                public void onSuccess(ResponseBean responseBean) {

                    String string= (String) responseBean.getObject();
                    try {
                        responseBean.setObject(BaseBean.newInstance(StartAppBean.class,string));
                        handle.onSuccess(responseBean);
                    }catch (JSONException e){
                        responseBean.setStatus(ServerConfig.JSON_DATA_ERROR);
                        responseBean.setInfo(TApplication.context.getResources().getString(R.string.exception_local_json_message));
                        handle.onFail(responseBean);
                    }

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


    /**
     * 监测版本更新
     * @param handle
     */
    public static void checkUpdate(final BaseBiz.RequestHandle handle){

        RequestParams requestParams =getParams();
        requestParams.put("a","version");
        BaseBiz.getRequest(ServerConfig.GETAPP_URL, requestParams, new BaseBiz.RequestHandle() {

            @Override
            public void onSuccess(ResponseBean responseBean) {
                String string= (String) responseBean.getObject();
                try {
                   List<VersionInfoBean> list = (List<VersionInfoBean>) BaseBean.toModelList(string, VersionInfoBean.class);
                    VersionInfoBean versionInfoBean=null;
                    if(list!=null&&list.size()>0){
                        versionInfoBean = list.get(0);
                    }
                    responseBean.setObject(versionInfoBean);
                    handle.onSuccess(responseBean);
                } catch (JSONException e) {
                    handle.onFail(responseBean);
                    e.printStackTrace();
                }
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

    /**
     * 上传图片监听
     */
    public static interface  UploadImgListener{
        public void onSuccess(ResponseBean responseBean);
        public void onFail(ResponseBean responseBean);
        public void onProgress(int progress);
        public void onFinish();
    }
}
