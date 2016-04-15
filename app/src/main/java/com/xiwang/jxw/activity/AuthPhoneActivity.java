package com.xiwang.jxw.activity;


import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.biz.UserBiz;
import com.xiwang.jxw.event.FinishEvent;
import com.xiwang.jxw.event.UserInfoEvent;
import com.xiwang.jxw.util.CheckUtil;
import com.xiwang.jxw.util.ToastUtil;
import com.xiwang.jxw.widget.MsgCodeInputEditView;
import de.greenrobot.event.EventBus;

/**
 * 校验手机号码
 * Created by liangxg on 2016/2/23.
 */
public class AuthPhoneActivity extends BaseSubmitActivity implements  View.OnClickListener {



    TextView btn_finish;
    TextView phone_hint_tv;
    String textTemp = "<ul>请输入手机号	<font color=\"#FF0000\">#th1#</font>	收到的短信验证码。</ul>";

    String send_phone;

    MsgCodeInputEditView edit_identifyingcode;

    @Override
    protected String getPageName() {
        return "填写手机号码";
    }

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("绑定手机");
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
        return R.layout.activity_auth_phone;
    }

    @Override
    protected void findViews() {
        btn_finish= (TextView) findViewById(R.id.btn_finish);
        phone_hint_tv= (TextView) findViewById(R.id.phone_hint_tv);
        edit_identifyingcode= (MsgCodeInputEditView) findViewById(R.id.edit_identifyingcode);





    }

    @Override
    protected void init() {
        phone_hint_tv.setText(Html.fromHtml(textTemp.replace("#th1#",send_phone)));
    }

    @Override
    protected void initGetData() {
        send_phone=getIntent().getStringExtra(getString(R.string.send_data));
    }

    @Override
    protected void widgetListener() {
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){

                    UserBiz.verifyCode(AuthPhoneActivity.this,send_phone, edit_identifyingcode.getText().toString(), "mobile", new BaseBiz.RequestHandle() {
                        @Override
                        public void onSuccess(ResponseBean responseBean) {


                            /**设置本地缓存*/
                            UserBean userBean= UserBiz.getUserBean(context);
                            userBean.getUserInfoBean().setCellphone(send_phone);
                            UserBiz.setUserBean(context, userBean);
                            /**更新个人详情的显示*/
                            EventBus.getDefault().post(new UserInfoEvent("PersionDetailActivity"));
                            /**结束输入手机号码页面*/
                            ToastUtil.showToast(context, responseBean.getInfo());
                            EventBus.getDefault().post(new FinishEvent("WritePhoneActivity"));
                            finish();

                        }

                        @Override
                        public void onFail(ResponseBean responseBean) {
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
            }
        });

        edit_identifyingcode.setSendListener(new MsgCodeInputEditView.SendListener() {
            @Override
            public void sendCode() {
                /*
                获取验证码
                 */
                UserBiz.getVerifyCode(AuthPhoneActivity.this,send_phone, "mobile", new BaseBiz.RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        ToastUtil.showToast(context,responseBean.getInfo());
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
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
        });

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected boolean checkInput() {
        String codeText=edit_identifyingcode.getText().toString();
        if(CheckUtil.isEmpty(context,"验证码",codeText)){
            return false;
        }
        return true;
    }
}

