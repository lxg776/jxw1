package com.xiwang.jxw.biz;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.loopj.android.http.RequestParams;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.BaseBean;
import com.xiwang.jxw.bean.DigUserBean;
import com.xiwang.jxw.bean.NewsDetailBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.SmileListBean;
import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.bean.UserInfoBean;
import com.xiwang.jxw.bean.postbean.TopicBean;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.util.SpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻逻辑
 * @author liangxg
 * @description
 * @date 2015/11/11
 * @modifier
 */
public class NewsBiz extends BaseBiz {


    /**
     * 获取表情表情
     * @return
     */
    public static void getSmileBean(final BaseBiz.RequestHandle handle){


        BaseBiz.getRequest(ServerConfig.SMILES_URL, getParams(), new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                    String string= (String) responseBean.getObject();
                    try {
                        JSONObject jsonObject=new JSONObject(string);
                        List<SmileListBean> list= (List<SmileListBean>) BaseBean.toModelList(jsonObject.optString("smiles"), SmileListBean.class);
                        responseBean.setObject(list);
                    }catch (JSONException e){
                        responseBean.setStatus(ServerConfig.JSON_DATA_ERROR);
                        responseBean.setInfo(TApplication.context.getResources().getString(R.string.exception_local_json_message));
                        handle.onFail(responseBean);
                    }
                 handle.onSuccess(responseBean);

            }

            @Override
            public void onFail(ResponseBean responseBean) {
                handle.onFail(responseBean);
            }

            @Override
            public ResponseBean getRequestCache() {
                return null;
            }

            @Override
            public void onRequestCache(ResponseBean result) {

            }
        });
    }


    /**
     * 回复
     *
     * @param tid
     * @param content
     * @param aids //图片
     * @param handle
     */
    public static void reply(String tid, String content, String aids, final BaseBiz.RequestHandle handle) {
        RequestParams params =getParams();
        params.put("tid", tid);
        params.put("action", "reply");
        params.put("content", content);
        if (!TextUtils.isEmpty(aids)) {
            params.put("aids", aids);
        }

        BaseBiz.getRequest(ServerConfig.PUBLISH_URL, params, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {

//                String string = (String) responseBean.getObject();
//                try {
//                    NewsDetailBean bean = (NewsDetailBean) BaseBean.newInstance(NewsDetailBean.class, string);
//                    responseBean.setObject(bean);
//                    handle.onSuccess(responseBean);
//                } catch (JSONException e) {
//                    responseBean.setStatus(ServerConfig.JSON_DATA_ERROR);
//                    responseBean.setInfo(TApplication.context.getResources().getString(R.string.exception_local_json_message));
//                    handle.onFail(responseBean);
//                }
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


    /**
     * 发布主题
     * @param topicBean 主题
     */
    public  static void publishTopic(Context context,TopicBean topicBean,final BaseBiz.RequestHandle handle){
        if(topicBean==null){
            return;
        }

        RequestParams params =getParams();
       // params.put("fid","70");
        params.put("fid",topicBean.getFid());
        //params.put("tid",topicBean.getTid());
        params.put("action",topicBean.getAction());
      //  params.put("tid",topicBean.getTid());
        params.put("subject",topicBean.getSubject());
        params.put("content",topicBean.getContent());
        params.put("aids",topicBean.getAids());
        params.put("a","post");
        BaseBiz.postRequest(context,ServerConfig.TOPIC_URL, params, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {

                handle.onSuccess(responseBean);
//                String string = (String) responseBean.getObject();
//                try {
//                    NewsDetailBean bean = (NewsDetailBean) BaseBean.newInstance(NewsDetailBean.class, string);
//                    responseBean.setObject(bean);
//                    handle.onSuccess(responseBean);
//                } catch (JSONException e) {
//                    responseBean.setStatus(ServerConfig.JSON_DATA_ERROR);
//                    responseBean.setInfo(TApplication.context.getResources().getString(R.string.exception_local_json_message));
//                    handle.onFail(responseBean);
//                }
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
        RequestParams params = getParams();
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

    /**
     * 获取点赞过后的新闻
     * @param context
     */
    public static ArrayList<String>  getDiges(Context context){
        UserBean userBean=UserBiz.getUserBean(context);
        String userId=null;
        if(null!=userBean){
            userId=userBean.getUid();
        }else{
            userId="unLoginUser";
        }
        ArrayList<String> digList= (ArrayList<String>) SpUtil.getObject(context,context.getResources().getString(R.string.cache_dig)+userId);
        if(digList==null){
            digList=new ArrayList<>();
        }
        return  digList;
    }

    /**
     * 设置点赞过后的帖子
     * @param context
     * @param digList
     */
    public static  void setDiges(Context context, ArrayList<String> digList){
        UserBean userBean=UserBiz.getUserBean(context);
        String userId=null;
        if(null!=userBean){
            userId=userBean.getUid();
        }else{
            userId="unLoginUser";
        }
        String key=context.getResources().getString(R.string.cache_dig)+userId;
        SpUtil.setObject(context,key,digList);
    }

    /**
     * 获取点赞列表
     * @param tid
     */
    public static void getDigUsers(String tid,final BaseBiz.RequestHandle handle){
        String url=ServerConfig.DIG_LIST_URL;
        RequestParams params = getParams();
        params.put("tid",tid);

        BaseBiz.getRequest(url, params, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                String string= (String) responseBean.getObject();
                try {
                    JSONObject jsonObject=new JSONObject(string);
                    responseBean.setObject( BaseBean.toModelList(jsonObject.optString("list"),DigUserBean.class));
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
