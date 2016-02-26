package com.xiwang.jxw.bean.postbean;

import android.text.TextUtils;

import com.xiwang.jxw.util.CommonUtil;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 提交帖子实体
 * Created by sunshine on 16/1/1.
 */
public class TopicBean {
    /** 板块id，不填时默认发到论坛八卦*/
    String fid;
    /** 分类（可不填）*/
    String type;
    /** 发帖为new,后面还有回复、修改等*/
    String action;
    /** （在回复、修改帖子等要传递过来）*/
    String tid;
    /** 帖子标题（现在的手机版为默认截取内容前30字）*/
    String subject;
    /** 帖子内容*/
    String content;
    /** 上传的图片id，为以逗号为分隔的字符串，如2,3,4,5,6……*/
    String aids;


    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }


    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        System.out.println("unicodeBytes is: " + unicodeBytes);
        return unicodeBytes;
    }




    public String getSubject() {

//        if(!TextUtils.isEmpty(subject)) {
//            return CommonUtil.decodeUnicode(subject);
//        }
        return  subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
//        if(!TextUtils.isEmpty(content)){
//            return CommonUtil.decodeUnicode(content);
//        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAids() {
        return aids;
    }

    public void setAids(String aids) {
        this.aids = aids;
    }
}
