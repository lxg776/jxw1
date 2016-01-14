package com.xiwang.jxw.fragment;

import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.HomePagerAdapter;
import com.xiwang.jxw.base.BaseFragment;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.event.LoginEvent;
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

    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {
        initTabsData();
    }

    /**
     * 初始化栏目数据
     */
    private void initTabsData(){





//        ColumnBean c1=new ColumnBean();
//        c1.setDataUrl("getapp.php?a=thread&fid=2");
//        c1.setName("新鲜事");
//        columnBeanList.add(c1);
//
//
//
//        ColumnBean c2=new ColumnBean();
//        c2.setDataUrl("getapp.php?a=thread&fid=2");
//        c2.setName("新闻");
//        columnBeanList.add(c2);
//
//        ColumnBean c3=new ColumnBean();
//        c3.setDataUrl("getapp.php?a=thread&fid=2");
//        c3.setName("供求");
//        columnBeanList.add(c3);
//
//        ColumnBean c4=new ColumnBean();
//        c4.setDataUrl("getapp.php?a=thread&fid=2");
//        c4.setName("房产");
//        columnBeanList.add(c4);
//
//        ColumnBean c5=new ColumnBean();
//        c5.setDataUrl("getapp.php?a=thread&fid=2");
//        c5.setName("交友");
//        columnBeanList.add(c5);
//
//
//        ColumnBean c6=new ColumnBean();
//        c6.setDataUrl("getapp.php?a=thread&fid=2");
//        c6.setName("求职");
//        columnBeanList.add(c6);
//
//        ColumnBean c7=new ColumnBean();
//        c7.setDataUrl("getapp.php?a=thread&fid=2");
//        c7.setName("景点旅游");
//        columnBeanList.add(c7);
        showColumnList();

    }

    private void showColumnList(){
        columnBeanList= (List<ColumnBean>) SpUtil.getObject(context,getString(R.string.cache_menu));
        if(null!=columnBeanList&&columnBeanList.size()>0){
            adapter=new HomePagerAdapter(getFragmentManager());
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
