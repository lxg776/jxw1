package com.xiwang.jxw.activity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.biz.PublishBiz;
import com.xiwang.jxw.util.CheckUtil;
import com.xiwang.jxw.util.ProcessDialogUtil;
import com.xiwang.jxw.util.ToastUtil;
import com.xiwang.jxw.widget.DeleteAutoCompleteTextView;
import com.xiwang.jxw.widget.DeleteEditText;
import com.xiwang.jxw.widget.MyTextSelectView;

/**
 * 招聘Activity
 * Created by liangxg on 2016/5/3.
 */
public class EmployActivity extends BaseSubmitActivity {
    /**招聘职位*/
    DeleteAutoCompleteTextView position_tv;
    /**职位描述*/
    DeleteAutoCompleteTextView duty_tv;
    /**能力描述*/
    DeleteAutoCompleteTextView ability_tv;
    /**工作经验*/
    MyTextSelectView experience_sv;
    /**工资待遇*/
    MyTextSelectView wages_sv;
    /**工作地点*/
    DeleteAutoCompleteTextView workaddress_tv;
    /**公司名称*/
    DeleteAutoCompleteTextView company_tv;
    /**联系人*/
    DeleteAutoCompleteTextView cname_tv;
    /**联系号码*/
    DeleteAutoCompleteTextView  mobile_tv;
    /**标题*/
    DeleteEditText title_edt;
    /**内容*/
    EditText content_edt;
    /**招聘人数*/
    DeleteAutoCompleteTextView number_tv;
    @Override
    protected String getPageName() {
        return "发布招聘页面";
    }

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("招聘");
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
        return R.layout.activity_employ;
    }

    @Override
    protected void findViews() {
        position_tv= (DeleteAutoCompleteTextView) findViewById(R.id.position_tv);
        duty_tv= (DeleteAutoCompleteTextView) findViewById(R.id.duty_tv);
        ability_tv= (DeleteAutoCompleteTextView) findViewById(R.id.ability_tv);
        experience_sv= (MyTextSelectView) findViewById(R.id.experience_sv);
        wages_sv= (MyTextSelectView) findViewById(R.id.wages_sv);
        workaddress_tv= (DeleteAutoCompleteTextView) findViewById(R.id.workaddress_tv);
        company_tv= (DeleteAutoCompleteTextView) findViewById(R.id.company_tv);
        cname_tv= (DeleteAutoCompleteTextView) findViewById(R.id.cname_tv);
        mobile_tv= (DeleteAutoCompleteTextView) findViewById(R.id.mobile_tv);
        title_edt= (DeleteEditText) findViewById(R.id.title_edt);
        content_edt= (EditText) findViewById(R.id.content_edt);
        number_tv= (DeleteAutoCompleteTextView) findViewById(R.id.number_tv);
    }

    @Override
    protected void init() {
        String[] experienceArray=getResources().getStringArray(R.array.select_workExperience);
        experience_sv.setShowItemes(experienceArray);
        String[] salaryArray=getResources().getStringArray(R.array.select_salary);
        wages_sv.setShowItemes(salaryArray);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected boolean checkInput() {
        String positionStr=position_tv.getText().toString();
        String numberStr=number_tv.getText().toString();
        String dustStr=duty_tv.getText().toString();
        String abilityStr=ability_tv.getText().toString();
        String experienceStr=experience_sv.getText();
        String companyStr=company_tv.getText().toString();
        String workaddressStr=workaddress_tv.getText().toString();
        String cnameStr=cname_tv.getText().toString();
        String moblieStr=mobile_tv.getText().toString();
        String titleStr=title_edt.getText().toString();
        String wagesStr=wages_sv.getText().toString();

        if(CheckUtil.isEmpty(context,"招聘职位",positionStr)){
            return false;
        }
        if(CheckUtil.isEmpty(context,"招聘人数",numberStr)){
            return false;
        }
        if(CheckUtil.isEmpty(context,"职位描述",dustStr)){
            return false;
        }
        if(CheckUtil.isEmpty(context, "能力要求", abilityStr)){
            return false;
        }
        if(CheckUtil.isSelect(context, "工作经验", experienceStr)){
            return false;
        }
        if(CheckUtil.isSelect(context, "工资待遇", wagesStr)){
            return false;
        }
        if(CheckUtil.isEmpty(context, "工作地点", workaddressStr)){
            return false;
        }
        if(CheckUtil.isEmpty(context, "公司名称", companyStr)){
            return false;
        }
        if(CheckUtil.isEmpty(context, "联系人", cnameStr)){
            return false;
        }
        if(CheckUtil.isEmpty(context, "联系号码", moblieStr)){
            return false;
        }
        if(!CheckUtil.checkMobileNumber(context,moblieStr)){
            return false;
        }

        if(CheckUtil.isEmpty(context, "标题", titleStr)){
            return false;
        }

        return true;
    }

    @Override
    protected void submit() {
        if(checkInput()){
            String positionStr=position_tv.getText().toString();
            String numberStr=number_tv.getText().toString();
            String dustStr=duty_tv.getText().toString();
            String abilityStr=ability_tv.getText().toString();
            String experienceStr=experience_sv.getText();
            String workaddressStr=workaddress_tv.getText().toString();
            String cnameStr=cname_tv.getText().toString();
            String moblieStr=mobile_tv.getText().toString();
            String titleStr=title_edt.getText().toString();
            String companyStr=company_tv.getText().toString();
            String wagesStr=wages_sv.getText().toString();
            String contentStr=content_edt.getText().toString();

            ProcessDialogUtil.showDialog(context, getResources().getString(R.string.loading), true);
            PublishBiz.publishEmploy(positionStr, companyStr, abilityStr, dustStr, experienceStr, wagesStr, workaddressStr, numberStr, cnameStr, moblieStr, titleStr, contentStr, new BaseBiz.RequestHandle() {
                @Override
                public void onSuccess(ResponseBean responseBean) {
                    ProcessDialogUtil.dismissDialog();
                    ToastUtil.showToast(context, responseBean.getInfo());
                    finish();

                }

                @Override
                public void onFail(ResponseBean responseBean) {

                    ToastUtil.showToast(context, responseBean.getInfo());
                    ProcessDialogUtil.dismissDialog();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finlish, menu);
        return true;
    }
}
