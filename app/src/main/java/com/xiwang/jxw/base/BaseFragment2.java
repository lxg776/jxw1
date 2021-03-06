package com.xiwang.jxw.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.xiwang.jxw.util.CommonUtil;

import de.greenrobot.event.EventBus;


/**
 * 基类Fragment
 * 
 * @Description 所有的Fragment必须继承自此类
 * @author CodeApe
 * @version 1.0
 * @date 2014年3月29日
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 */

public abstract class BaseFragment2 extends Fragment {
	/** Standard activity result: operation canceled. */
	protected final int RESULT_CANCELED = 0;
	/** Standard activity result: operation succeeded. */
	protected final int RESULT_OK = -1;
	/** Start of user-defined activity results. */
	protected final int RESULT_FIRST_USER = 1;
	protected Context context;
	/** 父视图 */
	protected View view_Parent;
	/** 广播接收器 */
	protected BroadcastReceiver receiver;
	/** 广播过滤器 */
	protected IntentFilter filter;
	/** 页面名称 */
	protected String pageName;



	long beg;

	protected abstract String getPageName();

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public BaseFragment2(){
			super();
		}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		filter = new IntentFilter();
		if (useEventBus()) {
			EventBus.getDefault().register(this);
		}
		initGetData();
		registerReceiver();
		setPageName(getPageName());
		super.onCreate(savedInstanceState);
	}



	@SuppressWarnings("unchecked")
	public <T extends View> T findViewById(int resId) {
		return (T) view_Parent.findViewById(resId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view_Parent = getViews(inflater,container);
		findViews();
		widgetListener();
		return view_Parent;
	}

	@Override
	public void onAttach(Activity context) {
		super.onAttach(context);
		this.context=context;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		init();

	}

	/**
	 * 获取布局
	 */
	protected abstract View getViews(LayoutInflater inflater, ViewGroup container);

	/**
	 * 查询View
	 */
	protected abstract void findViews();

	/**
	 * 主要用来初始化本地数据
	 */
	public abstract void initGetData();

	/**
	 * 设置监听
	 */
	protected abstract void widgetListener();

	/**
	 * 主要用来获取网络数据
	 */
	protected abstract void init();

	private void setFilterActions() {
		// 添加广播过滤器，在此添加广播过滤器之后，所有的子类都将收到该广播
//		filter.addAction(BroadcastFilters.ACTION_TEST);
//		filter.addAction(BroadcastFilters.ACTION_TYPE_SWITCH);
//		filter.addAction(BroadcastFilters.ACTION_TYPE_SWITCH1);
	}

	private void registerReceiver() {
		setFilterActions();
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				BaseFragment2.this.onReceive(context, intent);
			}
		};
		getActivity().registerReceiver(receiver, filter);
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(receiver);
		if (useEventBus()) {
			EventBus.getDefault().unregister(this);
		}
		super.onDestroy();
	}



	protected boolean useEventBus() {
		return false;
	}


	/**
	 * 广播监听回调
	 * 
	 * @param context
	 * @param intent
	 */
	protected void onReceive(Context context, Intent intent) {
		// 接受到广播之后做的处理操作
//		if (BroadcastFilters.ACTION_TEST.equals(intent.getAction())) {
//		}
	}
	public void onResume() {
		super.onResume();
		beg=System.currentTimeMillis();
		MobclickAgent.onPageStart(getPageName()); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
		MobclickAgent.onResume(context);          //统计时长
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(getPageName()); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
		MobclickAgent.onPause(context);
		int distence= (int) ((System.currentTimeMillis()-beg)/1000);
		CommonUtil.onFragmentPage(context, getPageName(), distence);
	}
}