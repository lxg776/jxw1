package com.xiwang.jxw.biz;

import com.loopj.android.http.RequestParams;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.config.ServerConfig;

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
