package com.xiwang.jxw.fragment;

import com.xiwang.jxw.util.MD5;

/**
 * Created by liangxg on 2016/11/3.
 */
public class SignatureUtil {


    public  static final String vector="jxw";


    /**
     * 获取签名
     * @param signKey
     * @param timeTemp
     * @param random
     * @return
     */
    public static String getSignature(String signKey,String timeTemp,String random){
        StringBuffer sb =new StringBuffer();
        sb.append(signKey+timeTemp+random+vector);
         return  MD5.GetMD5Code(sb.toString());
    }

}
