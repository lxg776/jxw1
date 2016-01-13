package com.xiwang.jxw.fragment;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.PublishNewsActivity;
import com.xiwang.jxw.base.BaseFragment;
import com.xiwang.jxw.util.DisplayUtil;
import com.xiwang.jxw.util.IntentUtil;


/**
 * @author liangxg
 * @description 发布招聘，求职，帖子等
 * @date 2015/9/24
 * @modifier
 */
public class PublishFragment extends BaseFragment implements View.OnClickListener{
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
        return "发布招聘，求职，帖子";
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
    }

    @Override
    protected void init() {

    }



    private void setDrawable(LinearLayout ll,Drawable drawable){
        drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 28), DisplayUtil.dip2px(context, 28));
        ImageView imageView= (ImageView) ll.getChildAt(0);
        imageView.setImageDrawable(drawable);
        imageView.setScaleType(ImageView.ScaleType.CENTER);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabu_btn:
                IntentUtil.gotoActivity(context, PublishNewsActivity.class);
                break;
        }
    }
}
