package com.xiwang.jxw.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.xiwang.jxw.R;

import java.io.File;

/**
 * 图片异步操作操作类
 * @author liangxg
 * @description
 * @date 2015/11/2
 * @modifier
 */
public class ImgLoadUtil {

    private static ImageLoader instance;

    /**
     * 获取异步加载图片配置
     * @return
     */
    public static ImageLoaderConfiguration getImageConfig(Context context){
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache");
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(720, 1280)
                .discCacheExtraOptions(720, 1280, null)
                .threadPoolSize(4)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .memoryCache(new LruMemoryCache(4 * 1024 * 1024))
                .memoryCacheSize(4 * 1024 * 1024)
                .memoryCacheSizePercentage(13)
                .discCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheSize(50 * 1024 * 1024)
                .discCacheFileCount(1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs()
                .build();
        return configuration;
    }

    /**
     * 异步加载图片
     * @param Uri 地址
     * @param imageView 显示的控件
     */
    public static void displayImage(String Uri,ImageView imageView){
        getInstance().displayImage(Uri,imageView,defaultDisplayOptions);
    }
    public static void displayImage(String Uri,ImageView imageView,DisplayImageOptions options,ImageLoadingListener listener){
        getInstance().displayImage(Uri,imageView,options,listener);
    }
    /**
     * 返回一个异步加载instance
     * @return
     */
    public static ImageLoader getInstance(){
        if(instance==null){
            instance= ImageLoader.getInstance();
        }
        return   instance;
    }


    public static DisplayImageOptions defaultDisplayOptions = new DisplayImageOptions.Builder() // 使用DisplayImageOptions.Builder()创建DisplayImageOptions

                    .showStubImage(R.mipmap.default_loading_img)          // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.default_loading_img)  // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.default_loading_img)       // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                    .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                    .build();                                   // 创建配置过得DisplayImageOption对象

    /**
     * 获取 用户的图片 DisplayImageOptions
     * @return
     */
    public static DisplayImageOptions getUserOptions(){
        DisplayImageOptions newsDetailDisplayOptions = new DisplayImageOptions.Builder() // 使用DisplayImageOptions.Builder()创建DisplayImageOptions

                .showStubImage(R.mipmap.defualt_user_icon)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.defualt_user_icon)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.defualt_user_icon)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                .build();                                   // 创建配置过得DisplayImageOption对象
        return newsDetailDisplayOptions;
    }

    /**
     * 获取默认的 LoadingListener
     * @return
     */
    public static ImageLoadingListener defaultLoadingListener(){
      ImageLoadingListener defaultListener =new SimpleImageLoadingListener(){

            public void onLoadingStarted(String imageUri, ImageView view) {
                super.onLoadingStarted(imageUri, view);
            }


            public void onLoadingFailed(String imageUri, ImageView view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
            }


            public void onLoadingComplete(String imageUri, ImageView view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                view.setImageBitmap(loadedImage);
            }


            public void onLoadingCancelled(String imageUri, ImageView view) {
                super.onLoadingCancelled(imageUri, view);
            }
        };
        return  defaultListener;
    }





}
