package com.xiwang.jxw.event;

import com.xiwang.jxw.bean.PushNewsBean;

/**
 * 推送消息事件
 * @author liangxg
 * @description
 * @date 2015/11/23
 * @modifier
 */
public class PushMessageEvent {
    /**消息实体*/
    public PushNewsBean newsBean;
    public PushMessageEvent(PushNewsBean newsBean){
            this.newsBean=newsBean;
    }
}
