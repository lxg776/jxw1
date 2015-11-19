package com.xiwang.jxw.fragment;

import android.view.View;
import android.widget.LinearLayout;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseFragment;


/**
 * @author liangxg
 * @description 发布招聘，求职，帖子等
 * @date 2015/9/24
 * @modifier
 */
public class PublishFragment extends BaseFragment {
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
    }

    @Override
    public void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {

    }
}
