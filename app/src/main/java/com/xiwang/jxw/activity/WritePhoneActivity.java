package com.xiwang.jxw.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.event.DeleteImageEvent;
import com.xiwang.jxw.event.FinishEvent;
import com.xiwang.jxw.util.CheckUtil;
import com.xiwang.jxw.util.IntentUtil;
import com.xiwang.jxw.widget.MyInputEditView2;

/**
 * 填写手机号码activity
 * Created by liangxg on 2016/2/23.
 */
public class WritePhoneActivity extends BaseSubmitActivity implements  View.OnClickListener {


    MyInputEditView2 edit_phone;
    TextView btn_next;

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
        return R.layout.activity_write_phone2;
    }

    @Override
    protected void findViews() {
        edit_phone= (MyInputEditView2) findViewById(R.id.edit_phone);
        edit_phone.getInput_edt().setInputType(InputType.TYPE_CLASS_PHONE);
        btn_next= (TextView) findViewById(R.id.btn_next);


    }

    @Override
    protected void init() {
        setActivityTag("WritePhoneActivity");
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        btn_next.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                if(checkInput()){
                    Bundle bundle=new Bundle();
                    bundle.putString(getString(R.string.send_data),edit_phone.getText().toString());
                    IntentUtil.gotoActivity(context,AuthPhoneActivity.class,bundle);
                }
            break;
        }
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

    public void onEvent(FinishEvent event){
        if (event.tag.equals(activityTag)){
            finish();
        }
    }
}

