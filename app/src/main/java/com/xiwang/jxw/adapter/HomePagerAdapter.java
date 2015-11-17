package com.xiwang.jxw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.fragment.NewsListFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**首页页面适配器
 * Created by sunshine on 15/11/7.
 */
public class HomePagerAdapter extends FragmentPagerAdapter{

    Map<ColumnBean,NewsListFragment> mapFragement=new HashMap<ColumnBean,NewsListFragment>();
    List<ColumnBean> columnBeanList;
    public void setColumnBeanList(List<ColumnBean> columnBeanList) {
        this.columnBeanList = columnBeanList;
    }

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(mapFragement.containsKey(columnBeanList.get(position))){
            return mapFragement.get(columnBeanList.get(position));
        }else{
            NewsListFragment fragment= NewsListFragment.newInstance(columnBeanList.get(position));
            mapFragement.put(columnBeanList.get(position),fragment);
            return  fragment;
        }
    }

    public List<ColumnBean> getColumnBeanList() {
        return columnBeanList;
    }

    @Override
    public int getCount() {
        return columnBeanList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(null!=columnBeanList){
            return columnBeanList.get(position).getName();
        }
        return  null;

    }


}
