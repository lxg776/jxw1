package com.xiwang.jxw.biz;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.BaseBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.StartAppBean;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;

import org.json.JSONException;

/**
 * 系统逻辑
 * Created by sunshine on 15/11/6.
 */
public class SystemBiz {

        public static void getStartAppImage(final BaseBiz.RequestHandle handle){

            BaseBiz.getRequest(ServerConfig.START_APP_IMG, null, new BaseBiz.RequestHandle() {

                @Override
                public void onSuccess(ResponseBean responseBean) {

                    String string= (String) responseBean.getObject();
                    try {
                        responseBean.setObject(BaseBean.newInstance(StartAppBean.class,string));
                        handle.onSuccess(responseBean);
                    }catch (JSONException e){
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
