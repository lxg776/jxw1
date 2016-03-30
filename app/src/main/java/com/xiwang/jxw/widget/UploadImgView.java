package com.xiwang.jxw.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.DeleteImageActivity;
import com.xiwang.jxw.activity.PickOrTakeImageActivity;
import com.xiwang.jxw.bean.BaseBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.ShowImg;
import com.xiwang.jxw.bean.UploadImgBean;
import com.xiwang.jxw.biz.SystemBiz;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.event.DeleteImageEvent;
import com.xiwang.jxw.event.PickImageEvent;
import com.xiwang.jxw.util.AlbumBitmapCacheHelper;
import com.xiwang.jxw.util.DisplayUtil;
import com.xiwang.jxw.util.IntentUtil;
import org.json.JSONException;
import java.io.FileNotFoundException;
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
    ArrayList<ShowImg> imageModelList;
    /** 成功上传的图片*/
    List<UploadImgBean> uploadImageUrlList;

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
    /** 选择图片监听*/
    PickImageListener pickImageListener;

    /**显示上传的图片*/
    RecyclerView recyclerView;

    ItemAdapter adapter;



    public List<UploadImgBean> getUploadImageUrlList() {
        return uploadImageUrlList;
    }



    public PickImageListener getPickImageListener() {
        return pickImageListener;
    }

    public void setPickImageListener(PickImageListener pickImageListener) {
        this.pickImageListener = pickImageListener;
    }

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
        imageModelList=new ArrayList<ShowImg>();
        uploadImageUrlList=new ArrayList<UploadImgBean>();
        adapter=new ItemAdapter();

        if(useEventBus()){
            EventBus.getDefault().register(this);
        }

        View contentView = View.inflate(context, R.layout.view_upload_img,null);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UploadImgView);
        horizontalSpacing=a.getDimensionPixelSize(R.styleable.UploadImgView_horizontalSpacing, DisplayUtil.dip2px(context, 8));
        verticalSpacing=a.getDimensionPixelSize(R.styleable.UploadImgView_verticalSpacing, DisplayUtil.dip2px(context, 8));
        numColumns=a.getInteger(R.styleable.UploadImgView_numColumns,5);
        a.recycle();
        recyclerView= (RecyclerView) contentView.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager mLayoutManager=new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(context));


//        /**
//         * 计算每个控件的w 和 h
//         */
//        int with=  DisplayUtil.getScreenWidth(context)-DisplayUtil.dip2px(context,16*2);
//        child_with=(with-(numColumns-1)*horizontalSpacing)/numColumns;
//        child_height=child_with;
//
//        //添加add按钮
        imageModelList.add(new ShowImg());
        recyclerView.setAdapter(adapter);

//        View addBtn=View.inflate(context,R.layout.item_upload_image,null);
//        addBtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.add_icon_gray));
//        addBtn.findViewById(R.id.progress_view).setVisibility(GONE);
////        addBtn.setBackgroundResource(R.drawable.upload_img_btn);
////        ImageView img_iv= (ImageView) addBtn.findViewById(R.id.img_iv);
////        Drawable drawable=context.getResources().getDrawable(R.mipmap.add_icon_gray);
////        drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 48), DisplayUtil.dip2px(context, 48));
////        img_iv.setImageDrawable(drawable);
//        addBtn.setId(beginId++);
//        addBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /**
//                 * 跳转选择多张图片activity
//                 */
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(context.getResources().getString(R.string.send_tag), tag);
//                IntentUtil.gotoActivity(context, PickOrTakeImageActivity.class, bundle);
//            }
//        });

        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(contentView,params);
    }

    /**
     * 计算布局位置
     */
//    private void calculLayout(){
//        if(getChildCount()>0){
//            /**
//             * 遍历子控件定位其位置
//             */
//            for(int i=0;i<getChildCount();i++){
//                View view=getChildAt(i);
//                RelativeLayout.LayoutParams params= (LayoutParams) view.getLayoutParams();
//
//                int left_view_id = 0;
//                int above_view_id = 0;
//                /** 左边view*/
//                if(i%numColumns!=0){
//                    if(i>0){
//                        left_view_id=getChildAt(i-1).getId();
//                    }
//                }
//                /** 上边view*/
//                if(i>=numColumns){
//                    above_view_id=getChildAt(i-numColumns).getId();
//                }
//                if(left_view_id!=0){
//                    params.addRule(RelativeLayout.RIGHT_OF,left_view_id);
//                    params.leftMargin=horizontalSpacing;
//                }
//                if(above_view_id!=0){
//                    params.addRule(RelativeLayout.BELOW,above_view_id);
//                    params.topMargin=verticalSpacing;
//                }
//            }
//        }
//    }


    /**
     * 添加图片
     */
    private void setImagePath(String path){
        ShowImg showImg=new ShowImg();
        View view=View.inflate(context,R.layout.item_upload_image,null);
        final int vId=beginId++;
        showImg.path=path;
        showImg.id=vId;
        imageModelList.add(0, showImg);
        adapter.notifyItemRangeChanged(0,imageModelList.size());

//        view.setId(vId);
//        ImageView img_iv= (ImageView) view.findViewById(R.id.img_iv);
//
//        /**
//         * 点击事件
//         */
//        img_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DeleteImageActivity.class);
//                intent.putParcelableArrayListExtra(IntentConfig.SEND_IMG_LIST, imageModelList);
//                int img_postion = 0;
//                int size = getChildCount();
//                for (int i = 0; i < size; i++) {
//                    if (getChildAt(i).getId() == vId) {
//                        img_postion = i;
//                    }
//                }
//                intent.putExtra(IntentConfig.SEND_IMG_POSTION, img_postion);
//                intent.putExtra(IntentConfig.SEND_TAG, tag);
//                context.startActivity(intent);
//            }
//        });
//
//
//        displayFromSDCard(context, img_iv, path);
//        addView(view, 0, getRelationLayout());
        //uploadImage(path, view);
    }



    private void uploadImage(final String path,View view){
        final PercentView progress_view= (PercentView) view.findViewById(R.id.progress_view);
        final ImageView img_iv= (ImageView) view.findViewById(R.id.img_iv);
        try {
            SystemBiz.uploadImg(path, new SystemBiz.UploadImgListener() {
                @Override
                public void onSuccess(ResponseBean responseBean) {
                    progress_view.setVisibility(View.GONE);
                    try {
                        UploadImgBean uploadImgBean= (UploadImgBean) BaseBean.newInstance(UploadImgBean.class,(String)responseBean.getObject());
                        //显示的图片包含上传成功的图片才进行上传
                        if(imageModelList.contains(path)){
                            uploadImageUrlList.add(uploadImgBean);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFail(ResponseBean responseBean) {
                    img_iv.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                    progress_view.setVisibility(GONE);
                }
                @Override
                public void onProgress(int progress) {
                    progress_view.setPercent(progress);
                }

                @Override
                public void onFinish() {
                    progress_view.setVisibility(GONE);
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            img_iv.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        }
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
                if(null!=pickImageListener){
                    pickImageListener.onImageSelect(event.picklist);
                }
                for(String path:event.picklist){
                    setImagePath(path);
                }
                //calculLayout();
            }
        }
    }

    public void onEvent(DeleteImageEvent event){
        if(tag.equals(event.fromTag)){
            if(event.imgList!=null){
                imageModelList.clear();
                imageModelList.addAll(event.imgList);
                adapter.notifyItemRangeChanged(0,imageModelList.size());
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
                imageView.setBackgroundDrawable(bd);
            }
        });
        if (bitmap != null){
            BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
            imageView.setBackgroundDrawable(bd);
        }else{
            imageView.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.default_loading_img));
        }
    }


    public interface PickImageListener{
            public void onImageSelect(List<String> picklist);
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            if(i==getItemCount()-1){
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_upload_image,
                        viewGroup, false);
                return new ViewHolder(view,ViewHolder.TYPE_BTN);
            }else{
                 view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_upload_image,
                        viewGroup, false);
                return new ViewHolder(view,ViewHolder.TYPE_IMG);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int i) {
            ShowImg showImg=imageModelList.get(i);
            if(i==getItemCount()-1){
                viewHolder.img_iv.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(context.getResources().getString(R.string.send_tag), tag);
                            IntentUtil.gotoActivity(context, PickOrTakeImageActivity.class, bundle);
                    }
                });
                viewHolder.img_iv.setBackgroundDrawable(getResources().getDrawable(R.mipmap.add_icon_gray));
                viewHolder.progress_view.setVisibility(View.GONE);
            }else{
                displayFromSDCard(context, viewHolder.img_iv, showImg.path);
                viewHolder.img_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DeleteImageActivity.class);
                        ArrayList<ShowImg> sendimagelList=new ArrayList<ShowImg>();
                        for(int index=0;index<imageModelList.size();index++){
                            if(!TextUtils.isEmpty(imageModelList.get(index).path)){
                                sendimagelList.add(imageModelList.get(index));
                            }
                        }
                        int send_img_postion=0;
                        if(sendimagelList.size()!=imageModelList.size()&&i>1){
                            send_img_postion=i-1;
                        }
                        intent.putParcelableArrayListExtra(IntentConfig.SEND_IMG_LIST, sendimagelList);
                        intent.putExtra(IntentConfig.SEND_IMG_POSTION, send_img_postion);
                        intent.putExtra(IntentConfig.SEND_TAG, tag);
                        context.startActivity(intent);
                    }
                });
                viewHolder.progress_view.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            if(imageModelList!=null){
                return  imageModelList.size();
            }
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView img_iv;
            PercentView progress_view;

            public static final int TYPE_IMG=2;
            public static final int TYPE_BTN=1;

            public ViewHolder(View itemView,int type) {
                  super(itemView);
                    img_iv= (ImageView) itemView.findViewById(R.id.img_iv);
                    progress_view= (PercentView) itemView.findViewById(R.id.progress_view);
            }
        }
    }


}
