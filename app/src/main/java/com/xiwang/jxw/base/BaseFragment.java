package com.xiwang.jxw.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public abstract class BaseFragment extends Fragment {
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


	@Override
	public void onCreate(Bundle savedInstanceState) {
		filter = new IntentFilter();
		context = getActivity();
		view_Parent = getViews();

		findViews();
		if (useEventBus()) {
			EventBus.getDefault().register(this);
		}
		initGetData();
		widgetListener();
		init();
		registerReceiver();
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T findViewById(int resId) {
		return (T) view_Parent.findViewById(resId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if ((ViewGroup) view_Parent.getParent() != null) {
			((ViewGroup) view_Parent.getParent()).removeView(view_Parent);
		}
		return view_Parent;
	}

	/**
	 * 获取布局
	 */
	protected abstract View getViews();

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
				BaseFragment.this.onReceive(context, intent);
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
}