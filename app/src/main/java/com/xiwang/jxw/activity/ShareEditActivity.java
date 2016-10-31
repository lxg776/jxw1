package com.xiwang.jxw.activity;

import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.ShareBean;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.ToastUtil;

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
        toolbar.setTitle("分享" + shareBean.getShowText());
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
                int length = subject_et.getText().toString().length();
                if (maxSize - length > 0) {
                    num_tv.setVisibility(View.VISIBLE);
                    num_tv.setText((maxSize - length) + "");
                } else {
                    num_tv.setVisibility(View.GONE);
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_share, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            sendShare();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 发送分享
     */
    private void sendShare(){
        UMImage image=null;
        ShareAction shareAction=new ShareAction(this);
        shareAction.setPlatform(shareBean.getPlatform());
        shareAction.setCallback(shareListener);
        shareAction.withText(newsBean.getSubject());
        shareAction.withTitle(newsBean.getSubject());
        shareAction.withTargetUrl(newsBean.getShareUrl());
        if(!TextUtils.isEmpty(newsBean.getImage())){
            image=new UMImage(this, newsBean.getImage());
        }
        if(image!=null){
            shareAction.withMedia(image);
        }
        shareAction.share();
    }

    /**
     * 成功回调
     */
    UMShareListener shareListener=new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtil.showToast(ShareEditActivity.this, "分享成功!");
            finish();


        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.showToast(ShareEditActivity.this,"分享失败!");
            finish();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showToast(ShareEditActivity.this,"分享取消!");
            finish();
        }
    };

}
