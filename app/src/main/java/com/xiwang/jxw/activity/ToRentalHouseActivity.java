package com.xiwang.jxw.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.util.CheckUtil;
import com.xiwang.jxw.widget.DeleteAutoCompleteTextView;
import com.xiwang.jxw.widget.DeleteEditText;
import com.xiwang.jxw.widget.MyTextSelectView;
import com.xiwang.jxw.widget.UploadImgView;


/**
 * 出租房屋activity
 * Created by liangxg on 2016/1/28.
 */
public class ToRentalHouseActivity extends BaseSubmitActivity{

    /**房/位/厅*/
    DeleteAutoCompleteTextView fwt_tv;
    /**期望房租*/
    DeleteAutoCompleteTextView mianji_tv;
    /**标题*/
    DeleteEditText title_edt;
    /**描述*/
    EditText content_edt;
    /**联系人*/
    DeleteEditText linkman_edt;
    /**联系电话*/
    DeleteEditText phone_edt;
    /**出售or出租*/
    MyTextSelectView type_sv;
    /**出售价格*/
    DeleteAutoCompleteTextView  price_tv;
    /**房租相片*/
    UploadImgView uploadView;


    @Override
    protected void findViews() {
        fwt_tv= (DeleteAutoCompleteTextView) findViewById(R.id.fwt_tv);
        mianji_tv= (DeleteAutoCompleteTextView) findViewById(R.id.mianji_tv);
        title_edt= (DeleteEditText) findViewById(R.id.title_edt);
        content_edt= (EditText) findViewById(R.id.content_edt);
        linkman_edt= (DeleteEditText) findViewById(R.id.linkman_edt);

    }


    @Override
    protected boolean checkInput() {
        String fwt_text=fwt_tv.getText().toString();




        return true;
    }

    @Override
    protected String getPageName() {
        return "租房";
    }

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("发布房产信息");
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
        return R.layout.activity_to_rental_house;
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
