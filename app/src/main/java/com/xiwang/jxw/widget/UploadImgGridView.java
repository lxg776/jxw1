package com.xiwang.jxw.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/** 上传图片控件
 * @author liangxg
 * @description
 * @date 2015/12/16
 * @modifier
 */
public class UploadImgGridView extends GridView{
    /** 上下文*/
    Context context;

    public UploadImgGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public UploadImgGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context,AttributeSet attributeSet){
        this.context=context;
    }
}
