package com.xiwang.jxw.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.PickOrTakeImageActivity;
import com.xiwang.jxw.bean.SingleImageModel;
import com.xiwang.jxw.util.DisplayUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.IntentUtil;

import java.util.ArrayList;

/**
     * 适配器
     */
public class UploadImgesAdapter extends BaseAdapter {
       public  ArrayList<String> imageModelList;
        Context context;
        String tag;
        DisplayImageOptions displayOptions;
        public UploadImgesAdapter(Context context,String tag){
            this.imageModelList=new ArrayList<>();
            this.imageModelList.add(SingleImageModel.TYPE_BUTTON);
            this.context=context;
            this.tag=tag;
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 4;   //width，hight设为原来的十分一
            displayOptions = new DisplayImageOptions.Builder() // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
                    .decodingOptions(options)
                    .showStubImage(R.mipmap.default_loading_img)          // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.default_loading_img)  // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.default_loading_img)       // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                    .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                    .build();
        }


        @Override
        public int getCount() {
                if(null!=imageModelList){
                    return  imageModelList.size();
                }
            return 0;
        }

        @Override
        public String getItem(int position) {
            return imageModelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final String bean=getItem(position);

            if(null==convertView){
                convertView=View.inflate(context, R.layout.item_upload_image,null);
            }
            ImageView img_iv= (ImageView) convertView.findViewById(R.id.img_iv);
            if(SingleImageModel.TYPE_BUTTON.equals(bean)){
                //convertView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.upload_img_btn));
                Drawable drawable=context.getResources().getDrawable(R.mipmap.add_icon_gray);
                drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 48), DisplayUtil.dip2px(context, 48));
                img_iv.setImageDrawable(drawable);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SingleImageModel.TYPE_BUTTON.equals(bean)) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(context.getResources().getString(R.string.send_tag), tag);
                            IntentUtil.gotoActivity(context, PickOrTakeImageActivity.class, bundle);
                        }
                    }
                });
            }else{
               // convertView.setBackgroundDrawable(null);
                String path=bean;
                ImgLoadUtil.displayFromSDCard(context,path, img_iv, displayOptions);
            }
            return convertView;
        }
    }