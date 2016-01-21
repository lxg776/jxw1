package com.xiwang.jxw.biz;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.loopj.android.http.RequestParams;
import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.LoginActivity;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.BaseBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.bean.UserInfoBean;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.event.UserInfoEvent;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.util.ToastUtil;
import org.json.JSONException;
import de.greenrobot.event.EventBus;

/**
 * @author liangxg
 * @description
 * @date 2015/11/23
 * @modifier
 */
public class UserBiz {

    /**
     * 判断是否登录
     */
    public static boolean isLogin(Activity context,Bundle bundle){
        if(null==getUserBean(context)){
            Intent intent=new Intent(context, LoginActivity.class);
            context.startActivityForResult(intent, IntentConfig.LOGIN_CODE, bundle);
            return false;
        }
        return  true;
    }


    /**
     * 自动登录
     */
    public static void autoLogin(final Context context){
           final  UserBean userBean=getUserBean(context);
            if(null!=userBean&&TextUtils.isEmpty(userBean.getPwd())){
                /**
                 *用户名和密码不为空进行自动登录
                 */
                login(userBean.getUsername(), userBean.getPwd(), new BaseBiz.RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {

                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        setNullToUser(context);
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
    }


   public static void setNullToUser(Context context){
       UserBean userBean=(UserBean)SpUtil.getObject(context,context.getResources().getString(R.string.cache_user));
       if(null!=userBean){
           SpUtil.setObject(context, context.getResources().getString(R.string.cache_userName), userBean.getUsername());
       }
        setUserBean(context,null);
   }

    /**
     * 设置用户bean
     * @param context
     * @param userBean
     */
    public static void setUserBean(Context context,UserBean userBean){
        if(userBean!=null){
            UserBean locUserBean=(UserBean)SpUtil.getObject(context,context.getResources().getString(R.string.cache_user));
            UserInfoBean locUserInfoBean=null;
            if(null!=locUserBean){
                locUserInfoBean =locUserBean.getUserInfoBean();
            }
            if(null==userBean.getUserInfoBean()){
                if(null!=locUserInfoBean){
                    userBean.setUserInfoBean(locUserInfoBean);
                }
            }
            TApplication.mUser=userBean;
            SpUtil.setObject(context, context.getResources().getString(R.string.cache_user), userBean);
        }else{
            SpUtil.setObject(context, context.getResources().getString(R.string.cache_user), null);
            TApplication.mUser=null;
        }
    }

    /**
     * 获取全局用户引用
     * @param context
     * @return
     */
    public static UserBean getUserBean(Context context){
        if(TApplication.mUser!=null){
                return TApplication.mUser;
        }
        UserBean locUserBean=(UserBean)SpUtil.getObject(context,context.getResources().getString(R.string.cache_user));
        if(locUserBean!=null){
            TApplication.mUser=locUserBean;
        }else{
            ToastUtil.showToast(context, "无用户数据!");
        }
        return TApplication.mUser;
    }


    /**
     * 获取我的信息
     */
    public static  void getMyInfo(final Context context,final UserBean userBean){
                if(null!=userBean&&!TextUtils.isEmpty(userBean.getUid())){
                             getUserInfo(userBean.getUid(), new BaseBiz.RequestHandle() {
                                 @Override
                                 public void onSuccess(ResponseBean responseBean) {
                                     userBean.setUserInfoBean((UserInfoBean) responseBean.getObject());
                                     setUserBean(context, userBean);
                                     /** 发送更新用户信息事件*/
                                     EventBus.getDefault().post(new UserInfoEvent());
                                 }

                                 @Override
                                 public void onFail(ResponseBean responseBean) {

                                 }

                                 @Override
                                 public ResponseBean getRequestCache() {
                                     return null;
                                 }

                                 @Override
                                 public void onRequestCache(ResponseBean result) {

                                 }
                             });
                }else{
                    ToastUtil.showToast(context, "当前用户不存在!");
                }
    }

    /**
     * 用户发帖
     * @param fid 频道
     * @param type 类型
     * @param action 操作
     * @param tid 跟帖
     * @param subject 主题
     * @param content 内容
     * @param aids 上传的图片
     * @param handle 操作
     */
    public static void faTie(String fid,String type,String action,String tid,String subject,String content,String aids,final BaseBiz.RequestHandle handle){
        RequestParams params =new RequestParams();
        if(!TextUtils.isEmpty(fid)){
            params.put("fid",fid);
        }
        if(!TextUtils.isEmpty(type)){
            params.put("type",type);
        }
        if(!TextUtils.isEmpty(tid)){
            params.put("tid",tid);
        }
        if(!TextUtils.isEmpty(subject)){
            params.put("subject",subject);
        }
        if(!TextUtils.isEmpty(content)){
            params.put("content",subject);
        }
        if(!TextUtils.isEmpty(aids)){
            params.put("aids",aids);
        }

        BaseBiz.getRequest(ServerConfig.PUBLISH_URL, params, new BaseBiz.RequestHandle() {

            @Override
            public void onSuccess(ResponseBean responseBean) {

                String string = (String) responseBean.getObject();
                try {
                    responseBean.setObject(BaseBean.newInstance(UserInfoBean.class, string));
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
     * 获取个人信息
     * @param uid
     * @param handle
     */
    public static void getUserInfo(String uid,final BaseBiz.RequestHandle handle){
        RequestParams params =new RequestParams();
        if(!TextUtils.isEmpty(uid)){
            params.put("uid",uid);
        }
        BaseBiz.getRequest(ServerConfig.MYINFO_URL, params, new BaseBiz.RequestHandle() {

            @Override
            public void onSuccess(ResponseBean responseBean) {

                String string = (String) responseBean.getObject();
                try {
                    responseBean.setObject(BaseBean.newInstance(UserInfoBean.class, string));
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
     * 用户登录方法
     * @param userName
     * @param pwd
     * @param handle
     */
    public static void login(String userName,String pwd,final BaseBiz.RequestHandle handle){
        RequestParams params =new RequestParams();
        params.put("username",userName);
        params.put("password",pwd);
       //params.put("a","login");
        BaseBiz.getRequest(ServerConfig.USER_LOGIN, params, new BaseBiz.RequestHandle() {

            @Override
            public void onSuccess(ResponseBean responseBean) {

                String string = (String) responseBean.getObject();
                try {
                    responseBean.setObject(BaseBean.newInstance(UserBean.class, string));
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
     * 用户登录方法
     * @param userName
     * @param pwd
     * @param handle
     */
    public static void reg(String userName,String pwd,String email,String sex,final BaseBiz.RequestHandle handle){
        RequestParams params =new RequestParams();
        params.put("username",userName);
        params.put("password",pwd);
        params.put("email",email);
        params.put("sex",sex);

        BaseBiz.getRequest(ServerConfig.USER_REG, params, new BaseBiz.RequestHandle() {

            @Override
            public void onSuccess(ResponseBean responseBean) {

                String string = (String) responseBean.getObject();
                try {
                    responseBean.setObject(BaseBean.newInstance(UserBean.class, string));
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




}
