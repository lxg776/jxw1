package com.xiwang.jxw.biz;

import com.loopj.android.http.RequestParams;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.BaseBean;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.HfPagerBean;
import com.xiwang.jxw.bean.HomeLunBoBean;
import com.xiwang.jxw.bean.ListBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * 首页逻辑
 * Created by sunshine on 15/11/6.
 */
public class HomeBiz {



    /**
     * 获取首页栏目
     * @param handle 回调
     */
    public static void getHomeMenu(final BaseBiz.RequestHandle handle){


        BaseBiz.getRequest(ServerConfig.MENUTOP_TOP, null, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                String string= (String) responseBean.getObject();
                try {
                    responseBean.setObject(BaseBean.toModelList(string,ColumnBean.class));

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
     * 获取首页新闻列表
     * @param url 请求地址
     * @param page 页数
     * @param handle 回调
     */
        public static void getHomeNewsList(String url,int page,final BaseBiz.RequestHandle handle){

            url=ServerConfig.SERVER_API_URL+url+"&page="+page;
            BaseBiz.getRequest(url, null, new BaseBiz.RequestHandle() {
                @Override
                public void onSuccess(ResponseBean responseBean) {
                    String string= (String) responseBean.getObject();
                    try {
                        ListBean listBean=new ListBean(string,NewsBean.class);
                        responseBean.setObject(listBean);
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
     * 获取头部广轮播
     * @param fid
     * @param handle
     */
    public static void getTopLunbo(String fid,final BaseBiz.RequestHandle handle){
            String url = ServerConfig.GETAPP_URL;
        RequestParams params =new RequestParams();
        params.put("a","ibenner");
        params.put("fid",fid);
        BaseBiz.getRequest(url, params, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                String string = (String) responseBean.getObject();
                HomeLunBoBean boBean =new HomeLunBoBean();
                try {
                    boBean.setRotation_speed(3000);
                    boBean.setDataList((ArrayList<HfPagerBean>) BaseBean.toModelList(string, HfPagerBean.class));
                    responseBean.setObject(boBean);
                    handle.onSuccess(responseBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                    responseBean.setInfo("数据转化异常");
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


}
