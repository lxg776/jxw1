package com.xiwang.jxw.biz;

import com.loopj.android.http.RequestParams;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.BaseBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.StartAppBean;
import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;

import org.json.JSONException;

/**
 * @author liangxg
 * @description
 * @date 2015/11/23
 * @modifier
 */
public class UserBiz {
    /**
     * 用户登录方法
     * @param userName
     * @param pwd
     * @param handle
     */
    public static void login(String userName,String pwd,final BaseBiz.RequestHandle handle){
        RequestParams params =new RequestParams();
        params.put("username",userName);
        params.put("password",pwd);

        BaseBiz.postRequest(ServerConfig.USER_LOGIN, params, new BaseBiz.RequestHandle() {

            @Override
            public void onSuccess(ResponseBean responseBean) {

                String string = (String) responseBean.getObject();
                try {
                    responseBean.setObject(BaseBean.newInstance(UserBean.class, string));
                    handle.onSuccess(responseBean);
                } catch (JSONException e) {
                    responseBean.setStatus(ServerConfig.JSON_DATA_ERROR);
                    responseBean.setInfo(TApplication.context.getResources().getString(R.string.exception_local_json_message));
                    handle.onFail(responseBean);
                }
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                handle.onFail(responseBean);
            }

            @Override
            public ResponseBean getRequestCache() {
                return handle.getRequestCache();
            }

            @Override
            public void onRequestCache(ResponseBean result) {
                handle.onRequestCache(result);
            }
        });

    }

}
