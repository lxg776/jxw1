package com.xiwang.jxw.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiwang.jxw.R;
import com.xiwang.jxw.bean.NewsDetailCommentBean;

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
        NewsDetailCommentBean bean;
        if(null==convertView){
            convertView=View.inflate(context, R.layout.item_newsdetail_comment,null);
            holder=new ViewHolder();
            holder.author_tv= (TextView) convertView.findViewById(R.id.author_tv);
            holder.publish_tv= (TextView) convertView.findViewById(R.id.publish_tv);
            holder.content_tv= (TextView) convertView.findViewById(R.id.content_tv);
            holder.author_headimg_iv= (ImageView) convertView.findViewById(R.id.author_headimg_iv);
        }
        bean=getItem(position);
        holder= (ViewHolder) convertView.getTag();
        holder.author_tv.setText(bean.getAuthor());
        holder.publish_tv.setText(bean.getPostdate());
        holder.content_tv.setText(bean.getContent());
        return convertView;
    }

    private class ViewHolder {
        /** 作者 */
        private TextView author_tv;
        /** 发布时间 */
        private TextView  publish_tv;
        /** 评论内容 */
        private TextView  content_tv;
        /** 头像*/
        private ImageView author_headimg_iv;
    }
}