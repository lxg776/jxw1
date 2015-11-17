package com.xiwang.jxw.biz;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.BaseBean;
import com.xiwang.jxw.bean.ListBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;

import org.json.JSONException;

/**
 * 首页逻辑
 * Created by sunshine on 15/11/6.
 */
public class HomeBiz {


    /**
     * 获取首页新闻列表
     * @param url 请求地址
     * @param page 页数
     * @param handle 回调
     */
        public static void getHomeNewsList(String url,int page,final BaseBiz.RequestHandle handle){

            url=url+"&page="+page;
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


}