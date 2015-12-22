package com.xiwang.jxw.activity;


import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.biz.UserBiz;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.event.LoginEvent;
import com.xiwang.jxw.util.CheckUtil;
import com.xiwang.jxw.util.IntentUtil;
import com.xiwang.jxw.util.ProcessDialogUtil;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.util.ToastUtil;
import com.xiwang.jxw.widget.DeleteEditText;
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
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
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
            ProcessDialogUtil.showDialog(context,"数据加载中...",false);
            UserBiz.login(userName, pwd, new BaseBiz.RequestHandle() {
                @Override
                public void onSuccess(ResponseBean responseBean) {
                    TApplication.mUser= (UserBean) responseBean.getObject();
                    TApplication.mUser.setPwd(pwd);
                    /** 保存用户*/
                    SpUtil.setObject(context,getString(R.string.cache_user),TApplication.mUser);
                    /** 发出登录成功通知*/
                    EventBus.getDefault().post(new LoginEvent(true));
                    ProcessDialogUtil.dismissDialog();
                    setResult(RESULT_OK);
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

                break;
        }
    }
}
