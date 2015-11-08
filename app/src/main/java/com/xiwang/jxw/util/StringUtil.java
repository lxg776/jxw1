package com.xiwang.jxw.util;

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
}
