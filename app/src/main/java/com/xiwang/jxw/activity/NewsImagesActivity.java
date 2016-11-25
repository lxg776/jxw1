package com.xiwang.jxw.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.listener.SaveImageListener;
import com.xiwang.jxw.util.CommonUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.LogUtil;
import com.xiwang.jxw.util.ToastUtil;
import com.xiwang.jxw.widget.PercentView;
import com.xiwang.jxw.widget.ZoomImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunshine on 16/1/11.
 */
public class NewsImagesActivity extends BaseActivity{

    /**当前图片链接*/
    String send_url;
    /**所有图片链接数组*/
    List<String> send_urlList;
    /**当前图片链接*/
    String send_title;

    ViewPager vp_content;
    TextView title_tv;
    /**下标*/
    int currentPostion;

    /**显示的view*/
    List<View> views=new ArrayList<>();
    /**保存下载好的bitmap*/
    Map<String,Bitmap> bitmapMap=new HashMap<String,Bitmap>();

    @Override
    protected String getPageName() {
        return "详情图片列表a";
    }

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         * 保存图片
         */
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_save) {
                    String currentUrl=send_urlList.get(currentPostion-1);
                    saveImage(currentUrl);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news_images;
    }

    @Override
    protected void findViews() {
        vp_content= (ViewPager) findViewById(R.id.vp_content);
        title_tv= (TextView) findViewById(R.id.title_tv);



    }

    @Override
    protected void init() {
        title_tv.setText(send_title);
        if(null!=send_urlList&&send_urlList.size()>0){
            for(int i=0;i<send_urlList.size();i++){
                if(send_urlList.get(i).equals(send_url)){
                    currentPostion=i+1;
                }
                View view = LayoutInflater.from(NewsImagesActivity.this).inflate(R.layout.item_news_image, null);
                initView(view,send_urlList.get(i));
                views.add(view);
            }

        }




        MyViewPagerAdapter pagerAdapter=new MyViewPagerAdapter();
        vp_content.setAdapter(pagerAdapter);
        vp_content.setCurrentItem(currentPostion - 1);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setToolBarTitle(currentPostion, send_urlList.size());
            }
        },1000);
        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPostion = position + 1;
                setToolBarTitle(currentPostion, views.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void setToolBarTitle(int currentPostion,int size){
        String titleString="("+currentPostion+"/"+size+")";
        toolbar.setTitle(titleString);
    }


    @Override
    protected void initGetData() {
        send_url=getIntent().getStringExtra(IntentConfig.SEND_URL);
        send_title=getIntent().getStringExtra(IntentConfig.SEND_TITLE);
        send_urlList= getIntent().getStringArrayListExtra(IntentConfig.SEND_URL_LIST);

    }

    @Override
    protected void widgetListener() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }


    private void initView(View view,String url){
        final ImageView imageView = (ImageView) view.findViewById(R.id.img_iv);
       // final ZoomImageView zoom_img_iv = (ZoomImageView) view.findViewById(R.id.zoom_img_iv);

        final ProgressBar progress_view= (ProgressBar) view.findViewById(R.id.progress_view);

        ImageViewAware imageViewAware=new ImageViewAware(imageView);
        ImgLoadUtil.getInstance().displayImage(url, imageViewAware, ImgLoadUtil.defaultDisplayOptions, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progress_view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progress_view.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                bitmapMap.put(imageUri,loadedImage);
                ((ZoomImageView) imageView).setSourceImageBitmap(loadedImage, NewsImagesActivity.this);
                // zoom_img_iv.setVisibility(View.VISIBLE);
                //  imageView.setVisibility(View.GONE);
                progress_view.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
//                progress_view.setVisibility(View.VISIBLE);
            }
        }, new ImageLoadingProgressListener() {

            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                int proess = current * 100 / total;
                LogUtil.i("progress",proess+"%");
                //progress_view.setPercent(proess);
            }
        });

    }

    private class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=views.get(position);


            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * 保存图片至相册
     */
    private void saveImage(final String url){
      final  Bitmap bitmap=bitmapMap.get(url);
                if(null!=bitmap){
                    new Thread(){
                        @Override
                        public void run() {
                            CommonUtil.saveImageToGallery(NewsImagesActivity.this,bitmap, url, new SaveImageListener() {
                                @Override
                                public void success(final String msg) {
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.showToast(NewsImagesActivity.this, msg);
                                        }
                                    });

                                }

                                @Override
                                public void fail(final String msg) {
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.showToast(NewsImagesActivity.this, msg);
                                        }
                                    });
                                }
                            });
                        }
                    }.start();
                }

    }






    public static void jumpNewsImagesActivity(Context context,String title,String currentImagesUrl,  ArrayList<String> imagesUrlList){
        Intent intent=new Intent(context,NewsImagesActivity.class);
        intent.putExtra(IntentConfig.SEND_URL,currentImagesUrl);
        intent.putStringArrayListExtra(IntentConfig.SEND_URL_LIST, imagesUrlList);
        intent.putExtra(IntentConfig.SEND_TITLE,title);
        context.startActivity(intent);
    }



}
