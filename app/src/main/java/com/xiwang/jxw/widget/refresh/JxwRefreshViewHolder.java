package com.xiwang.jxw.widget.refresh;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.view.View;

import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshView;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by liangxg on 2016/10/27.
 */
public class JxwRefreshViewHolder extends BGARefreshViewHolder {


    private JxwRefreshView mMeiTuanRefreshView;
    private int mPullDownImageResId = -1;
    private int mChangeToReleaseRefreshAnimResId = -1;
    private int mRefreshingAnimResId = -1;


    /**
     * 设置下拉过程中的图片资源
     *
     * @param resId
     */
    public void setPullDownImageResource(@DrawableRes int resId) {
        mPullDownImageResId = resId;
    }

    /**
     * 设置进入释放刷新状态时的动画资源
     *
     * @param resId
     */
    public void setChangeToReleaseRefreshAnimResId(@DrawableRes int resId) {
        mChangeToReleaseRefreshAnimResId = resId;
    }

    /**
     * 设置正在刷新时的动画资源
     *
     * @param resId
     */
    public void setRefreshingAnimResId(@DrawableRes int resId) {
        mRefreshingAnimResId = resId;
    }

    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    public JxwRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, cn.bingoogolapple.refreshlayout.R.layout.view_refresh_header_meituan, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }

            mMeiTuanRefreshView = (JxwRefreshView) mRefreshHeaderView.findViewById(cn.bingoogolapple.refreshlayout.R.id.meiTuanView);
            if (mPullDownImageResId != -1) {
                mMeiTuanRefreshView.setPullDownImageResource(mPullDownImageResId);
            } else {
                throw new RuntimeException("请调用" + JxwRefreshViewHolder.class.getSimpleName() + "的setPullDownImageResource方法设置下拉过程中的图片资源");
            }
            if (mChangeToReleaseRefreshAnimResId != -1) {
                mMeiTuanRefreshView.setChangeToReleaseRefreshAnimResId(mChangeToReleaseRefreshAnimResId);
            } else {
                throw new RuntimeException("请调用" + JxwRefreshViewHolder.class.getSimpleName() + "的setChangeToReleaseRefreshAnimResId方法设置进入释放刷新状态时的动画资源");
            }
            if (mRefreshingAnimResId != -1) {
                mMeiTuanRefreshView.setRefreshingAnimResId(mRefreshingAnimResId);
            } else {
                throw new RuntimeException("请调用" + JxwRefreshViewHolder.class.getSimpleName() + "的setRefreshingAnimResId方法设置正在刷新时的动画资源");
            }
        }
        return mRefreshHeaderView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
            if (scale <= 1.0f) {
                mMeiTuanRefreshView.handleScale(scale);
            }
    }

    @Override
    public void changeToIdle() {
        mMeiTuanRefreshView.changeToIdle();
    }

    @Override
    public void changeToPullDown() {
        mMeiTuanRefreshView.changeToPullDown();
    }

    @Override
    public void changeToReleaseRefresh() {
        mMeiTuanRefreshView.changeToReleaseRefresh();
    }

    @Override
    public void changeToRefreshing() {
        mMeiTuanRefreshView.changeToRefreshing();
    }

    @Override
    public void onEndRefreshing() {
        mMeiTuanRefreshView.onEndRefreshing();
    }
}
