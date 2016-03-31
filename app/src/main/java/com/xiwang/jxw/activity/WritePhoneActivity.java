package com.xiwang.jxw.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.TextAdapter;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.biz.UserBiz;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.util.CheckUtil;
import com.xiwang.jxw.util.CommonUtil;
import com.xiwang.jxw.util.DialogUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.widget.MyInputEditView2;

/**
 * 填写手机号码activity
 * Created by liangxg on 2016/2/23.
 */
public class WritePhoneActivity extends BaseSubmitActivity implements  View.OnClickListener {


    MyInputEditView2 edit_phone;

    @Override
    protected String getPageName() {
        return "填写手机号码";
    }

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("个人信息");
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
        return R.layout.activity_write_phone2;
    }

    @Override
    protected void findViews() {
        edit_phone= (MyInputEditView2) findViewById(R.id.edit_phone);
        edit_phone.getInput_edt().setInputType(InputType.TYPE_CLASS_PHONE);


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


    @Override
    public void onClick(View v) {

    }

    @Override
    protected boolean checkInput() {
        String phoneText=edit_phone.getText().toString();
        if(CheckUtil.isEmpty(context,"手机号码",phoneText)){
            return false;
        }


        if(!CheckUtil.checkMobileNumber(context,phoneText)){
            return false;
        }
        return true;
    }
}

