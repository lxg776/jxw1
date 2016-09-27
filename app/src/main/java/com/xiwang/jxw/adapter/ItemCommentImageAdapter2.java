package com.xiwang.jxw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.util.ImgLoadUtil;

import java.util.List;

/**
 * @author liangxg
 * @description 评论列表适配器
 * @date 2015/11/16
 * @modifier
 */
public class ItemCommentImageAdapter2 extends BaseAdapter{

    /** 评论数据*/
    List<String> mImagesList;
    /** 上下文*/
    Context mContext;

    /** 图片的异步显示的选项*/
    DisplayImageOptions options=ImgLoadUtil.getUserOptions();
    /** 加载监听*/
    ImageLoadingListener loadingListener=ImgLoadUtil.defaultLoadingListener();

    public ItemCommentImageAdapter2(Context context, List<String> imagesList){
        this.mContext=context;
        this.mImagesList = imagesList;
    }


    @Override
    public int getCount() {
        if(null!=mImagesList)
            return mImagesList.size();
        return 0;
    }

    @Override
    public String getItem(int position) {
        return mImagesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = View.inflate(mContext,R.layout.item_comment_image,null);
        }
        ImageView  mImages = (ImageView) convertView.findViewById(R.id.comment_image);
        String imageUrl = mImagesList.get(position);
        if(!TextUtils.isEmpty(imageUrl)){
            ImgLoadUtil.getInstance().displayImage(imageUrl, mImages, options, loadingListener);
        }
        return convertView;
    }

}
