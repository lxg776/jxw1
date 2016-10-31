package com.xiwang.jxw.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.biz.PublishBiz;
import com.xiwang.jxw.util.CheckUtil;
import com.xiwang.jxw.util.ToastUtil;
import com.xiwang.jxw.widget.DeleteAutoCompleteTextView;
import com.xiwang.jxw.widget.DeleteEditText;
import com.xiwang.jxw.widget.MyTextSelectView;


/**
 * 租房activity
 * Created by liangxg on 2016/1/28.
 */
public class RentalActivity extends BaseSubmitActivity{

    /**房/位/厅*/
    DeleteAutoCompleteTextView fwt_tv;
    /**面积*/
    DeleteAutoCompleteTextView mianji_tv;
    /**期望房租*/
    MyTextSelectView fangzu_sv;
    /**标题*/
    DeleteEditText title_edt;
    /**描述*/
    EditText content_edt;

    /**联系人*/
    DeleteEditText linkman_edt;
    /**联系电话*/
    DeleteEditText phone_edt;

    /**房/位/厅 数据*/
    String [] fwtArray;
    /**房租 数据*/
    String []fangZuArray;


    @Override
    protected void findViews() {
        fwt_tv= (DeleteAutoCompleteTextView) findViewById(R.id.fwt_tv);
        mianji_tv= (DeleteAutoCompleteTextView) findViewById(R.id.mianji_tv);
        fangzu_sv= (MyTextSelectView) findViewById(R.id.fangzu_sv);
        title_edt= (DeleteEditText) findViewById(R.id.title_edt);
        content_edt= (EditText) findViewById(R.id.content_edt);
        linkman_edt= (DeleteEditText) findViewById(R.id.linkman_edt);
        phone_edt= (DeleteEditText) findViewById(R.id.phone_edt);

    }


    @Override
    protected boolean checkInput() {
        String fwt_text=fwt_tv.getText().toString();
        String fangzu_text=fangzu_sv.getText();
        String mianji_text=mianji_tv.getText().toString();
        String title_text=title_edt.getText().toString();
        String desc_text=content_edt.getText().toString();
        String linkman_text=linkman_edt.getText().toString();
        String phone_text=phone_edt.getText().toString();

        if(CheckUtil.isEmpty(context,"房/厅/卫",fwt_text)){
            return  false;
        }
        if(CheckUtil.isSelect(context, "面积", mianji_text)){
            return  false;
        }

        if(CheckUtil.isSelect(context, "期望房租", fangzu_text)){
            return  false;
        }
        if(CheckUtil.isEmpty(context, "标题", title_text)){
            return  false;
        }
        if(CheckUtil.isEmpty(context, "描述内容", desc_text)){
            return  false;
        }
        if(CheckUtil.isEmpty(context, "联系人", linkman_text)){
            return  false;
        }
        if(CheckUtil.isEmpty(context, "联系电话", phone_text)){
            return  false;
        }
        if(!CheckUtil.checkMobileNumber(context,phone_text)){
            return false;
        }



        return true;
    }

    @Override
    protected String getPageName() {
        return "租房";
    }

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("求租");
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
    protected void submit() {
        if(!checkInput()){
            return;
        }
        String fwt_text=fwt_tv.getText().toString();
        String fangzu_text=fangzu_sv.getText();
        String mianji_text=mianji_tv.getText().toString();
        String title_text=title_edt.getText().toString();
        String desc_text=content_edt.getText().toString();
        String linkman_text=linkman_edt.getText().toString();
        String phone_text=phone_edt.getText().toString();


        PublishBiz.publicHouse("qhire", fwt_text, mianji_text, fangzu_text, title_text, desc_text, linkman_text, phone_text, "", "", new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                ToastUtil.showToast(context,responseBean.getInfo());
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
    protected int getContentViewId() {
        return R.layout.activity_rental;
    }



    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {
        fwtArray=getResources().getStringArray(R.array.select_ftw);
        fangZuArray=getResources().getStringArray(R.array.select_fangzu);

        ArrayAdapter arrayAdapter=new ArrayAdapter<String>(this,R.layout.item_autotext,fwtArray);
        fwt_tv.setAdapter(arrayAdapter);

        fangzu_sv.setShowItemes(fangZuArray);

        fangzu_sv.getTitleText();

    }

    @Override
    protected void widgetListener() {

    }
}
