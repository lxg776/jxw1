package com.xiwang.jxw.biz;

import com.loopj.android.http.RequestParams;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.BaseBean;
import com.xiwang.jxw.bean.ListBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.NewsDetailBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.postbean.TopicBean;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;

import org.json.JSONException;

/**
 * 新闻逻辑
 * @author liangxg
 * @description
 * @date 2015/11/11
 * @modifier
 */
public class NewsBiz {


    /**
     * 发布主题
     * @param topicBean 主题
     */
    public  static void publishTopic(TopicBean topicBean,final BaseBiz.RequestHandle handle){
        if(topicBean==null){
            return;
        }

        RequestParams params =new RequestParams();
        params.put("fid",topicBean.getFid());
        params.put("tid",topicBean.getTid());
        params.put("action",topicBean.getAction());
        params.put("tid",topicBean.getTid());
        params.put("subject",topicBean.getSubject());
        params.put("content",topicBean.getContent());
        params.put("aids",topicBean.getAids());
        BaseBiz.getRequest(ServerConfig.TOPIC_URL, params, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                String string = (String) responseBean.getObject();
                try {
                    NewsDetailBean bean = (NewsDetailBean) BaseBean.newInstance(NewsDetailBean.class, string);
                    responseBean.setObject(bean);
                    handle.onSuccess(responseBean);
                } catch (JSONException e) {
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
     * @param tid 帖子的id
     * @param handle 回调
     */
    public static void getNewsDetail(String tid,int page,final BaseBiz.RequestHandle handle){
        String url=ServerConfig.NEWS_DETAIL;
        RequestParams params =new RequestParams();
        params.put("page",page);
        params.put("tid",tid);
        BaseBiz.getRequest(url, params, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                String string= (String) responseBean.getObject();
                try {
                    NewsDetailBean bean= (NewsDetailBean) BaseBean.newInstance(NewsDetailBean.class, string);
                    responseBean.setObject(bean);
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
