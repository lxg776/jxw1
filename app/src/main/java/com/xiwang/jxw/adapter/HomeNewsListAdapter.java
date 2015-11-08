package com.xiwang.jxw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.StringUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页新闻列表适配器
 * Created by sunshine on 15/11/8.
 */
public class HomeNewsListAdapter extends BaseAdapter{
    /** 列表数据*/
    List<NewsBean> newsBeanList;
    /** 上下文*/
    Context context;


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

    public List<NewsBean> getNewsBeanList() {
        return newsBeanList;
    }

    public void setNewsBeanList(List<NewsBean> newsBeanList) {
        this.newsBeanList = newsBeanList;
    }

    public HomeNewsListAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        if(null!=newsBeanList)
        {
            return newsBeanList.size();
        }

        return 0;
    }

    @Override
    public NewsBean getItem(int position) {
        return newsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder hoder=null;
        NewsBean newsBean=getItem(position);
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_home_newslist,null);
            hoder=new ViewHoder();
            hoder.subject_tv= (TextView) convertView.findViewById(R.id.subject_tv);
            hoder.images_ll= (LinearLayout) convertView.findViewById(R.id.images_ll);
            hoder.tag_tv= (TextView) convertView.findViewById(R.id.tag_tv);
            hoder.author_tv= (TextView) convertView.findViewById(R.id.author_tv);
            hoder.replies_tv= (TextView) convertView.findViewById(R.id.replies_tv);
            hoder.hits_tv= (TextView) convertView.findViewById(R.id.hits_tv);
            hoder.image_iv= (ImageView) convertView.findViewById(R.id.image_iv);
            convertView.setTag(hoder);
        }else{
            hoder= (ViewHoder) convertView.getTag();
        }
        hoder.subject_tv.setText(newsBean.getSubject());
        hoder.author_tv.setText(newsBean.getAuthor());
        hoder.hits_tv.setText(newsBean.getHits());
        hoder.replies_tv.setText(newsBean.getReplies());
        if(!StringUtil.isEmpty(newsBean.getImage())){
            hoder.image_iv.setVisibility(View.VISIBLE);
            ImgLoadUtil.displayImage(newsBean.getImage(), hoder.image_iv, ImgLoadUtil.defaultDisplayOptions, listener);
        }else{
            hoder.image_iv.setVisibility(View.GONE);
        }



        return convertView;
    }




    class ViewHoder{
        /** 标题*/
        TextView subject_tv;
        /** 超过三张的新闻图片*/
        LinearLayout images_ll;
        /** 标签（热点，推荐）*/
        TextView tag_tv;
        /** 作者*/
        TextView author_tv;
        /** 回复数*/
        TextView replies_tv;
        /** 浏览数*/
        TextView hits_tv;
        /** 新闻图片*/
        ImageView image_iv;

    }


}
