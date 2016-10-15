package com.xiwang.jxw.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.xiwang.jxw.R;
import com.xiwang.jxw.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 循环广告位
 * 
 * @author Administrator
 * 
 */
public class AutoScrollViewPager extends FrameLayout {

	private MyViewPager mPager;
	private LinearLayout mLayout;
	Context context;
	/**
	 * 默认大小
	 */
	protected static final int DEFAULT_LENGTH = 1000*5;

	private int dataLength;
	private List<ImageView> mIndicators = new ArrayList<ImageView>();

	public AutoScrollViewPager(Context context) {
		this(context, null);
		this.context = context;
	}

	public AutoScrollViewPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
	}

	public AutoScrollViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;

		mPager = new MyViewPager(context);
		addView(mPager);
		mLayout = new LinearLayout(getContext());
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		mLayout.setLayoutParams(params);
		addView(mLayout);
	}

	public void setNestedpParent(ViewGroup parent) {
			if(null!=mPager){
				mPager.setNestedpParent(parent);
			}
	}



	/**
	 * 设置指示器
	 */
	private void setIndicator() {
		int size = DisplayUtil.dip2px(context, 6);
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(size, size);
		int px = DisplayUtil.dip2px(context, 6);
		p.setMargins(px, 0, 0, px);
		if (mLayout.getChildCount() > 0) {
			mLayout.removeAllViews();
			mIndicators.clear();
		}

		for (int i = 0; i < dataLength; i++) {
			ImageView iv = new ImageView(getContext());
			iv.setImageResource(R.drawable.defual_circle);
			mIndicators.add(iv);
			mLayout.addView(iv, p);
		}

	}

	public void setAdapter(PagerAdapter arg0) {
		mPager.setAdapter(new JthouPagerAdapter(arg0));
	}

	class JthouPagerAdapter extends PagerAdapter implements
			ViewPager.OnPageChangeListener {

		PagerAdapter adapter;

		public JthouPagerAdapter(PagerAdapter adapter) {
			super();
			this.adapter = adapter;
			mPager.setOnPageChangeListener(this);
		}

		@Override
		public int getCount() {
			if (dataLength != adapter.getCount()) {
				dataLength = adapter.getCount();
				AutoScrollViewPager.this.setIndicator();
			}
			return DEFAULT_LENGTH;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return adapter.isViewFromObject(arg0, arg1);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			if (dataLength > 0) {
				position %= dataLength;
			}

			return adapter.instantiateItem(container, position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			adapter.destroyItem(container, position, object);
		}

		/**
		 * finishUpdate表示ViewPager的更新即将完成
		 */
		@Override
		public void finishUpdate(ViewGroup container) {
			int position = mPager.getCurrentItem();
			if (position == 0) {
				position = dataLength;
				mPager.setCurrentItem(position, false);
			} else if (position == DEFAULT_LENGTH - 1) {
				position = dataLength - 1;
				mPager.setCurrentItem(position, false);
			}
			startAutoScroll(scrollTime);

		};

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			setIndicator(arg0);
		}

		/**
		 * 改变指定位置的图片
		 * 
		 * @param position
		 */
		private void setIndicator(int position) {
			if (dataLength > 0) {
				position %= dataLength;
			}
			for (ImageView indicator : mIndicators) {
				indicator.setImageResource(R.drawable.defual_circle);
			}
			if(mIndicators.size()>0){
				mIndicators.get(position)
						.setImageResource(R.drawable.select_circle);
			}
		}

	}

	/**
	 * 轮播线程
	 */
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			int currentItem = mPager.getCurrentItem();
			mPager.setCurrentItem(++currentItem);
			removeCallbacks(runnable);
			postDelayed(runnable, scrollTime);
		}
	};

	/** 轮播时间 */
	long scrollTime;

	/**
	 * 开始自动轮播
	 */
	public void startAutoScroll(long scrollTime) {
		if (scrollTime > 0) {
			this.scrollTime = scrollTime;
			removeCallbacks(runnable);
			postDelayed(runnable, scrollTime);
		}

	}

	public void stopScroll() {
		removeCallbacks(runnable);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stopScroll();
	}

}