package com.xiwang.jxw.activity;


import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.widget.DeleteEditText;

/**
 * @author lxg776
 * @description 登录Activity
 * @date 2015/11/2
 * @modifier
 */
public class LoginActivity extends BaseActivity {
    /** 用户名*/
    DeleteEditText username_edt;

    /** 密码*/
    DeleteEditText pwd_edt;

    @Override
    protected void initActionBar() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews() {
        username_edt= (DeleteEditText) findViewById(R.id.username_edt);
        pwd_edt= (DeleteEditText) findViewById(R.id.pwd_edt);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }
}
