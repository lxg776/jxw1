package com.xiwang.jxw.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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

    DeleteAutoCompleteTextView mAddressTv;


    @Override
    protected void findViews() {

        fwt_tv= (DeleteAutoCompleteTextView) findViewById(R.id.fwt_tv);
        mianji_tv= (DeleteAutoCompleteTextView) findViewById(R.id.mianji_tv);
        title_edt= (DeleteEditText) findViewById(R.id.title_edt);
        content_edt= (EditText) findViewById(R.id.content_edt);
        linkman_edt= (DeleteEditText) findViewById(R.id.linkman_edt);
        phone_edt= (DeleteEditText) findViewById(R.id.phone_edt);
        type_sv=(MyTextSelectView) findViewById(R.id.type_sv);
        price_tv=(DeleteAutoCompleteTextView) findViewById(R.id.price_tv);
        mAddressTv= (DeleteAutoCompleteTextView) findViewById(R.id.address_tv);
        uploadView=(UploadImgView) findViewById(R.id.uploadView);

    }


    @Override
    protected boolean checkInput() {
        String fwt_text=fwt_tv.getText().toString();
        String mianji_text=mianji_tv.getText().toString();
        String price_text=price_tv.getText().toString();
        String address_text=mAddressTv.getText().toString();
        String linkman_text=linkman_edt.getText().toString();
        String phone_text=phone_edt.getText().toString();
        String title_text=title_edt.getText().toString();
        String content_text=content_edt.getText().toString();


        if(CheckUtil.isEmpty(context,"房/厅/卫",fwt_text)){
            return  false;
        }
        if(CheckUtil.isSelect(context, "面积", mianji_text)){
            return  false;
        }
        if(CheckUtil.isEmpty(context, "价格", price_text)){
            return  false;
        }

        if(CheckUtil.isEmpty(context, "详细地址", address_text)){
            return  false;
        }

        if(CheckUtil.isEmpty(context, "标题", title_text)){
            return  false;
        }
        if(CheckUtil.isEmpty(context, "房屋概况", content_text)){
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


        String fwt_text=fwt_tv.getText().toString();
        String mianji_text=mianji_tv.getText().toString();
        String price_text=price_tv.getText().toString();
        String address_text=mAddressTv.getText().toString();
        String linkman_text=linkman_edt.getText().toString();
        String phone_text=phone_edt.getText().toString();
        String title_text=title_edt.getText().toString();
        final String content_text=content_edt.getText().toString();



        if(TextUtils.isEmpty(type_sv.getText())){
            ToastUtil.showToast(context,"请选择类型");
            return;
        }
        String type;

        if("出售".equals(type_sv.getText())){
                type="hire";
        }else{
                type="zhire";
        }

        String aids =uploadView.getAids();
        if(TextUtils.isEmpty(aids)){
            ToastUtil.showToast(context,"请上传房屋图片,才能发布");
            return;
        }

        PublishBiz.publicHouse(type, fwt_text, mianji_text, price_text, title_text, content_text, linkman_text, phone_text, address_text, aids, new BaseBiz.RequestHandle() {
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
        return R.layout.activity_to_rental_house;
    }

    String []rentTypeArray;

    @Override
    protected void init() {
        rentTypeArray=getResources().getStringArray(R.array.select_rent_type);
        type_sv.setShowItemes(rentTypeArray);
    }

    @Override
    protected void initGetData() {


    }

    @Override
    protected void widgetListener() {

    }
}
