package com.xiwang.jxw.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.bean.NewsDetailCommentBean;
import com.xiwang.jxw.util.CommonUtil;
import com.xiwang.jxw.util.DisplayUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.widget.NoScrollGridView;
import com.xiwang.jxw.widget.RichTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangxg
 * @description 评论列表适配器
 * @date 2015/11/16
 * @modifier
 */
public class CommentListAdapter extends BaseAdapter{

    /** 评论数据*/
    List<NewsDetailCommentBean> commentBeanList=new ArrayList<NewsDetailCommentBean>();
    /** 上下文*/
    Context context;

    /** 图片的异步显示的选项*/
    DisplayImageOptions options=ImgLoadUtil.getUserOptions();
    /** 加载监听*/
    ImageLoadingListener loadingListener=ImgLoadUtil.defaultLoadingListener();

    public List<NewsDetailCommentBean> getCommentBeanList() {
        return commentBeanList;
    }

    public void setCommentBeanList(List<NewsDetailCommentBean> commentBeanList) {
        this.commentBeanList = commentBeanList;
    }

    public CommentListAdapter(Context context){
        this.context=context;
    }


    @Override
    public int getCount() {
        if(null!=commentBeanList)
            return commentBeanList.size();
        return 0;
    }

    @Override
    public NewsDetailCommentBean getItem(int position) {
        return commentBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        NewsDetailCommentBean bean=getItem(position);

        if(null==convertView){
            convertView=View.inflate(context, R.layout.item_newsdetail_comment,null);
            holder=new ViewHolder();
            holder.author_tv= (TextView) convertView.findViewById(R.id.author_tv);
            holder.publish_tv= (TextView) convertView.findViewById(R.id.publish_tv);
            holder.content_tv= (RichTextView) convertView.findViewById(R.id.content_tv);
            holder.author_headimg_iv= (ImageView) convertView.findViewById(R.id.author_headimg_iv);
            holder.mImages = (NoScrollGridView) convertView.findViewById(R.id.images_rv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.author_tv.setText(bean.getUserinfo().getAuthor());
        holder.publish_tv.setText(bean.getPostdate());
        holder.content_tv.setRichText(bean.getContent());
        ImgLoadUtil.getInstance().displayImage(bean.getUserinfo().getFace(), holder.author_headimg_iv, options, loadingListener);
        //显示评论图片
        if(bean.getImages()!=null&&bean.getImages().size()>0){
            holder.mImages.setVisibility(View.VISIBLE);
            refreshData(bean.getImages(),holder.mImages);
        }else{
            holder.mImages.setVisibility(View.GONE);
        }
        return convertView;
    }



    private final int ONE_LINE_SHOW_NUMBER = 3;

    public void refreshData(List<String> data,RecyclerView item_recyclerview) {

        //每行显示3个，水平显示
        item_recyclerview.setLayoutManager(new GridLayoutManager(context,ONE_LINE_SHOW_NUMBER, LinearLayoutManager.HORIZONTAL,false));

        ViewGroup.LayoutParams layoutParams = item_recyclerview.getLayoutParams();
        //计算行数
        int lineNumber = data.size()%ONE_LINE_SHOW_NUMBER==0? data.size()/ONE_LINE_SHOW_NUMBER:data.size()/ONE_LINE_SHOW_NUMBER +1;
        //计算高度=行数＊每行的高度 ＋(行数－1)＊10dp的margin ＋ 10dp（为了居中）
        //因为每行显示3个条目，为了保持正方形，那么高度应该是也是宽度/3
        //高度的计算需要自己好好理解，否则会产生嵌套recyclerView可以滑动的现象
        int height = 0;
        if(null!=data&&data.size()>0){
            int size =data.size();
            height = ((size-1/ONE_LINE_SHOW_NUMBER)+1)*DisplayUtil.dip2px(context,80);
        }
        layoutParams.height= height;
        layoutParams.width = DisplayUtil.dip2px(context,270);

        System.out.print("qtbj_height"+layoutParams.height);
        System.out.print("qtbj_width"+layoutParams.width);

        item_recyclerview.setLayoutParams(layoutParams);
        item_recyclerview.setAdapter(new ItemCommentImageAdapter(context,data));
    }


    public void refreshData(List<String> data,NoScrollGridView gridView) {
        int height = 0;
        if(null!=data&&data.size()>0){
            int size =data.size();
            height = ((size-1/ONE_LINE_SHOW_NUMBER)+1)*DisplayUtil.dip2px(context,80);
        }

        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
        layoutParams.height =height;
        gridView.setLayoutParams(layoutParams);

        gridView.setAdapter(new ItemCommentImageAdapter2(context,data));
    }


    private class ViewHolder {
        /** 作者 */
        private TextView author_tv;
        /** 发布时间 */
        private TextView  publish_tv;
        /** 评论内容 */
        private RichTextView content_tv;
        /** 头像*/
        private ImageView author_headimg_iv;

        /**评论图片*/
        private NoScrollGridView mImages;
    }
}
