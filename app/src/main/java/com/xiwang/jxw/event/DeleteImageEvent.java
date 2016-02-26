package com.xiwang.jxw.event;

import com.xiwang.jxw.bean.ShowImg;
import com.xiwang.jxw.widget.UploadImgView;

import java.util.List;

/**
 * Created by liangxg on 2016/2/24.
 */
public class DeleteImageEvent{
    /**图片路径*/
    public List<ShowImg> imgList;
    /**来源标识*/
    public  String fromTag;
    /**图片路径*/
    public ShowImg deleteImg;

}
