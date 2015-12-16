package com.xiwang.jxw.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.orhanobut.dialogplus.ViewHolder;
import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.PickOrTakeImageActivity;
import com.xiwang.jxw.bean.SingleImageModel;
import com.xiwang.jxw.event.PickImageEvent;
import com.xiwang.jxw.util.DisplayUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/** 上传图片控件
 * @author liangxg
 * @description
 * @date 2015/12/16
 * @modifier
 */
public class UploadImgGridView extends GridView{
    /** 上下文*/
    Context context;
    /** 选择的图片*/
    List<String> imageModelList;

    public  DisplayImageOptions displayOptions;
    String tag="uploadimgGridView";


    @Override
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

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
        imageModelList=new ArrayList<String>();
        SingleImageModel button=new SingleImageModel();
        imageModelList.add(SingleImageModel.TYPE_BUTTON);
        // 创建配置过得DisplayImageOption对象





    }

    /**
     * 添加图片
     */
    private void setImagePath(String path){
        imageModelList.add(0,path);
    }




    protected boolean useEventBus() {
        return true;
    }





    @Override
    protected void onDetachedFromWindow() {
        if(useEventBus()){
            EventBus.getDefault().unregister(this);
        }
        super.onDetachedFromWindow();
    }



}
