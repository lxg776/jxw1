package com.xiwang.jxw.biz;

import com.loopj.android.http.RequestParams;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.BaseBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.SmileListBean;
import com.xiwang.jxw.bean.ThreadTypeBean;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题分类业务逻辑
 * Created by liangxg on 2016/1/22.
 */
public class ThreadTypeBiz {

    /**
     * 获取表情表情
     * @return
     */
    public static void getThreadTypes(final BaseBiz.RequestHandle handle){

        BaseBiz.getRequest(ServerConfig.THREAD_TYPE_URL, new RequestParams(), new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                String string= (String) responseBean.getObject();
                try {
                    JSONObject jsonObject=new JSONObject(string);
                    ArrayList<ThreadTypeBean> list= (ArrayList<ThreadTypeBean>) BaseBean.toModelList(jsonObject.optString("type"), ThreadTypeBean.class);
                    responseBean.setObject(list);
                }catch (JSONException e){
                    responseBean.setStatus(ServerConfig.JSON_DATA_ERROR);
                    responseBean.setInfo(TApplication.context.getResources().getString(R.string.exception_local_json_message));
                    handle.onFail(responseBean);
                }
                handle.onSuccess(responseBean);
            }
            @Override
            public void onFail(ResponseBean responseBean) {
                handle.onFail(responseBean);
            }

            @Override
            public ResponseBean getRequestCache() {
                return null;
            }

            @Override
            public void onRequestCache(ResponseBean result) {

            }
        });
    }
}
