package com.xiwang.jxw.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 字符串工具类
 * Created by sunshine on 15/11/8.
 */
public class StringUtil {
    /**
     * 判断字符串是否为空
     * @param string
     * @return
     */
    public static boolean isEmpty(String string){
        if(null==string||"".equals(string.trim())||"null".equals(string.trim().toLowerCase())){
            return true;
        }
        return  false;

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
}
