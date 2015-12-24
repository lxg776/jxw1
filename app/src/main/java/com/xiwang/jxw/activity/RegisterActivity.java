package com.xiwang.jxw.activity;


import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.util.CheckUtil;
import com.xiwang.jxw.util.IntentUtil;
import com.xiwang.jxw.util.ToastUtil;
import com.xiwang.jxw.widget.DeleteEditText;

/**
 * @author lxg776
 * @description 登录Activity
 * @date 2015/11/2
 * @modifier
 */
public class RegisterActivity extends BaseSubmitActivity implements View.OnClickListener{
    /**用户名 */
    DeleteEditText username_edt;
    /**密码 */
    DeleteEditText pwd_edt;
    /**确认密码 */
    DeleteEditText re_pwd_edt;
    /**email */
    DeleteEditText email_edt;

    /**条款*/
    View check_btn;
    /**是否同意条款*/
    boolean isAgreeTerms=false;
    /**注册按钮*/
    TextView regiter_btn;
    /**服务协议按钮*/
    TextView xieyi_btn;


    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("用户注册");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 改变同意条款按钮
     */
    private void changeCheckView(){
        if(isAgreeTerms){
            check_btn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.image_choose));
        }else{
            check_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.no_checked_btn));
        }
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_regiter;
    }

    @Override
    protected void findViews() {
        username_edt= (DeleteEditText) findViewById(R.id.username_edt);
        pwd_edt= (DeleteEditText) findViewById(R.id.pwd_edt);
        re_pwd_edt= (DeleteEditText) findViewById(R.id.re_pwd_edt);
        email_edt= (DeleteEditText) findViewById(R.id.email_edt);
        check_btn=findViewById(R.id.check_btn);
        regiter_btn= (TextView) findViewById(R.id.regiter_btn);
        xieyi_btn= (TextView) findViewById(R.id.xieyi_btn);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        check_btn.setOnClickListener(this);
        regiter_btn.setOnClickListener(this);
        xieyi_btn.setOnClickListener(this);
    }

    @Override
    protected boolean checkInput() {
       if(CheckUtil.isEmpty(context,"用户名",username_edt.getText().toString())){
           return false;
       }

        if(CheckUtil.isEmpty(context,"email",email_edt.getText().toString())){
            return false;
        }
        String pwd=pwd_edt.getText().toString();
        String re_pwd=re_pwd_edt.getText().toString();

        if(CheckUtil.isEmpty(context,"密码",pwd)){
            return false;
        }
        if(CheckUtil.isEmpty(context,"确认密码",re_pwd)){
            return false;
        }
        if(!pwd.equals(re_pwd)){
            ToastUtil.showToast(context,"两次密码输入不一致！");
            return false;
        }
        if(!isAgreeTerms){
            ToastUtil.showToast(context,"请先同意西网服务协议");
            return false;
        }

        return true;
    }

    /**
     * 注册提交
     */
    @Override
    protected void submit() {
        super.submit();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /**
             * 同意注册条款
             */
            case R.id.check_btn:
                isAgreeTerms=!isAgreeTerms;
                changeCheckView();
                break;
            /**
             * 注册
             */
            case  R.id.regiter_btn:
                submit();
                break;
            /**
             * 查看服务协议
             */
            case  R.id.xieyi_btn:
                IntentUtil.gotoActivity(context,ServiceProtocolActivity.class);
                break;
        }
    }
}
