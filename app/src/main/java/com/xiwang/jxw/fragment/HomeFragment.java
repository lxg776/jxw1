package com.xiwang.jxw.fragment;

import android.view.View;

import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.HomePagerAdapter;
import com.xiwang.jxw.base.BaseFragment;
import com.xiwang.jxw.base.BaseFragment2;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.event.MenuEvent;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.widget.MyJazzyViewPager;
import com.xiwang.jxw.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;


/**
 * @author liangxg
 * @description homeFragment,用于显示资讯信息
 * @date 2015/9/24
 * @modifier
 */
public class HomeFragment extends BaseFragment {
    /** 栏目控件*/
    PagerSlidingTabStrip tabs;
    /** viewpaper 控件*/
    MyJazzyViewPager pager;
    /** 栏目数组数据*/
    List<ColumnBean> columnBeanList=new ArrayList<ColumnBean>();
    HomePagerAdapter adapter;

    @Override
    protected String getPageName() {
        return "首页新闻f";
    }

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.fragment_home, null);
    }

    @Override
    protected void findViews() {
        tabs=findViewById(R.id.tabs);
        tabs.setSelectedTextColor(getResources().getColor(R.color.orange_500));
        tabs.setIndicatorColorResource(R.color.orange_500);
        tabs.setTextColorResource(R.color.gray_500);

        pager=findViewById(R.id.pager);


    }

    @Override
    public void initGetData() {
        initTabsData();
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {
        showColumnList();
    }

    /**
     * 初始化栏目数据
     */
    private void initTabsData(){
        adapter=new HomePagerAdapter(getFragmentManager());


    }

    private void showColumnList(){
        columnBeanList= (List<ColumnBean>) SpUtil.getObject(context,getString(R.string.cache_menu));
        if(null!=columnBeanList&&columnBeanList.size()>0){
            adapter.setColumnBeanList(columnBeanList);

            pager.setAdapter(adapter);
            tabs.setViewPager(pager);
        }

    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    public void onEvent(MenuEvent event) {
        showColumnList();
    }


}
