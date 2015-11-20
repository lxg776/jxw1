package com.xiwang.jxw.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseFragment;
import com.xiwang.jxw.util.DisplayUtil;
import com.xiwang.jxw.util.ToastUtil;


/**
 * @author liangxg
 * @description 我的
 * 我的发布 我的收藏 提到我的
 * @date 2015/9/24
 * @modifier
 */
public class MineFragment extends BaseFragment implements View.OnClickListener{
    /** 个人资料*/
    private LinearLayout ziliao_ll;
    /** 我的收藏*/
    private LinearLayout collect_ll;
    /** 提到我的*/
    private LinearLayout tidao_ll;
    /** 我的发布*/
    private LinearLayout fabu_ll;
    /** 系统设置*/
    private LinearLayout setting_ll;


    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.fragment_mine, null);
    }

    @Override
    protected void findViews() {
        ziliao_ll= (LinearLayout) view_Parent.findViewById(R.id.ziliao_ll);
        fabu_ll= (LinearLayout) view_Parent.findViewById(R.id.fabu_ll);
        setting_ll= (LinearLayout) view_Parent.findViewById(R.id.setting_ll);
        collect_ll = (LinearLayout) view_Parent.findViewById(R.id.collect_ll);
        tidao_ll = (LinearLayout) view_Parent.findViewById(R.id.tidao_ll);


    }

    @Override
    public void initGetData() {

    }

    @Override
    protected void widgetListener() {
        ziliao_ll.setOnClickListener(this);
        fabu_ll.setOnClickListener(this);
        setting_ll.setOnClickListener(this);
        collect_ll.setOnClickListener(this);
        tidao_ll.setOnClickListener(this);

    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.ziliao_ll:
                    break;
                case R.id.fabu_ll:
                    break;
                case R.id.setting_ll:
                    break;
                case R.id.collect_ll:
                    ToastUtil.showToast(context,"lxg776");
                    break;
                case R.id.tidao_ll:
                    break;
            }
    }
}
