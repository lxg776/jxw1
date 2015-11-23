package com.xiwang.jxw.event;

/**
 * @author liangxg
 * @description
 * @date 2015/11/23
 * @modifier
 */
public class LoginEvent {
    /** 是否登录 */
    public boolean islogin;
    public LoginEvent(Boolean islogin) {
        this.islogin=islogin;
    }
}
