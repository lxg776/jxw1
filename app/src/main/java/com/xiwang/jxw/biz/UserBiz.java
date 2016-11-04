package com.xiwang.jxw.biz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.LoginActivity;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.BaseBean;
import com.xiwang.jxw.bean.DigOrFightBean;
import com.xiwang.jxw.bean.ListBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.bean.UserInfoBean;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.event.LoginEvent;
import com.xiwang.jxw.event.UserInfoEvent;
import com.xiwang.jxw.intf.UserInfoListener;
import com.xiwang.jxw.network.AppHttpClient;
import com.xiwang.jxw.util.Log;
import com.xiwang.jxw.util.SpUtil;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.entity.mime.Header;
import de.greenrobot.event.EventBus;

/**
 * @author liangxg
 * @description
 * @date 2015/11/23
 * @modifier
 */
public class UserBiz  extends BaseBiz{

    /**
     * 判断是否登录
     */
    public static boolean isLogin(Activity context, Bundle bundle) {
        if (null == getUserBean(context)) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtras(bundle);
            context.startActivityForResult(intent, IntentConfig.LOGIN_CODE);
            return false;
        }
        return true;
    }


    /**
     * 自动登录
     */
    public static void autoLogin(final Context context) {
        final UserBean userBean = getUserBean(context);
        if (null != userBean && !TextUtils.isEmpty(userBean.getPwd())) {
            /**
             *用户名和密码不为空进行自动登录
             */
            login(userBean.getUsername(), userBean.getPwd(), new BaseBiz.RequestHandle() {
                @Override
                public void onSuccess(ResponseBean responseBean) {
                    EventBus.getDefault().post(new LoginEvent(true));
                }

                @Override
                public void onFail(ResponseBean responseBean) {
                    setNullToUser(context);
                    AppHttpClient.clearCookie();
                }

                @Override
                public ResponseBean getRequestCache() {
                    return null;
                }

                @Override
                public void onRequestCache(ResponseBean result) {

                }
            });

        }else if(null!=userBean&&!TextUtils.isEmpty(userBean.getOpenid())){
            //第三方登录
            otherLogin(context,userBean.getOpenid(),userBean.getPlatform(),userBean.getUsername(),userBean.getSex(),userBean.getFace(),new BaseBiz.RequestHandle(){

                @Override
                public void onSuccess(ResponseBean responseBean) {
                    EventBus.getDefault().post(new LoginEvent(true));
                }

                @Override
                public void onFail(ResponseBean responseBean) {
                    setNullToUser(context);
                    AppHttpClient.clearCookie();
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

    /**
     * 第三方登录
     * @param context
     * @param openid
     * @param platform
     * @param username
     * @param sex
     * @param mHandle
     */
    public static void otherLogin(final Context context ,String openid,String platform,String username,String sex,String face,final BaseBiz.RequestHandle mHandle){
        RequestParams params =  getParams();
        params.put("a", "login");
        params.put("step", "qqlogin");
        params.put("openid", openid);
        params.put("platform", platform);
        params.put("username", username);
        params.put("sex", sex);
        params.put("face", face);
        BaseBiz.postRequest(context,ServerConfig.GETAPP_URL, params, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {


                String string = (String) responseBean.getObject();
                try {
                    responseBean.setObject(BaseBean.newInstance(UserBean.class, string));
                    mHandle.onSuccess(responseBean);
                } catch (JSONException e) {
                    responseBean.setStatus(ServerConfig.JSON_DATA_ERROR);
                    responseBean.setInfo(TApplication.context.getResources().getString(R.string.exception_local_json_message));
                    mHandle.onFail(responseBean);
                }


            }

            @Override
            public void onFail(ResponseBean responseBean) {
                mHandle.onFail(responseBean);
            }

            @Override
            public ResponseBean getRequestCache() {
                return mHandle.getRequestCache();
            }

            @Override
            public void onRequestCache(ResponseBean result) {
                mHandle.onRequestCache(result);

            }
        });

    }

    /**
     * 测试cookies
     */
    public static void testCookies() {
        String url = "http://m.jingxi.net/cookie.php";
        BaseBiz.getRequest(url, getParams(), new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {

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


    }


    public static String TYPE_DIG = "dig";
    public static String TYPE_FIGHT = "fight";


    /**
     * 点赞或者反对
     *
     * @param type {点赞:dig  反对:fight}
     * @param tid  {当前帖子ID}
     * @param pid  （评论ID，0为楼主）
     */
    public static void digOrFight(String type, String tid, String pid, final BaseBiz.RequestHandle handle) {
        RequestParams params = getParams();
        params.put("a", "reply");
        params.put("type", type);
        params.put("tid", tid);
        params.put("pid", pid);
        BaseBiz.getRequest(ServerConfig.DIG_FIGHT_URL, params, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {

                String string = (String) responseBean.getObject();
                try {
                    responseBean.setObject(BaseBean.newInstance(DigOrFightBean.class, string));
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


    public static void setNullToUser(Context context) {
        UserBean userBean = (UserBean) SpUtil.getObject(context, context.getResources().getString(R.string.cache_user));
        if (null != userBean) {
            SpUtil.setObject(context, context.getResources().getString(R.string.cache_userName), userBean.getUsername());
        }
        setUserBean(context, null);
    }

    /**
     * 设置用户bean
     *
     * @param context
     * @param userBean
     */
    public static void setUserBean(Context context, UserBean userBean) {
        if (userBean != null) {
            UserBean locUserBean = (UserBean) SpUtil.getObject(context, context.getResources().getString(R.string.cache_user));
            UserInfoBean locUserInfoBean = null;
            if (null != locUserBean) {
                locUserInfoBean = locUserBean.getUserInfoBean();
            }
            if (null == userBean.getUserInfoBean()) {
                if (null != locUserInfoBean) {
                    userBean.setUserInfoBean(locUserInfoBean);
                }
            }
            TApplication.mUser = userBean;
            SpUtil.setObject(context, context.getResources().getString(R.string.cache_user), userBean);
        } else {
            SpUtil.setObject(context, context.getResources().getString(R.string.cache_user), null);
            TApplication.mUser = null;
        }
    }

    /**
     * 获取全局用户引用
     *
     * @param context
     * @return
     */
    public static UserBean getUserBean(Context context) {
        if (TApplication.mUser != null) {
            return TApplication.mUser;
        }
        UserBean locUserBean = (UserBean) SpUtil.getObject(context, context.getResources().getString(R.string.cache_user));
        if (locUserBean != null) {
            TApplication.mUser = locUserBean;
        }
        return TApplication.mUser;
    }


    /**
     * 获取我的信息
     */
    public static void getMyInfo(final Context context,final UserInfoListener listener,boolean isRefresh) {
        final  UserBean userBean=getUserBean(context);
        if (null != userBean && !TextUtils.isEmpty(userBean.getUid())) {
            if(userBean.getUserInfoBean()!=null){
              if(listener!=null){
                  listener.onCache(userBean);
              }
            }

            if(isRefresh||userBean.getUserInfoBean()==null){
                getUserInfo(userBean.getUid(), new BaseBiz.RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        userBean.setUserInfoBean((UserInfoBean) responseBean.getObject());
                        setUserBean(context, userBean);
                        if(listener!=null){
                            listener.onHttpGet(userBean);
                        }
                        /** 发送更新用户信息事件*/
                        EventBus.getDefault().post(new UserInfoEvent("PersionDetailActivity"));
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
            }
        } else {
            if(listener!=null){
                listener.onFail();
            }
        }
    }




    /**
     * 用户发帖
     *
     * @param fid     频道
     * @param type    类型
     * @param action  操作
     * @param tid     跟帖
     * @param subject 主题
     * @param content 内容
     * @param aids    上传的图片
     * @param handle  操作
     */
    public static void faTie(String fid, String type, String action, String tid, String subject, String content, String aids, final BaseBiz.RequestHandle handle) {
        RequestParams params = getParams();
        if (!TextUtils.isEmpty(fid)) {
            params.put("fid", fid);
        }
        if (!TextUtils.isEmpty(type)) {
            params.put("type", type);
        }
        if (!TextUtils.isEmpty(tid)) {
            params.put("tid", tid);
        }
        if (!TextUtils.isEmpty(subject)) {
            params.put("subject", subject);
        }
        if (!TextUtils.isEmpty(content)) {
            params.put("content", subject);
        }
        if (!TextUtils.isEmpty(aids)) {
            params.put("aids", aids);
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
     *
     * @param uid
     * @param handle
     */
    public static void getUserInfo(String uid, final BaseBiz.RequestHandle handle) {
        RequestParams params = getParams();
        if (!TextUtils.isEmpty(uid)) {
            params.put("uid", uid);
        }
        BaseBiz.getRequest(ServerConfig.MYINFO_URL, params, new BaseBiz.RequestHandle() {

            @Override
            public void onSuccess(ResponseBean responseBean) {

                String string = (String) responseBean.getObject();
                try {
                    JSONObject jsonObject=new JSONObject(string);
                    responseBean.setObject(BaseBean.newInstance(UserInfoBean.class, jsonObject.optJSONObject("userinfo")));
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
     *
     * @param userName
     * @param pwd
     * @param handle
     */
    public static void login(String userName, String pwd, final BaseBiz.RequestHandle handle) {
        RequestParams params = getParams();
        params.put("username", userName);
        params.put("password", pwd);
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
     *
     * @param userName
     * @param pwd
     * @param handle
     */
    public static void reg(String userName, String pwd, String email, String sex, final BaseBiz.RequestHandle handle) {
        RequestParams params = getParams();
        params.put("username", userName);
        params.put("password", pwd);
        params.put("email", email);
        params.put("sex", sex);

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

    /**
     * 获取我的发布
     * @param context
     * @param handle
     */
    public static void getMyPublish(Context context,int page,final BaseBiz.RequestHandle handle){
        RequestParams params = getParams();
        params.put("a", "myread");
        params.put("page", page);
        BaseBiz.postRequest(context,ServerConfig.GETAPP_URL, params, new BaseBiz.RequestHandle() {

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
     * 校验手机或者邮箱的验证码
     * @param phoneOrMail
     * @param code
     * @param type
     */
    public static void verifyCode(Context context,String phoneOrMail,String code,String type,final BaseBiz.RequestHandle handle){
        RequestParams params = getParams();
        if(TextUtils.isEmpty(type)){
            type="mobile";
        }
        if("mobile".equals(type)){
            params.put("mobile", phoneOrMail);
            params.put("type", "ismcode");
        }else{
            params.put("email", phoneOrMail);
            params.put("type", "isecode");
        }
//        params.put("a", "smscode");
        params.put("code", code);



        BaseBiz.postRequest(context,ServerConfig.CHECK_CODE_URL, params, new BaseBiz.RequestHandle() {

            @Override
            public void onSuccess(ResponseBean responseBean) {


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
     * 校验手机或者邮箱的验证码
     * @param phoneOrMail
     * @param type mobile 手机验证码 email 邮件验证码
     */
    public static void getVerifyCode(Context context,String phoneOrMail,String type,final BaseBiz.RequestHandle handle){
        RequestParams params = getParams();
        if(TextUtils.isEmpty(type)){
            type="mobile";
        }
        if("mobile".equals(type)){
            params.put("mobile", phoneOrMail);
        }else{
            params.put("email", phoneOrMail);
        }
        params.put("type", type);
        params.put("a", "smscode");
        BaseBiz.postRequest(context,ServerConfig.CHECK_CODE_URL, params, new BaseBiz.RequestHandle() {

            @Override
            public void onSuccess(ResponseBean responseBean) {
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
     * 上传图片
     * @param path
     * @param handle
     * @throws FileNotFoundException
     */
    public static void uploadHeadImg(String path,final SystemBiz.UploadImgListener handle) throws FileNotFoundException {
        RequestParams params= getParams();
        params.put("a","userinfo");
        params.put("ac","upface");
        File file =new File(path);
        if(!file.exists()){
            return;
        }
        params.put("file",file);
        AppHttpClient.post(ServerConfig.GETAPP_URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String responseStr = new String(responseBody);

                ResponseBean responseBean = new ResponseBean();
                Log.d("data:" + responseStr);
                try {
                    JSONObject jsonObject = new JSONObject(responseStr);
                    responseBean.setInfo(jsonObject.optString("msg"));
                    responseBean.setStatus(jsonObject.optString("code"));
                    JSONObject data=jsonObject.optJSONObject("data");
                    if(data!=null){
                        responseBean.setObject(data.optString("face"));
                    }
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

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                ResponseBean responseBean = new ResponseBean();
                responseBean.setStatus(statusCode + "");
                if(null!=responseBody){
                    responseBean.setInfo(new String(responseBody));
                }
                handle.onFail(responseBean);
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                int progress= (int) (bytesWritten*100/totalSize);
                if(progress==100){
                    handle.onFinish();
                }
                handle.onProgress(progress);
            }
        });
    }


    public static void updateUserInfo(Context context,String sex,String qq, String wx,final BaseBiz.RequestHandle handle){

    }




}
