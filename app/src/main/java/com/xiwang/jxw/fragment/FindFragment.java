package com.xiwang.jxw.fragment;

import android.view.View;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseFragment;


/**
 * @author liangxg
 * @description 发现fragment
 * @date 2015/9/24
 * @modifier
 */
public class FindFragment extends BaseFragment {


    @Override
    protected String getPageName() {
        return "发现f";
    }

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.fragment_find, null);
    }

    @Override
    protected void findViews() {

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
