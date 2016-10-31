package com.xiwang.jxw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.NewsDetailActivity;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.HfPagerBean;
import com.xiwang.jxw.bean.HomeLunBoBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.widget.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 首页新闻列表适配器
 * Created by sunshine on 15/11/8.
 */
public class HomeNewsListAdapter2 extends RecyclerView.Adapter{

    /** 列表数据*/
    List<Object> newsBeanList;
    /** 上下文*/
    Context context;
    /** item点击事件*/
    OnitemClicklistener onitemClicklistener;




    public final int TYPE_HEAD=0;
    public final int TYPE_NORMAL=1;
    /**栏目id*/
    ColumnBean mColumnBean;

    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.default_loading_img01) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.drawable.default_loading_img01) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.default_loading_img01) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build();




    public List<Object> getNewsBeanList() {
        return newsBeanList;
    }

    public void setNewsBeanList(List<Object> newsBeanList) {
        this.newsBeanList = newsBeanList;
    }

    public OnitemClicklistener getOnitemClicklistener() {
        return onitemClicklistener;
    }

    public void setOnitemClicklistener(OnitemClicklistener onitemClicklistener) {
        this.onitemClicklistener = onitemClicklistener;
    }

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



    public HomeNewsListAdapter2(Context context,ColumnBean columnBean){
        this.context=context;
        this.mColumnBean=columnBean;
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if(viewType==TYPE_HEAD){
           View ad_view = View.inflate(context,R.layout.view_lunbo_ad,null);
           return new LunBoAdHolder(ad_view);
       }else{
           View view = View.inflate(context,R.layout.item_home_newslist,null);
           return new ItemViewHolder(view);
       }
    }


    @Override
    public int getItemViewType(int position) {
            Object o = newsBeanList.get(position);
            if(o instanceof HomeLunBoBean){
                return  TYPE_HEAD;
            }else {
               return TYPE_NORMAL;
            }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,final int position) {
        Object  o =  newsBeanList.get(position);
        if(o instanceof NewsBean) {
            NewsBean newsBean = (NewsBean) o;
            ItemViewHolder holder = (ItemViewHolder) viewHolder;
            holder.subject_tv.setText(newsBean.getSubject());
            holder.author_tv.setText(newsBean.getAuthor());
            holder.hits_tv.setText(newsBean.getHits());
            holder.replies_tv.setText(newsBean.getReplies());
            if(!TextUtils.isEmpty(newsBean.getImage())){
                holder.image_iv.setVisibility(View.VISIBLE);
                ImgLoadUtil.displayImage(newsBean.getImage(), holder.image_iv, ImgLoadUtil.defaultDisplayOptions, listener);
            }else{
                holder.image_iv.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if(null!=onitemClicklistener){
                        onitemClicklistener.onitemClick(v,position);
                    }
                }
            });
        }else if(o instanceof HomeLunBoBean){
            HomeLunBoBean homeLunBoBean = (HomeLunBoBean) o;
            LunBoAdHolder holder = (LunBoAdHolder) viewHolder;
            //轮播图
            setViewPagerSrc(homeLunBoBean,holder.viewPager);
        }
    }


    /**
     * 网络请求后的结果 填充 viewPager
     * @param lunBoBean
     */
    public void setViewPagerSrc(HomeLunBoBean lunBoBean, BGABanner viewPager) {
        ArrayList<HfPagerBean> list = lunBoBean.getDataList();
        // 将图片装载到数组中
        if (list != null && list.size() > 0) {
            ArrayList<String> stringArrayList =new ArrayList<>();
            for(HfPagerBean bean:list){
                stringArrayList.add(bean.getTitle());
            }
            viewPager.setData(R.layout.item_homepage,list,stringArrayList);
            viewPager.setVisibility(View.VISIBLE);
            viewPager.setPageChangeDuration(200);
            viewPager.setAdapter(new BGABanner.Adapter() {
                @Override
                public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                    ImageView imageView = (ImageView) view;
                    final HfPagerBean bean = (HfPagerBean) model;
                    if (null != bean) {
                        final String imgUrl = bean.getImage();
                        ImageLoader.getInstance().displayImage(imgUrl, imageView,
                                options, listener);
                        final HfPagerBean adBean = bean;

                        imageView.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {// viewpager点击监听事件
                                //跳转详情
                                NewsBean newsBean =new NewsBean();
                                newsBean.setTid(bean.getTid());
                                newsBean.setSubject(bean.getTitle());
                                newsBean.setShareUrl(bean.getUrl());
                                NewsDetailActivity.jumpNewsDetailActivity(context,newsBean,mColumnBean);
                            }
                        });
                    }
                }
            });
        } else {
            viewPager.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(null!=newsBeanList)
        {
            return newsBeanList.size();
        }
        return 0;
    }


    class LunBoAdHolder extends RecyclerView.ViewHolder{
        BGABanner viewPager;
        public LunBoAdHolder(View itemView) {
            super(itemView);
            viewPager = (BGABanner) itemView.findViewById(R.id.viewPager);
        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder{
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

        public ItemViewHolder(View itemView) {
            super(itemView);
            subject_tv= (TextView) itemView.findViewById(R.id.subject_tv);
           images_ll= (LinearLayout) itemView.findViewById(R.id.images_ll);
           tag_tv= (TextView) itemView.findViewById(R.id.tag_tv);
            author_tv= (TextView) itemView.findViewById(R.id.author_tv);
           replies_tv= (TextView) itemView.findViewById(R.id.replies_tv);
           hits_tv= (TextView) itemView.findViewById(R.id.hits_tv);
          image_iv= (ImageView) itemView.findViewById(R.id.image_iv);

        }
    }


}
