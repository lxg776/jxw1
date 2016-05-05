package com.xiwang.jxw.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.ApplyWorkActivity;
import com.xiwang.jxw.activity.EmployActivity;
import com.xiwang.jxw.activity.HouseActivity;
import com.xiwang.jxw.activity.PublishNewsActivity;
import com.xiwang.jxw.activity.RentalActivity;
import com.xiwang.jxw.activity.TestActivity;
import com.xiwang.jxw.activity.ToRentalHouseActivity;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.base.BaseFragment;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.ThreadTypeBean;
import com.xiwang.jxw.biz.ThreadTypeBiz;
import com.xiwang.jxw.biz.UserBiz;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.util.CommonUtil;
import com.xiwang.jxw.util.DisplayUtil;
import com.xiwang.jxw.util.IntentUtil;


/**
 * @author liangxg
 * @description 发布招聘，求职，帖子等
 * @date 2015/9/24
 * @modifier
 */
public class PublishFragment extends BaseFragment implements View.OnClickListener{

    //发帖
    public static int DO_FABU=0x23;
    //求租
    public static int DO_QIUZU=0x24;
    //求职
    public static int DO_QIUZHI=0x25;
    //房产
    public static int DO_HOUSE=0x26;
    //招聘
    public static int DO_EMPLOYT=0x27;


    /** 发帖按钮*/
    LinearLayout fabu_btn;
    /** 二手交易*/
    LinearLayout ershou_btn;
    /** 招聘信息*/
    LinearLayout zhaopin_btn;
    /** 发简历求职*/
    LinearLayout qiuzhi_btn;
    /** 房租出租*/
    LinearLayout chuzu_btn;
    /** 求租信息*/
    LinearLayout qiuzu_btn;
    /** 二手房源*/
    LinearLayout ershoufang_btn;
    /** 地皮/商铺信息*/
    LinearLayout dipi_btn;




    @Override
    protected String getPageName() {
        return "发布招聘，求职，帖子f";
    }

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.fragment_publish, null);
    }

    @Override
    protected void findViews() {
        fabu_btn=findViewById(R.id.fabu_btn);
        ershou_btn=findViewById(R.id.ershou_btn);
        zhaopin_btn=findViewById(R.id.zhaopin_btn);
        qiuzhi_btn=findViewById(R.id.qiuzhi_btn);
        chuzu_btn=findViewById(R.id.chuzu_btn);
        qiuzu_btn=findViewById(R.id.qiuzu_btn);
        ershoufang_btn=findViewById(R.id.ershoufang_btn);
        dipi_btn=findViewById(R.id.dipi_btn);

        setDrawable(fabu_btn,getResources().getDrawable(R.mipmap.f_fabu));
        setDrawable(ershou_btn, getResources().getDrawable(R.mipmap.f_goods));
        setDrawable(zhaopin_btn,getResources().getDrawable(R.mipmap.f_zhaopin));
        setDrawable(qiuzhi_btn, getResources().getDrawable(R.mipmap.f_qiuzhi));
        setDrawable(chuzu_btn,getResources().getDrawable(R.mipmap.f_rent_home));
        setDrawable(qiuzu_btn,getResources().getDrawable(R.mipmap.f_qiuzu));
        setDrawable(ershoufang_btn,getResources().getDrawable(R.mipmap.f_home));
        setDrawable(dipi_btn,getResources().getDrawable(R.mipmap.f_dipi));


    }





    @Override
    public void initGetData() {

    }

    @Override
    protected void widgetListener() {
        fabu_btn.setOnClickListener(this);
        qiuzu_btn.setOnClickListener(this);
        qiuzhi_btn.setOnClickListener(this);
        zhaopin_btn.setOnClickListener(this);
        chuzu_btn.setOnClickListener(this);
    }

    @Override
    protected void init() {
        /**加载主题分类*/
        CommonUtil.getThreadTypeList(context);
    }



    private void setDrawable(LinearLayout ll,Drawable drawable){
        drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 28), DisplayUtil.dip2px(context, 28));
        ImageView imageView= (ImageView) ll.getChildAt(0);
        imageView.setImageDrawable(drawable);
        imageView.setScaleType(ImageView.ScaleType.CENTER);


    }


    private Bundle getBundle(int send_do){
        Bundle bundle=new Bundle();
        bundle.putString(IntentConfig.SEND_FRAMGE_TAG, getTag());
        bundle.putInt(IntentConfig.SEND_DO,send_do);
        return  bundle;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabu_btn:
                if(UserBiz.isLogin(context,getBundle(DO_FABU))){
                    IntentUtil.gotoActivity(context, PublishNewsActivity.class);
                }
                break;
            case R.id.qiuzu_btn:
                if(UserBiz.isLogin(context,getBundle(DO_QIUZU))){
                    IntentUtil.gotoActivity(context, RentalActivity.class);
                }

                break;
            case R.id.qiuzhi_btn:
                /*
                发求职简历
                 */
                if(UserBiz.isLogin(context,getBundle(DO_QIUZHI))){
                    IntentUtil.gotoActivity(context, ApplyWorkActivity.class);
                }
                break;
            case R.id.chuzu_btn:
                  /*
                发求房产信息
                 */
                if(UserBiz.isLogin(context,getBundle(DO_HOUSE))){
                    IntentUtil.gotoActivity(context, ToRentalHouseActivity.class);
                }
                break;
            case R.id.zhaopin_btn:
                /*
                招聘信息
                 */
                if(UserBiz.isLogin(context,getBundle(DO_EMPLOYT))){
                    IntentUtil.gotoActivity(context, EmployActivity.class);
                }
                break;


        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            /**
             * 登录成功后
             */
            if(requestCode==IntentConfig.LOGIN_CODE&&null!=data){
                    int do_=data.getIntExtra(IntentConfig.SEND_DO,0);
                    if(do_==DO_FABU){
                    IntentUtil.gotoActivity(context, PublishNewsActivity.class);
                    }else if(do_==DO_QIUZU){
                        IntentUtil.gotoActivity(context, RentalActivity.class);
                    }else if(do_==DO_HOUSE){
                        IntentUtil.gotoActivity(context, HouseActivity.class);
                    }else if(do_==DO_QIUZHI){
                        IntentUtil.gotoActivity(context, ApplyWorkActivity.class);
                    }else if(do_==DO_EMPLOYT){
                        IntentUtil.gotoActivity(context, EmployActivity.class);
                    }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
