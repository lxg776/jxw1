package com.xiwang.jxw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.bean.NewsDetailCommentBean;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.widget.RichTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangxg
 * @description 评论列表适配器
 * @date 2015/11/16
 * @modifier
 */
public class ItemCommentImageAdapter extends RecyclerView.Adapter{

    /** 评论数据*/
    List<String> mImagesList;
    /** 上下文*/
    Context mContext;

    /** 图片的异步显示的选项*/
    DisplayImageOptions options=ImgLoadUtil.getUserOptions();
    /** 加载监听*/
    ImageLoadingListener loadingListener=ImgLoadUtil.defaultLoadingListener();

    public ItemCommentImageAdapter(Context context,List<String> imagesList){
        this.mContext=context;
        this.mImagesList = imagesList;
    }








    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(mContext,R.layout.item_comment_image,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String imageUrl = mImagesList.get(position);
        if(holder instanceof  ViewHolder){
            if(!TextUtils.isEmpty(imageUrl)){
                ViewHolder vh = (ViewHolder) holder;
                ImgLoadUtil.getInstance().displayImage(imageUrl, vh.mImages, options, loadingListener);
            }

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if(null!=mImagesList)
            return mImagesList.size();
        return 0;
    }



    private class ViewHolder extends RecyclerView.ViewHolder {
        /** 图片 */
        public ImageView mImages;
        public ViewHolder(View itemView) {
            super(itemView);
            mImages = (ImageView) itemView.findViewById(R.id.comment_image);
        }
    }
}
