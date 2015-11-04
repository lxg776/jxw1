package com.xiwang.jxw.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.util.HandlerUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.IntentUtil;

/**
 * @author lxg776
 * @description
 * @date 2015/11/2
 * @modifier
 */
public class StartAppActivity extends BaseActivity {
    /** 过渡的图片*/
    ImageView img_view;
    /** 图片路径*/
    String imgUrl;

    /** 图片显示的配置*/
    DisplayImageOptions displayOptions = new DisplayImageOptions.Builder() // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
            .build();

    /** 加载的监听*/
    ImageLoadingListener listener =new SimpleImageLoadingListener(){
        @Override
        public void onLoadingStarted(String imageUri, View view) {
            super.onLoadingStarted(imageUri, view);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            super.onLoadingFailed(imageUri, view, failReason);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            super.onLoadingComplete(imageUri, view, loadedImage);
            img_view.setImageBitmap(loadedImage);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            super.onLoadingCancelled(imageUri, view);
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_start_app;
    }

    @Override
    protected void findViews() {
        img_view= (ImageView) findViewById(R.id.img_view);
    }

    @Override
    protected void init() {
        ImgLoadUtil.displayImage(imgUrl, img_view, displayOptions, listener);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentUtil.gotoActivity(context,MainActivity.class);
            }
        },3000);
    }

    @Override
    protected void initGetData() {
        imgUrl=ServerConfig.START_APP_IMAGE;
    }

    @Override
    protected void widgetListener() {

    }
}
