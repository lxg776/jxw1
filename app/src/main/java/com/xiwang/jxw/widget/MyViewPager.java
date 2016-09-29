package com.xiwang.jxw.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * @author chenyang
 * 自定义viewPager  可以将它放在Listview中解决滑动冲突
 *
 */
public class MyViewPager extends ViewPager {
	private ViewGroup parent;
//	// 滑动距离及坐标
//	private float xDistance, yDistance, xLast, yLast;
//
//	float curX = 0f;
//	float downX = 0f;


	Context context;


	public MyViewPager(Context context) {
		super(context);

		// TODO Auto-generated constructor stub
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setNestedpParent(ViewGroup parent) {
		this.parent = parent;
	}



	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(parent!=null){
			getParent().requestDisallowInterceptTouchEvent(true);
		}
		return super.dispatchTouchEvent(ev);
	}




}
