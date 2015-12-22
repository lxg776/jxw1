package com.xiwang.jxw.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.orhanobut.dialogplus.ViewHolder;
import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.PickOrTakeImageActivity;
import com.xiwang.jxw.bean.SingleImageModel;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.event.PickImageEvent;
import com.xiwang.jxw.util.AlbumBitmapCacheHelper;
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
public class UploadImgView extends RelativeLayout{
    /** 上下文*/
    Context context;
    /** 选择的图片*/
    List<String> imageModelList;
    /** 标识*/
    String tag="uploadimgGridView";
    int beginId=1001;
    /** 水平间距*/
    int horizontalSpacing;
    /** 垂直间距*/
    int verticalSpacing;

    /** 子控件的宽带*/
    int child_with;
    /** 子控件的高度*/
    int child_height;
    /**每行有多少个控件 */
    int numColumns;




    @Override
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public UploadImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public UploadImgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init(final Context context,AttributeSet attrs){
        this.context=context;
        imageModelList=new ArrayList<String>();
        SingleImageModel button=new SingleImageModel();
        imageModelList.add(SingleImageModel.TYPE_BUTTON);
        if(useEventBus()){
            EventBus.getDefault().register(this);
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UploadImgView);
        horizontalSpacing=a.getDimensionPixelSize(R.styleable.UploadImgView_horizontalSpacing, DisplayUtil.dip2px(context, 8));
        verticalSpacing=a.getDimensionPixelSize(R.styleable.UploadImgView_verticalSpacing, DisplayUtil.dip2px(context, 8));
        numColumns=a.getInteger(R.styleable.UploadImgView_numColumns,5);
        a.recycle();


        /**
         * 计算每个控件的w 和 h
         */
        int with=  DisplayUtil.getScreenWidth(context)-DisplayUtil.dip2px(context,16*2);
        child_with=(with-(numColumns-1)*horizontalSpacing)/numColumns;
        child_height=child_with;

        //添加add按钮
        View addBtn=View.inflate(context,R.layout.item_upload_image,null);
        addBtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.add_icon_gray));
//        addBtn.setBackgroundResource(R.drawable.upload_img_btn);
//        ImageView img_iv= (ImageView) addBtn.findViewById(R.id.img_iv);
//        Drawable drawable=context.getResources().getDrawable(R.mipmap.add_icon_gray);
//        drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 48), DisplayUtil.dip2px(context, 48));
//        img_iv.setImageDrawable(drawable);
        addBtn.setId(beginId++);
        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 跳转选择多张图片activity
                 */
                Bundle bundle = new Bundle();
                bundle.putSerializable(context.getResources().getString(R.string.send_tag), tag);
                IntentUtil.gotoActivity(context, PickOrTakeImageActivity.class, bundle);
            }
        });
        addView(addBtn,getRelationLayout());
    }

    /**
     * 计算布局位置
     */
    private void calculLayout(){
        if(getChildCount()>0){
            /**
             * 遍历子控件定位其位置
             */
            for(int i=0;i<getChildCount();i++){
                View view=getChildAt(i);
                RelativeLayout.LayoutParams params= (LayoutParams) view.getLayoutParams();

                int left_view_id = 0;
                int above_view_id = 0;
                /** 左边view*/
                if(i%numColumns!=0){
                    if(i>0){
                        left_view_id=getChildAt(i-1).getId();
                    }
                }
                /** 上边view*/
                if(i>=numColumns){
                    above_view_id=getChildAt(i-numColumns).getId();
                }
                if(left_view_id!=0){
                    params.addRule(RelativeLayout.RIGHT_OF,left_view_id);
                    params.leftMargin=horizontalSpacing;
                }
                if(above_view_id!=0){
                    params.addRule(RelativeLayout.BELOW,above_view_id);
                    params.topMargin=verticalSpacing;
                }
            }
        }
    }


    /**
     * 添加图片
     */
    private void setImagePath(String path){
        imageModelList.add(0, path);
        View view=View.inflate(context,R.layout.item_upload_image,null);
        view.setId(beginId++);
        ImageView img_iv= (ImageView) view.findViewById(R.id.img_iv);
        displayFromSDCard(context, img_iv, path);
        addView(view,0,getRelationLayout());

    }


    private RelativeLayout.LayoutParams getRelationLayout(){
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(child_with,child_height);
        return params;
    }



    protected boolean useEventBus() {
        return true;
    }



    public void onEvent(PickImageEvent event){
        if(tag.equals(event.fromTag)){
            if(null!=event.picklist&&event.picklist.size()>0){
                for(String path:event.picklist){
                    setImagePath(path);

                }
                calculLayout();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if(useEventBus()){
            EventBus.getDefault().unregister(this);
        }
        AlbumBitmapCacheHelper.getInstance().clearCache();
        super.onDetachedFromWindow();
    }


    /**
     * 从内存卡中异步加载本地图片
     *
     * @param uri
     * @param imageView
     */
    private  void displayFromSDCard(final Context context,final ImageView imageView,String uri) {

        AlbumBitmapCacheHelper.getInstance().addPathToShowlist(uri);
        Bitmap bitmap = AlbumBitmapCacheHelper.getInstance().getBitmap(uri, 255, 255, new AlbumBitmapCacheHelper.ILoadImageCallback() {
            @Override
            public void onLoadImageCallBack(Bitmap bitmap, String path1, Object... objects) {
                if (bitmap == null) {
                    return;
                }
                BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
                imageView.setImageDrawable(bd);
            }
        });
        if (bitmap != null){
            BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
            imageView.setBackgroundDrawable(bd);
        }else{
            imageView.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.default_loading_img));
        }

    }
}
