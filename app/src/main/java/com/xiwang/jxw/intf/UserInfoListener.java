package com.xiwang.jxw.intf;

import com.xiwang.jxw.bean.UserBean;

/**
 * Created by liangxg on 2016/4/1.
 */
public interface UserInfoListener {

    public void onCache(UserBean userBean);



    public void onHttpGet(UserBean userBean);


    public void onFail();


}
