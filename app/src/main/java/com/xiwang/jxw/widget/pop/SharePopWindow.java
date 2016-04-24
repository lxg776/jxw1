package com.xiwang.jxw.widget.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xiwang.jxw.R;
import com.xiwang.jxw.bean.ShareBean;
import com.xiwang.jxw.intf.OnShareListener;
import com.xiwang.jxw.widget.DividerGridItemDecoration;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;
import java.util.List;


/**
 * 评论弹窗
 * Created by liangxg on 2016/1/26.
 */
public class SharePopWindow extends PopupWindow {
    /**
     * 弹出的view
     */
    View view_parent;
    /**
     * 上下文
     */
    Context context;
    /**
     * 取消按钮
     */
    TextView cancel_btn;
    /**
     * 表情视图
     */
    RecyclerView share_listview;
    List<ShareBean> shareBeanList;
    /**适配器*/
    ItemAdapter adapter;

    public SharePopWindow(Context context) {
        this.context = context;
        view_parent = View.inflate(context, R.layout.pop_share_view, null);
        cancel_btn = (TextView) view_parent.findViewById(R.id.cancel_btn);
        share_listview = (RecyclerView) view_parent.findViewById(R.id.share_listview);
        if(adapter==null){
            initData();
        }

        StaggeredGridLayoutManager mLayoutManager=new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        share_listview.setLayoutManager(mLayoutManager);
        share_listview.addItemDecoration(new DividerGridItemDecoration(context));


        share_listview.setAdapter(adapter);
        this.setContentView(view_parent);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        this.setAnimationStyle(R.style.popwin_anim_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        /**
         * 取消按钮
         */
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });




    }
    /**分享点击*/
    OnShareListener listener;



    /**
     * 初始化数据
     */
    private void  initData(){
        shareBeanList=new ArrayList<>();

        ShareBean wxShare=new ShareBean();
        wxShare.setShowText("微信朋友圈");
        wxShare.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
        shareBeanList.add(wxShare);
        ShareBean wxFriendShare=new ShareBean();
        wxFriendShare.setShowText("微信好友");
        wxFriendShare.setPlatform(SHARE_MEDIA.WEIXIN);
        shareBeanList.add(wxFriendShare);

        ShareBean qZoneShare=new ShareBean();
        qZoneShare.setShowText("QQ空间");
        qZoneShare.setPlatform(SHARE_MEDIA.QZONE);
        shareBeanList.add(qZoneShare);

        ShareBean txwbShare=new ShareBean();
        txwbShare.setShowText("腾讯微博");
        txwbShare.setPlatform(SHARE_MEDIA.TENCENT);
        shareBeanList.add(txwbShare);

        ShareBean sinaShare=new ShareBean();
        sinaShare.setPlatform(SHARE_MEDIA.SINA);
        sinaShare.setShowText("新浪微博");
        shareBeanList.add(sinaShare);

        adapter=new ItemAdapter(shareBeanList);




    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
        List<ShareBean> shareBeanList;

        public ItemAdapter(List<ShareBean> mShareBeanList) {
            this.shareBeanList = mShareBeanList;
        }


        @Override
        public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_share_view, parent, false);



            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemAdapter.ViewHolder holder, final int position) {
            ShareBean shareBean = shareBeanList.get(position);
            if (SHARE_MEDIA.WEIXIN_CIRCLE.equals(shareBean.getPlatform())) {
                /**
                 * 微信朋友圈
                 */

                holder.icon_iv.setImageDrawable(context.getResources().getDrawable(R.mipmap.wxcircle_icon));
            } else if (SHARE_MEDIA.WEIXIN.equals(shareBean.getPlatform())) {
                holder.icon_iv.setImageDrawable(context.getResources().getDrawable(R.mipmap.wxfriend_icon));
            }else if(SHARE_MEDIA.QZONE.equals(shareBean.getPlatform())){
                holder.icon_iv.setImageDrawable(context.getResources().getDrawable(R.mipmap.qzone_icon));
            }else if(SHARE_MEDIA.TENCENT.equals(shareBean.getPlatform())){
                holder.icon_iv.setImageDrawable(context.getResources().getDrawable(R.mipmap.txwb_icon));
            }else if(SHARE_MEDIA.SINA.equals(shareBean.getPlatform())){
                holder.icon_iv.setImageDrawable(context.getResources().getDrawable(R.mipmap.sina_icon));
            }
            holder.name_tv.setText(shareBean.getShowText());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onShare(shareBeanList.get(position));
                    }
                    dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            if (null == shareBeanList) {
                return 0;
            }
            return shareBeanList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView icon_iv;
            TextView name_tv;

            View itemView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView=itemView;
                icon_iv = (ImageView) itemView.findViewById(R.id.icon_iv);
                name_tv = (TextView) itemView.findViewById(R.id.name_tv);
            }

            public void onItemClick(View view, int postion) {

                ShareBean bean = shareBeanList.get(postion);
                if(bean != null){
                    if(listener!=null){
                        listener.onShare(bean);
                    }
                }
                dismiss();
            }
        }
    }

}