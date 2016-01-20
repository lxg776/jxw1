package com.xiwang.jxw.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.umeng.analytics.MobclickAgent;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.SmileListBean;
import com.xiwang.jxw.biz.NewsBiz;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.listener.SaveImageListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 友盟统计事件
     * @param context
     * @param eventId
     */
    public static void tjEvent(Context context, String eventId){
        if(TApplication.isUmeng){
            MobclickAgent.onEvent(context, eventId);
        }
    }


    /**
     * 友盟统计事件
     * @param context
     * @param pageName fragment页面名称
     * @param duration 持续事件
     */
    public static void onFragmentPage(Context context, String pageName,int duration){
        if(TApplication.isUmeng){
            Map<String, String> map_value = new HashMap<String, String>();
            map_value.put("page" , pageName );
            MobclickAgent.onEventValue(context, context.getResources().getString(R.string.u_fragment), map_value, duration);

        }
    }

    /**
     * 友盟统计事件
     * @param context
     * @param pageName fragment页面名称
     * @param duration 持续事件
     */
    public static void onActivityPage(Context context, String pageName,int duration){
        if(TApplication.isUmeng){
            Map<String, String> map_value = new HashMap<String, String>();
            map_value.put("page" , pageName );
            MobclickAgent.onEventValue(context, context.getResources().getString(R.string.u_activity), map_value, duration);
        }
    }

    public static void uMengClick(Context context,String eventName){
        Map<String, String> map_value = new HashMap<String, String>();
        map_value.put("click_event" , eventName );
        MobclickAgent.onEvent(context,context.getResources().getString(R.string.u_click_event),map_value);
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
     * 保存图片到系统相册
     * @param bmp
     * @param url
     * @param listener 回调函数
     */
    public static void saveImageToGallery(Context context,Bitmap bmp,String url,SaveImageListener listener) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = MD5.GetMD5Code(url) + ".jpg";
        File file = new File(appDir, fileName);
        if(file.exists()){
            if(listener!=null){
                listener.success("图片已保存.");
            }
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            if(listener!=null){
                listener.success("保存失败.");
            }
            e.printStackTrace();


        } catch (IOException e) {
            if(listener!=null){
                listener.success("保存失败.");
            }
            e.printStackTrace();

        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.toString())));
        if(listener!=null){
            listener.success( "图片保存至"+appDir.getAbsolutePath());
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
