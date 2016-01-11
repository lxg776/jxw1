package com.xiwang.jxw.util;

import android.content.Context;
import android.os.Environment;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.SmileListBean;
import com.xiwang.jxw.biz.NewsBiz;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author liangxg
 * @description
 * @date 2015/12/16
 * @modifier
 */
public class CommonUtil {


    public static String getAboutAbsoluteImgUrl(String imgUrl){
        return ServerConfig.SERVER_API_URL +imgUrl;
    }

    /**
     * 设置表情列表
     * @param context
     */
    public static void setSmileList(final Context context){
        NewsBiz.getSmileBean(new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                List<SmileListBean> list= (List<SmileListBean>) responseBean.getObject();
                if(list!=null&&list.size()>0){
                    TApplication.smilesList=list;
                    SpUtil.setObject(context,context.getResources().getString(R.string.cache_smiles),list);
                }
            }
            @Override
            public void onFail(ResponseBean responseBean) {

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


    /**
     * 获取表情列表
     * @return
     */
    public static List<SmileListBean> getSmileList(final Context context){
        if(TApplication.smilesList!=null&&TApplication.smilesList.size()>0){
            return TApplication.smilesList;
        }else{
           List<SmileListBean> smileListBeans= (List<SmileListBean>) SpUtil.getObject(context,context.getResources().getString(R.string.cache_smiles));
            TApplication.smilesList=smileListBeans;
            if(TApplication.smilesList!=null&&TApplication.smilesList.size()>0){
                return TApplication.smilesList;
            }else{
                NewsBiz.getSmileBean(new BaseBiz.RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        TApplication.smilesList= (List<SmileListBean>) responseBean.getObject();
                        if(TApplication.smilesList!=null&&TApplication.smilesList.size()>0){
                            SpUtil.setObject(context,context.getResources().getString(R.string.cache_smiles),TApplication.smilesList);
                        }
                    }
                    @Override
                    public void onFail(ResponseBean responseBean) {

                    }
                    @Override
                    public ResponseBean getRequestCache() {
                        return null;
                    }
                    @Override
                    public void onRequestCache(ResponseBean result) {

                    }
                });
                return TApplication.smilesList;
            }

        }
    }

    /**
     * md5加密
     */
    public static String md5(Object object) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(toByteArray(object));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static byte[] toByteArray (Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }



    /**
     * 获取存储路径
     */
    public static String getDataPath() {
        String path;
        if (isExistSDcard())
            path = Environment.getExternalStorageDirectory().getPath() + "/albumSelect";
        else
            path = TApplication.context.getFilesDir().getPath();
        if (!path.endsWith("/"))
            path = path + "/";
        return path;
    }


    /**
     * 检测SDcard是否存在
     *
     * @return
     */
    public static boolean isExistSDcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED))
            return true;
        else {
            return false;
        }
    }
}
