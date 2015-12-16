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
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.StartAppBean;
import com.xiwang.jxw.biz.HomeBiz;
import com.xiwang.jxw.biz.SystemBiz;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.IntentUtil;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.widget.PercentView;

import java.util.List;

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
    PercentView pv;


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
    protected void initActionBar() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_start_app;
    }

    @Override
    protected void findViews() {
        img_view= (ImageView) findViewById(R.id.img_view);
        pv= (PercentView) findViewById(R.id.pv);



    }

    @Override
    protected void init() {

        SystemBiz.getStartAppImage(new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                StartAppBean bean = (StartAppBean) responseBean.getObject();
                imgUrl = bean.getImgurl();
                ImgLoadUtil.displayImage(imgUrl, img_view, displayOptions, listener);
                SpUtil.setObject(context, getString(R.string.start_app_img), responseBean);
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                //img_view.setBackgroundDrawable(getResources().getDrawable(R.mipmap.start_app_img));
            }

            @Override
            public ResponseBean getRequestCache() {
                return (ResponseBean) SpUtil.getObject(context, getString(R.string.start_app_img));
            }

            @Override
            public void onRequestCache(ResponseBean result) {
                StartAppBean bean = (StartAppBean) result.getObject();
                imgUrl = bean.getImgurl();
                ImgLoadUtil.displayImage(imgUrl, img_view, displayOptions, listener);
            }
        });
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentUtil.gotoActivityAndFinish(context, ApplyWorkActivity.class);
            }
        }, 3000);
        /**
         * 获取栏目
         */
        HomeBiz.getHomeMenu(new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {

                List<ColumnBean> columnBeanList = (List<ColumnBean>) responseBean.getObject();
                SpUtil.setObject(context, getString(R.string.cache_menu), columnBeanList);
            }

            @Override
            public void onFail(ResponseBean responseBean) {

            }

            @Override
            public ResponseBean getRequestCache() {
                return null;
            }

            @Override
            public void onRequestCache(ResponseBean result) {

            }
        });
//
//        final Runnable runnable = new Runnable(){
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                // 在此处添加执行的代码
//                pv.setPercent(pv.getPercent() + 1);
//                if(pv.getPercent()<100){
//                    mHandler.postDelayed(this, 500);// 150是延时时长
//                }else{
//                    mHandler.removeCallbacks(this);
//                }
//
//            }
//        };
//
//
//        pv.setPercentlistener(new PercentView.PercentListener() {
//            @Override
//            public void finish() {
//
//            }
//
//            @Override
//            public void onPercent(int percent) {
//
//            }
//        });
//        mHandler.postDelayed(runnable,500);







    }

    @Override
    protected void initGetData() {
        imgUrl=ServerConfig.START_APP_IMAGE;
    }

    @Override
    protected void widgetListener() {

    }


}
