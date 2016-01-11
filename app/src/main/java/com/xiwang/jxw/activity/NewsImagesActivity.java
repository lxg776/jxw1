package com.xiwang.jxw.activity;

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
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.widget.PercentView;
import com.xiwang.jxw.widget.ZoomImageView;
import java.util.List;

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

    int currentPostion;

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
                    break;
                }
            }
            setToolBarTitle(currentPostion, send_urlList.size());
        }
    }

    private void setToolBarTitle(int currentPostion,int size){
        String titleString="("+currentPostion+"/"+size+")";
        toolbar.setTitle(titleString);
    }


    @Override
    protected void initGetData() {
        send_url=getIntent().getStringExtra(IntentConfig.SEND_URL);
        send_title=getIntent().getStringExtra(IntentConfig.SEND_TITLE);
        send_urlList= (List<String>) getIntent().getSerializableExtra(IntentConfig.SEND_URL);

    }

    @Override
    protected void widgetListener() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finlish, menu);
        return true;
    }

    private class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return send_urlList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            currentPostion=position+1;

            View view = LayoutInflater.from(NewsImagesActivity.this).inflate(R.layout.item_news_image, null);
            final ZoomImageView zoomImageView = (ZoomImageView) view.findViewById(R.id.zoom_image_view);
            final PercentView progress_view= (PercentView) view.findViewById(R.id.progress_view);
            String url=send_urlList.get(position);

            /**
             * 异步加载图片s
             */
            ImgLoadUtil.getInstance().displayImage(url, zoomImageView, ImgLoadUtil.defaultDisplayOptions, new SimpleImageLoadingListener(){

                public void onLoadingStarted(String imageUri, ImageView view) {
                    super.onLoadingStarted(imageUri, view);
                    progress_view.setVisibility(View.VISIBLE);
                }


                public void onLoadingFailed(String imageUri, ImageView view, FailReason failReason) {
                    super.onLoadingFailed(imageUri, view, failReason);
                }


                public void onLoadingComplete(String imageUri, ImageView view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    view.setImageBitmap(loadedImage);
                    progress_view.setVisibility(View.GONE);
                }


                public void onLoadingCancelled(String imageUri, ImageView view) {
                    super.onLoadingCancelled(imageUri, view);
                }
            }, new ImageLoadingProgressListener() {
                @Override
                public void onProgressUpdate(String imageUri, View view, int current, int total) {
                    int percent=current*100/total;
                    progress_view.setPercent(percent);
                }
            });
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



}
