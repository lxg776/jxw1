package com.xiwang.jxw.widget.pop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xiwang.jxw.R;
import com.xiwang.jxw.bean.ShareBean;
import com.xiwang.jxw.widget.PercentView;

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
    public SharePopWindow(Context context) {
        this.context = context;
        view_parent = View.inflate(context, R.layout.pop_share_view, null);
        cancel_btn = (TextView) view_parent.findViewById(R.id.cancel_btn);
        share_listview = (RecyclerView) view_parent.findViewById(R.id.share_listview);
    }

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
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
        List<ShareBean> shareBeanList;


        @Override
        public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.item_share_view,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
            ShareBean shareBean=shareBeanList.get(position);
            if(SHARE_MEDIA.WEIXIN_CIRCLE.equals(shareBean.getPlatform())){
                /**
                 * 微信朋友圈
                 */

                holder.icon_iv.setImageDrawable(context.getResources().getDrawable(R.mipmap.wxcircle_icon));
            }else if(SHARE_MEDIA.WEIXIN.equals(shareBean.getPlatform())){
                holder.icon_iv.setImageDrawable(context.getResources().getDrawable(R.mipmap.wxfriend_icon));
            }
            holder.name_tv.setText(shareBean.getShowText());
        }

        @Override
        public int getItemCount() {
            if(null==shareBeanList) {
                return 0;
            }
            return shareBeanList.size();
        }


        class ViewHolder extends   RecyclerView.ViewHolder {
            ImageView icon_iv;
            TextView name_tv;

            public ViewHolder(View itemView) {
                super(itemView);
                icon_iv= (ImageView) itemView.findViewById(R.id.icon_iv);
                name_tv= (TextView) itemView.findViewById(R.id.name_tv);
            }
        }
    }

}