package com.xiwang.jxw.activity;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.bean.ShowImg;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.event.DeleteImageEvent;
import com.xiwang.jxw.util.AlbumBitmapCacheHelper;
import com.xiwang.jxw.widget.ZoomImageView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 删除已上传的图片
 * Created by liangxg on 2016/2/24.
 */
public class DeleteImageActivity extends BaseActivity implements View.OnClickListener {


    /**返回*/
    ImageView iv_back;
    /**标题*/
    TextView tv_title;
    /**删除按钮*/
    Button btn_delete;
    /**图片vp*/
    ViewPager vp_content;
    /** 上传的图片*/
    List<ShowImg> imgList;
    /**图片的位置*/
    int mPostion;
    /**适配器*/
    MyViewPagerAdapter pagerAdapter;
    /**来源标识*/
    String fromTag;

    List<View> views=new ArrayList<View>();



    @Override
    protected String getPageName() {
        return "删除图片activity";
    }

    @Override
    protected void initActionBar() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_delete_images;
    }

    @Override
    protected void findViews() {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        tv_title= (TextView) findViewById(R.id.tv_title);
        btn_delete= (Button) findViewById(R.id.btn_delete);
        vp_content= (ViewPager) findViewById(R.id.vp_content);
    }

    @Override
    protected void init() {
        pagerAdapter =new MyViewPagerAdapter();
        vp_content.setAdapter(pagerAdapter);
        vp_content.setCurrentItem(mPostion);
        setTv_title();
    }

    @Override
    protected void initGetData() {
        imgList = getIntent().getParcelableArrayListExtra(IntentConfig.SEND_IMG_LIST);
        mPostion=getIntent().getIntExtra(IntentConfig.SEND_IMG_POSTION, 0);
        fromTag=getIntent().getStringExtra(IntentConfig.SEND_TAG);

        if(null!=imgList&&imgList.size()>0){
            for(int i=0;i<imgList.size();i++){
                View view = LayoutInflater.from(DeleteImageActivity.this).inflate(R.layout.widget_zoom_iamge, null);
                views.add(view);

            }

        }

    }

    private void setView(int index){
            View view=views.get(index);
            final ZoomImageView zoomImageView = (ZoomImageView) view.findViewById(R.id.zoom_image_view);
            AlbumBitmapCacheHelper.getInstance().addPathToShowlist(imgList.get(index).path);
            final  String tag=imgList.get(index).id+"tag";
            zoomImageView.setTag(tag);
            Bitmap bitmap = AlbumBitmapCacheHelper.getInstance().getBitmap(imgList.get(index).path, 0, 0, new AlbumBitmapCacheHelper.ILoadImageCallback() {
                @Override
                public void onLoadImageCallBack(Bitmap bitmap, String path, Object... objects) {
                    ZoomImageView view = ((ZoomImageView)vp_content.findViewWithTag(tag));
                    if (view != null && bitmap != null)
                        view.setSourceImageBitmap(bitmap, DeleteImageActivity.this);
                }
            }, index);
            if (bitmap != null){
                zoomImageView.setSourceImageBitmap(bitmap, DeleteImageActivity.this);
            }
        }


    /**
     * 设置标题显示
     */
    private void setTv_title(){
      if(null!= imgList&&imgList.size()>0){
            tv_title.setText(mPostion + 1 + "/" + imgList.size());
      }else{
          tv_title.setText(mPostion+ "/" + imgList.size());
      }
    }

    @Override
    protected void widgetListener() {
        iv_back.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPostion=position;
              //  setView(position);
//                if(position>0){
//                    setView(position-1);
//                }
//                if(position<pagerAdapter.getCount()-2){
//                    setView(position+1);
//                }


                setTv_title();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //返回按钮
            case R.id.iv_back:
                    finish();
                break;
            //删除按钮
            case  R.id.btn_delete:
                if(null!= imgList &&mPostion<= imgList.size()-1){

                    ShowImg delImg=imgList.remove(mPostion);
                    View delView= views.remove(views.size()-1);

                    pagerAdapter.notifyDataSetChanged();
                    if(views.size()==0){
                        finish();
                    }else{
//                        if(mPostion==0){
//                            vp_content.setCurrentItem(1,false);
//                        }else{
//                            vp_content.setCurrentItem(mPostion-1,false);
//                        }
                        if(mPostion==views.size()){
                            vp_content.setCurrentItem(mPostion-1);
                        }else{
                            vp_content.setCurrentItem(mPostion);
                        }
                        if(null!=imgList&&imgList.size()>0){
                            for(int i=0;i<imgList.size();i++){
                               setView(i);
                            }
                        }
                        String delTag= (String) delView.getTag();
                        if(vp_content.findViewWithTag(delTag)!=null){
                            vp_content.removeView(vp_content.findViewWithTag(delTag));
                        }
                    }




                    setTv_title();


//                       /*
//                    发送event通知控件删除
//                     */
                    DeleteImageEvent event=new DeleteImageEvent();
                    event.fromTag=fromTag;
                    event.imgList=imgList;
                    event.deleteImg=delImg;
                    EventBus.getDefault().post(event);
//
//                    setTv_title();
                }
                break;
        }
    }

    /**
     * 图片适配器
     */
    private class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if(null!= views){
                return views.size();
            }
            return  0;
        }

        public MyViewPagerAdapter(){

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=views.get(position);
            view.setTag(position+"");
            setView(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           // View view = (View) object;
            container.removeView(views.get(position));
            AlbumBitmapCacheHelper.getInstance().removePathFromShowlist(imgList.get(position).path);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
