package com.xiwang.jxw.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.xiwang.jxw.bean.ColumnBean;

import java.util.List;

/**
 * 首页页面适配器
 * Created by sunshine on 15/11/7.
 */
public class HomePagerViewAdapter extends PagerAdapter {

    //Map<ColumnBean, NewsListView> mapFragement = new HashMap<ColumnBean, NewsListView>();
    List<ColumnBean> columnBeanList;

    Context context;


    public HomePagerViewAdapter(Context context){
        this.context=context;
    }



    @Override
    public int getCount() {
        if(null!=columnBeanList){
            return  columnBeanList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        if (null != columnBeanList) {
            return columnBeanList.get(position).getName();
        }
        return null;
    }
}
