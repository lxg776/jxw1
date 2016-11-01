package com.xiwang.jxw.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.HomePagerAdapter;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.base.BaseFragment;
import com.xiwang.jxw.base.BaseFragment2;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.biz.HomeBiz;
import com.xiwang.jxw.event.MenuEvent;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.widget.MyJazzyViewPager;
import com.xiwang.jxw.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * @author liangxg
 * @description homeFragment,用于显示资讯信息
 * @date 2015/9/24
 * @modifier
 */
public class HomeFragment extends BaseFragment2 {
    /** 栏目控件*/
    PagerSlidingTabStrip tabs;
    /** viewpaper 控件*/
    MyJazzyViewPager pager;
    /** 栏目数组数据*/
    List<ColumnBean> columnBeanList=new ArrayList<ColumnBean>();
    HomePagerAdapter adapter;

    boolean isInit=false;

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
        showColumnList();
        isInit=true;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (null != view_Parent) {
            ViewGroup parent = (ViewGroup) view_Parent.getParent();
            if (null != parent) {
                parent.removeView(view_Parent);
            }
        } else {
            view_Parent = getViews();
            findViews();
            widgetListener();
        }
        return view_Parent;
    }


    @Override
    public void initGetData() {
        initTabsData();
    }

    int currentPostion;

    @Override
    protected void widgetListener() {
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPostion=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void init() {
            pager.setCurrentItem(currentPostion,false);
    }

    /**
     * 初始化栏目数据
     */
    private void initTabsData(){
        adapter=new HomePagerAdapter(getFragmentManager());


    }

    private void showColumnList(){



       final ResponseBean cacheData= (ResponseBean) SpUtil.getObject(context,getString(R.string.cache_menu));


        /**
         * 获取栏目
         */
        HomeBiz.getHomeMenu(new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {

                SpUtil.setObject(context, getString(R.string.cache_menu), responseBean);
                Object o =responseBean.getObject();
                if(null!=o&&o instanceof  List){
                    columnBeanList = (List<ColumnBean>)o;
                    if(null!=columnBeanList&&columnBeanList.size()>0){
                        adapter.setColumnBeanList(columnBeanList);
                        pager.setAdapter(adapter);
                        tabs.setViewPager(pager);
                    }
                }
//                EventBus.getDefault().post(new MenuEvent());
            }

            @Override
            public void onFail(ResponseBean responseBean) {

            }
            @Override
            public ResponseBean getRequestCache() {
                return cacheData;
            }
            @Override
            public void onRequestCache(ResponseBean result) {
                Object o =result.getObject();
//                if(null!=o&&o instanceof  List){
//                    columnBeanList = (List<ColumnBean>)o;
//                    if(null!=columnBeanList&&columnBeanList.size()>0){
//                        adapter.setColumnBeanList(columnBeanList);
//                        pager.setAdapter(adapter);
//                        tabs.setViewPager(pager);
//                    }
                //}
            }
        });






    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    public void onEvent(MenuEvent event) {
        if(isInit){
            showColumnList();
        }
    }


}
