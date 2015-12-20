package com.xiwang.jxw.util;

import android.content.Context;
import android.text.TextUtils;

import com.xiwang.jxw.R;
import com.xiwang.jxw.config.TApplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串工具类
 * Created by sunshine on 15/11/8.
 */
public class CheckUtil {
    /**
     * 判断字符串是否为空
     * @param key 校验的字段
     * @param value 校验的值
     * @return
     */
    public static boolean isEmpty(Context context,String key,String value){
       if(TextUtils.isEmpty(value)){
            String hintString=String.format(context.getResources().getString(R.string.input_notnull),key);
             ToastUtil.showToast(context,hintString);
            return  false;
       }
        return  true;

    }



    /**
     * 输入流转换成字符串
     * @param is
     * @return
     */
    public   static   String   inputStream2String(InputStream   is)   throws   IOException{
        ByteArrayOutputStream   baos   =   new ByteArrayOutputStream();
        int   i=-1;
        while((i=is.read())!=-1){
            baos.write(i);
        }
        return   baos.toString();
    }


    /**
     * 验证邮箱
     * @param context 上下文
     * @param email 邮箱
     * @return
     */
    public static boolean checkEmail(Context context,String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        if(!flag){
            ToastUtil.showToast(context,context.getResources().getString(R.string.email_format_error));
        }


        return true;
    }

    /**
     * 验证手机号码
     * @param context 上下文
     * @param mobileNumber 手机号码
     * @return
     */
    public static boolean checkMobileNumber(Context context,String mobileNumber){
        boolean flag = false;
        try{
            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(17([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        if(!flag){
            ToastUtil.showToast(context,context.getResources().getString(R.string.cellphone_format_error));
        }


        return true;
    }
}
