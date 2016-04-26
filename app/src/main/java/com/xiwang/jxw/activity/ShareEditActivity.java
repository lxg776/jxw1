package com.xiwang.jxw.activity;

import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.ShareBean;
import com.xiwang.jxw.util.ImgLoadUtil;

/**
 * 编辑分享
 * Created by sunshine on 15/11/19.
 */
public class ShareEditActivity extends BaseActivity{

    /**字数限制*/
    TextView num_tv;
    /**新闻图*/
    ImageView image_iv;
    /**分享数据*/
    NewsBean newsBean;
    /**标题*/
    EditText subject_et;

    /**限制输入*/
    int maxSize=100;

    ShareBean shareBean;

    /** 加载图片的监听*/
    ImageLoadingListener listener =new SimpleImageLoadingListener(){

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

    @Override
    protected String getPageName() {
        return "分享编辑页面";
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
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_share_edit;
    }

    @Override
    protected void findViews() {
        num_tv= (TextView) findViewById(R.id.num_tv);
        image_iv= (ImageView) findViewById(R.id.image_iv);
        subject_et= (EditText) findViewById(R.id.subject_et);
    }

    @Override
    protected void init() {
        toolbar.setTitle("分享"+shareBean.getShowText());
    }

    @Override
    protected void initGetData() {
        newsBean= (NewsBean) getIntent().getSerializableExtra(getString(R.string.send_data));
        shareBean=(ShareBean) getIntent().getSerializableExtra("sharebean");
        if(null!=shareBean){
            subject_et.setText("【" + newsBean.getSubject() + "】");
            if(!TextUtils.isEmpty(newsBean.getImage())){
                ImgLoadUtil.displayImage(newsBean.getImage(), image_iv, ImgLoadUtil.defaultDisplayOptions, listener);
            }
        }
    }

    @Override
    protected void widgetListener() {
        subject_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    int length=subject_et.getText().toString().length();
                    if(maxSize-length>0){
                        num_tv.setVisibility(View.VISIBLE);
                        num_tv.setText((maxSize-length)+"");
                    }else{
                        num_tv.setVisibility(View.GONE);
                    }
            }
        });


    }


}
