package com.xiwang.jxw.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;


/**
 * 基类Activity
 * 
 * @Description 所有的Activity都继承自此Activity，并实现基类模板方法，本类的目的是为了规范团队开发项目时候的开发流程的命名，
 *              基类也用来处理需要集中分发处理的事件、广播、动画等，如开发过程中有发现任何改进方案都可以一起沟通改进。
 * @author CodeApe
 * @version 1.0
 * @date 2015年10月10日
 * 
 */
public abstract class BaseActivity extends AppCompatActivity {

	/** 广播接收器 */
	public BroadcastReceiver receiver;
	/** 广播过滤器 */
	public IntentFilter filter;

	/** 上下文 */
	public Context context;

	/** handler */
	public Handler mHandler;

	/**标题栏 */
	protected  Toolbar toolbar;

	/** 页面名称 */
	protected String pageName;

	protected abstract String getPageName();

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		context=this;
		View view = View.inflate(context, getContentViewId(), null);
		mHandler=new Handler();
		setContentView(view);
		startAnimation();
		if (useEventBus()) {
			EventBus.getDefault().register(this);
		}
		findViews();
		initGetData();
		initActionBar();
		widgetListener();
		init();
		registerReceiver();
		setPageName(getPageName());
	}

	protected abstract void initActionBar();


	public void onResume() {
		super.onResume();
		/**友盟统计*/
		MobclickAgent.onResume(this);
	}
	public void onPause() {
		super.onPause();
		/**友盟统计*/
		MobclickAgent.onPause(this);
	}

	/**
	 * 界面开始动画 (此处输入方法执行任务.)
	 * 
	 * @version 1.0
	 * @createTime 2015年5月9日,下午2:36:15
	 * @updateTime 2015年5月9日,下午2:36:15
	 * @createAuthor Xiaohuan
	 * @updateAuthor Xiaohuan
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	protected void startAnimation() {

	}

	/**
	 * 界面回退动画 (此处输入方法执行任务.)
	 * 
	 * @version 1.0
	 * @createTime 2015年5月9日,下午2:36:33
	 * @updateTime 2015年5月9日,下午2:36:33
	 * @createAuthor Xiaohuan
	 * @updateAuthor Xiaohuan
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	protected void endAnimation() {

	}

	@Override
	public void onBackPressed() {// 监听回退键
		super.onBackPressed();
		finish();
	}

	@Override
	public void finish() {// 设置回退动画
		super.finish();
		endAnimation();
	}

	protected boolean useEventBus() {
		return false;
	}

	/**
	 * 获取显示view的xml文件ID
	 * 
	 * 在Activity的 {@link #onCreate(Bundle)}里边被调用
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午2:31:19
	 * @updateTime 2014年4月21日,下午2:31:19
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return xml文件ID
	 */
	protected abstract int getContentViewId();


	/**
	 * 控件查找
	 * 
	 * 在 {@link #getContentViewId()} 之后被调用
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午1:58:20
	 * @updateTime 2014年4月21日,下午1:58:20
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void findViews();

	/**
	 * 初始化应用程序，设置一些初始化数据都获取数据等操作
	 * 
	 * 在{@link #widgetListener()}之后被调用
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午1:55:15
	 * @updateTime 2014年4月21日,下午1:55:15
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void init();

	/**
	 * 获取上一个界面传送过来的数据
	 * 
	 * 在{@link BaseActivity#init()}之前被调用
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午2:42:56
	 * @updateTime 2014年4月21日,下午2:42:56
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void initGetData();

	/**
	 * 组件监听模块
	 * 
	 * 在{@link #findViews()}后被调用
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午1:56:06
	 * @updateTime 2014年4月21日,下午1:56:06
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void widgetListener();

	/**
	 * 设置广播过滤器
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,下午1:43:15
	 * @updateTime 2014年5月22日,下午1:43:15
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected void setFilterActions() {
		filter = new IntentFilter();
		filter.addAction("");

	}

	/**
	 * 注册广播
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,下午1:41:25
	 * @updateTime 2014年5月22日,下午1:41:25
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected void registerReceiver() {
		setFilterActions();
		receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				BaseActivity.this.onReceive(context, intent);
			}
		};
		registerReceiver(receiver, filter);

	}

	/**
	 * 骞挎挱鐩戝惉鍥炶皟
	 * 
	 * @version 1.0
	 * @createTime 2014骞�5鏈�22鏃�,涓嬪崍1:40:30
	 * @updateTime 2014骞�5鏈�22鏃�,涓嬪崍1:40:30
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (姝ゅ杈撳叆淇敼鍐呭,鑻ユ棤淇敼鍙笉鍐�.)
	 * 
	 * @param context
	 *            涓婁笅鏂�
	 * @param intent
	 *            骞挎挱闄勫甫鍐呭
	 */
	protected void onReceive(Context context, Intent intent) {

		// TODO 鐖剁被闆嗕腑澶勭悊鍏卞悓鐨勮姹�

	}



	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		if (useEventBus()) {
			EventBus.getDefault().unregister(this);
		}
		super.onDestroy();
	}

	

}
