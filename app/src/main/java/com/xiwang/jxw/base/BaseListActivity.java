package com.xiwang.jxw.base;

/**
 * Created by sunshine on 15/12/20.
 */
abstract class BaseListActivity extends BaseActivity{

    /**
     * 根据页面加载数据
     * @param page
     * @param limit
     */
    public abstract void loadDataByPage(int page,int limit);
}
