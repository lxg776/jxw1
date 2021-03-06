package com.xiwang.jxw.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.biz.UserBiz;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.event.LoginEvent;
import com.xiwang.jxw.event.RegEvent;
import com.xiwang.jxw.util.CheckUtil;
import com.xiwang.jxw.util.IntentUtil;
import com.xiwang.jxw.util.ProcessDialogUtil;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.util.ToastUtil;
import com.xiwang.jxw.widget.DeleteEditText;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * @author lxg776
 * @description 登录Activity
 * @date 2015/11/2
 * @modifier
 */
public class LoginActivity extends BaseSubmitActivity implements View.OnClickListener{
    /** 用户名*/
    DeleteEditText username_edt;
    /** 密码*/
    DeleteEditText pwd_edt;
    /** 登录按钮*/
    TextView login_btn;
    /** 注册按钮*/
    TextView register_btn;
    /** 自动登录*/
    TextView tx_login_btn;

    @Override
    protected String getPageName() {
        return "登录a";
    }

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("登录");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }



    @Override
    protected void findViews() {
        username_edt= (DeleteEditText) findViewById(R.id.username_edt);
        pwd_edt= (DeleteEditText) findViewById(R.id.pwd_edt);
        login_btn= (TextView) findViewById(R.id.login_btn);
        register_btn= (TextView) findViewById(R.id.register_btn);
        tx_login_btn= (TextView) findViewById(R.id.tx_login_btn);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {
        String userName= (String) SpUtil.getObject(this,getString(R.string.cache_userName));
        if(TextUtils.isEmpty(userName)){
            username_edt.setText(userName);
        }
    }

    @Override
    protected void widgetListener() {
        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        tx_login_btn.setOnClickListener(this);
    }

    /**
     * 校验输入
     * @return
     */
    protected boolean checkInput(){
        String userName=username_edt.getText().toString();
        String pwd=pwd_edt.getText().toString();
        if(!CheckUtil.isEmpty(context,getResources().getString(R.string.login_username),userName)){
            return false;
        }
        if(!CheckUtil.isEmpty(context,getResources().getString(R.string.login_pwd),pwd)){
            return false;
        }
        return true;
    }

    /**
     * 用户登录
     */
    protected void submit(){
        super.submit();
        String userName=username_edt.getText().toString();
        final String pwd=pwd_edt.getText().toString();
        if(CheckUtil.isEmpty(context,"用户名",userName)){
            return;
        }
        if(CheckUtil.isEmpty(context,"密码",pwd)){
            return;
        }


        ProcessDialogUtil.showDialog(context, getResources().getString(R.string.loading), false);
        UserBiz.login(userName, pwd, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                TApplication.mUser = (UserBean) responseBean.getObject();
                TApplication.mUser.setPwd(pwd);
                UserBiz.setUserBean(context, TApplication.mUser);
                /** 发出登录成功通知*/
                EventBus.getDefault().post(new LoginEvent(true));
                ProcessDialogUtil.dismissDialog();
                Intent intent = new Intent();
                Bundle bundle = getIntent().getExtras();
                if (null != bundle)
                    intent.putExtras(bundle);
                setResult(RESULT_OK, intent);

                /** 帐号登录，记录友盟*/
                MobclickAgent.onProfileSignIn(TApplication.mUser.getUid() + "_" + TApplication.mUser.getUsername());
                finish();
            }

            @Override
            public void onFail(ResponseBean responseBean) {

                ProcessDialogUtil.dismissDialog();
                ToastUtil.showToast(context, responseBean.getInfo());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /** 登录*/
            case  R.id.login_btn:
            {
                submit();
            }
            break;
            /** 注册*/
            case R.id.register_btn:
                IntentUtil.gotoActivity(context,RegisterActivity.class);
//                mShareAPI.deleteOauth(LoginActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
//                    @Override
//                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//                        ToastUtil.showToast(context, "取消成功!");
//                    }
//
//                    @Override
//                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onCancel(SHARE_MEDIA share_media, int i) {
//
//                    }
//                });
                break;
            case R.id.tx_login_btn:
                qq_login();
                break;
        }
    }


    UMShareAPI mShareAPI;

    /**
     * qq登录
     */
    private void qq_login(){
      final  SHARE_MEDIA platform = SHARE_MEDIA.QQ;
        if(mShareAPI==null){
            mShareAPI = UMShareAPI.get(this);
        }


        if(mShareAPI.isInstall(this,platform)){
//            if(mShareAPI.isAuthorize(this,platform)){
//                mShareAPI.getPlatformInfo(this, platform, new UMAuthListener() {
//
//                    @Override
//                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//                        //ToastUtil.showToast(context, "获取成功!");
//                        authLogin(map);
//                    }
//
//                    @Override
//                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//                        ToastUtil.showToast(context, "获取失败!");
//                    }
//
//                    @Override
//                    public void onCancel(SHARE_MEDIA share_media, int i) {
//                        ToastUtil.showToast(context, "获取取消!");
//                    }
//                });
//            }else{
                mShareAPI.doOauthVerify(this, platform, new UMAuthListener() {

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

                        mShareAPI.getPlatformInfo(LoginActivity.this, platform, new UMAuthListener() {

                            @Override
                            public void onComplete(SHARE_MEDIA share_media, int i,final Map<String, String> map) {
                              //  ToastUtil.showToast(context, "获取成功!");
                                authLogin(map);
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                                ToastUtil.showToast(context, "获取失败!");
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media, int i) {
                                ToastUtil.showToast(context, "获取取消!");
                            }
                        });


                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        ToastUtil.showToast(context, "授权失败!");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        ToastUtil.showToast(context, "授权取消!");
                    }
                });
          //  }
        }else{
            ToastUtil.showToast(context,"未安装腾讯QQ");

        }

    }

    /**
     * 授权登录
     * @param map
     */
    private void authLogin(Map<String, String> map){
        if(map==null){
                return;
        }
        /**性别*/
        final String gender=map.get("gender");
        /**uid*/
        String openid=map.get("openid");
        /**显示名称*/
        String screen_name=map.get("screen_name");
        /**头像地址*/
        final String profile_image_url=map.get("profile_image_url");

        final  String platform = "qq";

        ProcessDialogUtil.showDialog(context, getResources().getString(R.string.loading), false);
        UserBiz.otherLogin(LoginActivity.this, openid, platform, screen_name, gender,profile_image_url, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                ProcessDialogUtil.dismissDialog();
                ToastUtil.showToast(context, responseBean.getInfo());
                TApplication.mUser = (UserBean) responseBean.getObject();
                TApplication.mUser.setPlatform(platform);
                TApplication.mUser.setFace(profile_image_url);
                TApplication.mUser.setSex(gender);
                TApplication.mUser.setOpenid("openid");
                UserBiz.setUserBean(context, TApplication.mUser);
                /** 发出登录成功通知*/
                EventBus.getDefault().post(new LoginEvent(true));
                ProcessDialogUtil.dismissDialog();
                Intent intent = new Intent();
                Bundle bundle = getIntent().getExtras();
                if (null != bundle)
                    intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                /** 帐号登录，记录友盟*/
                MobclickAgent.onProfileSignIn(TApplication.mUser.getUid() + "_" + TApplication.mUser.getUsername());
                finish();
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                ProcessDialogUtil.dismissDialog();
                ToastUtil.showToast(context,responseBean.getInfo());
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








    @Override
    protected boolean useEventBus() {
        return true;
    }


    public void onEvent(RegEvent event) {
        /**注册成功结束当前界面*/
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
}
