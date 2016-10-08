package com.xiwang.jxw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.bean.HfPagerBean;

import java.util.ArrayList;

/**
 * 轮播适配器
	 * @author liangxg
	 * 
	 */
public class AdPageAdapter extends PagerAdapter {

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

		ArrayList<HfPagerBean> mList;

		// public int getOriginalCount() {
		// if (null != mViewList) {
		// return mViewList.size();
		// }
		// return 0;
		// }

		Context mContext;

		public AdPageAdapter(Context context, ArrayList<HfPagerBean> list) {

			this.mList = list;
			this.mContext=context;
		}

		@Override
		public int getCount() {
			if (null != mList) {
				return mList.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int index, Object object) {
			// ToastUtil.showToast(context, "销毁" + index);
			container.removeView((View) object);
		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int index) {
			ImageView imageView = (ImageView) View.inflate(mContext,
					R.layout.item_homepage, null);
			HfPagerBean bean = null;
			int y_index = 0;
			if (mList.size() > 0 && index >= mList.size()) {
				y_index = index % mList.size();
			} else {
				y_index = index;
			}
			bean = mList.get(y_index);

			if (null != bean) {
				final String imgUrl = bean.getImage_url();
				ImageLoader.getInstance().displayImage(imgUrl, imageView,
						options, listener);
				final HfPagerBean adBean = bean;
				imageView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {// viewpager点击监听事件
						if (null != adBean
								&& !TextUtils.isEmpty(adBean
										.getImage_hyperlink())) {
							final Bundle bundle = new Bundle();
							bundle.putSerializable("ad_bean", adBean);
//							IntentUtil.gotoActivity(mContext,
//									AdDetailActivity.class, bundle);
						}

					}
				});
			}
			container.addView(imageView);
			return imageView;
		}

	DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.mipmap.home_img_defualt) // 设置图片下载期间显示的图片
			.showImageForEmptyUri(R.mipmap.home_img_defualt) // 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.mipmap.home_img_defualt) // 设置图片加载或解码过程中发生错误显示的图片
			.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
			.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
			.build();
	}