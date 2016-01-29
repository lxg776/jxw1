package com.xiwang.jxw.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.UploadImgesAdapter;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.util.CheckUtil;
import com.xiwang.jxw.widget.DeleteAutoCompleteTextView;
import com.xiwang.jxw.widget.DeleteEditText;
import com.xiwang.jxw.widget.MyDatePickView;
import com.xiwang.jxw.widget.MyTextSelectView;
import com.xiwang.jxw.widget.UploadImgView;

//import jp.wasabeef.richeditor.RichEditor;

/**
 * 求职界面
 * Created by sunshine on 15/12/7.
 */
public class ApplyWorkActivity extends BaseSubmitActivity{
    /**姓名*/
    DeleteEditText name_edt;
    /**出生年月*/
    MyDatePickView  birthDate_pv;
    /**性别*/
    MyTextSelectView sex_sv;
    /**最高学历*/
    MyTextSelectView education_sv;
    /**工作年限*/
    MyTextSelectView workExperience_sv;
    /**手机号码*/
    DeleteEditText  phone_edt;
    /**期望职位*/
    DeleteAutoCompleteTextView position_tv;
    /**期望薪资*/
    MyTextSelectView salary_sv;
    /**自我评价*/
    EditText pingjia_edt;
    @Override
    protected String getPageName() {
        return "求职";
    }

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("发布求职简历");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_finish) {
                    submit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_apple_work;
    }

    @Override
    protected void findViews() {

        name_edt = (DeleteEditText) findViewById(R.id.name_edt);
        birthDate_pv = (MyDatePickView) findViewById(R.id.birthDate_pv);
        sex_sv = (MyTextSelectView) findViewById(R.id.sex_sv);
        education_sv= (MyTextSelectView) findViewById(R.id.education_sv);
        workExperience_sv= (MyTextSelectView) findViewById(R.id.workExperience_sv);
        phone_edt= (DeleteEditText) findViewById(R.id.phone_edt);
        position_tv= (DeleteAutoCompleteTextView) findViewById(R.id.position_tv);
        salary_sv= (MyTextSelectView) findViewById(R.id.salary_sv);
        pingjia_edt= (EditText) findViewById(R.id.pingjia_edt);

    }



    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {
        sex_sv.setShowItemes(getResources().getStringArray(R.array.select_sex));
        education_sv.setShowItemes(getResources().getStringArray(R.array.select_education));
        workExperience_sv.setShowItemes(getResources().getStringArray(R.array.select_workExperience));
        String postionesString=getResources().getString(R.string.postions_str);
        //职位列表设置
        String [] postiones=postionesString.split(",");
        ArrayAdapter arrayAdapter=new ArrayAdapter(context,R.layout.item_autotext,postiones);
        position_tv.setAdapter(arrayAdapter);
        salary_sv.setShowItemes(getResources().getStringArray(R.array.select_salary));
    }

    @Override
    protected void widgetListener() {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finlish, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected boolean checkInput() {

        String name_text=name_edt.getText().toString();
        String brith_text=birthDate_pv.getText();
        String sex_text=sex_sv.getText();
        String education_text=education_sv.getText();
        String workExperience_text= workExperience_sv.getText();
        String phone_text=phone_edt.getText().toString();
        String postion_text=position_tv.getText().toString();
        String salary_text=salary_sv.getText();
        String pingjia_text=pingjia_edt.getText().toString();
        if(CheckUtil.isEmpty(context,"姓名",name_text)){
            return false;
        }
        if(CheckUtil.isEmpty(context, "出生日期", brith_text)){
            return false;
        }
        if(CheckUtil.isSelect(context, "性别", sex_text)) {
            return false;
        }
        if(CheckUtil.isSelect(context, "最高学历", education_text)) {
            return false;
        }
        if(CheckUtil.isSelect(context, "工作经验", workExperience_text)) {
            return false;
        }
        if(CheckUtil.isEmpty(context, "手机号码", phone_text)) {
            return false;
        }
        if(!CheckUtil.checkMobileNumber(context,phone_text)){
            return  false;
        }

        if(CheckUtil.isEmpty(context, "期望职位", postion_text)) {
            return false;
        }
        if(CheckUtil.isSelect(context, "期望薪水", salary_text)) {
            return false;
        }
        if(CheckUtil.isEmpty(context, "自我评价", pingjia_text)) {
            return false;
        }
        return true;
    }
}
