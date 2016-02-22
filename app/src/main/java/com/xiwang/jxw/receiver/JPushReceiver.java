package com.xiwang.jxw.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.xiwang.jxw.activity.MainActivity;
import com.xiwang.jxw.bean.MessageBean;
import com.xiwang.jxw.config.IntentConfig;
import cn.jpush.android.api.JPushInterface;



/**
 * 从极光服务器接收消息，再广播给app应用的MessageReceiver。
 *         自定义接收器
 *         如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 * @Description TODO
 * @author XiaoHuan
 * @date 2015-9-2
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 */
public class JPushReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	private NotificationManager nm;
	private MessageBean bean;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (null == nm) {
			nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		}

		// 推送事件
		//CommonUtil.UMAnalytics(context, Config.JpusjMessage);

		Bundle bundle = intent.getExtras();
		// bundle.getString(JPushInterface.EXTRA_MESSAGE)

		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
			// send the Registration Id to your server...

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			// String title = bundle.getString(JPushInterface.EXTRA_TITLE);

				/** 反馈App收到消息 */
				JSONObject object2;
				try {
					object2 = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
				
				final long messageId = object2.getLong("messageId");
				boolean isUnShowNotice = object2.getBoolean("isUnShowNotice");
			
				if (isUnShowNotice) {
					notification(bundle, context, true);
				} else {

				}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
			notification(bundle, context, false);
			// RPC.message().setReceived(msgCode.getMessageId());
			// String title =
			// bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
			// if (title.equals("店铺加盟审批")) {
			// Intent mIntent = new Intent(BroadcastFilters.ACTION_CAERFUL);
			// // 店铺加盟审批发送广播
			// context.sendBroadcast(mIntent);
			// //EventBus.getDefault().post(new
			// ShopFreeEvent(Long.parseLong(split[1])));
			// }
			// Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
			// sendMessageReceived(context,bundle);
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			openMessageActivity(context, bundle);

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
			// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
			// 打开一个网页等..

		} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
			boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
		} else {
			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
		}

	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	/**
	 * 接收通知
	 * 
	 * @version 1.0
	 * @createTime 2015年6月16日,下午4:55:10
	 * @updateTime 2015年6月16日,下午4:55:10
	 * @createAuthor Xiaohuan
	 * @updateAuthor Xiaohuan
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param bundle
	 * @param context
	 */
	private void notification(Bundle bundle, Context context, boolean isCustom) {
			/** 反馈App收到消息 */
			JSONObject object2;
			try {
				object2 = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
			
			final long messageId = object2.getLong("messageId");
			long user_id = object2.getLong("userId");
			
			/** 获得当前登录的用户ID */
		//	dbutil = new DatabaseUtil(context);
			String title;
			String message;
			if (bundle != null) {
				if (isCustom) {
					title = bundle.getString(JPushInterface.EXTRA_MESSAGE);
					message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
				} else {
					title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
					message = bundle.getString(JPushInterface.EXTRA_ALERT);
				}
				bean = new MessageBean();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
				Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
				String str = formatter.format(curDate);
				bean.setSend_time(str);
				bean.setTitle(title);
				bean.setIs_read(1);
				bean.setUser_id(user_id);
				bean.setMessage_content(message);
				//boolean insert = dbutil.Insert(bean);
				/** 插入Id */
//				System.out.println(insert);
				Intent mIntent = new Intent(IntentConfig.ACTION_MESSAGE);
				context.sendBroadcast(mIntent);
//				RequestExecutor.addTask(new MyTask() {
//					@Override
//					public Object sendRequest() {
//						try {
//							RPC.message().setReceived(messageId);
//							return 1;
//						} catch (Exception e) {
//							Logger.error(e);
//						}
//						return null;
//					}
//
//					@Override
//					public void onSuccess(Object result) {
//					}
//
//					@Override
//					public void onFail(Object result) {
//					}
//
//					@Override
//					public void onRequestCache(Object result) {
//					}
//
//					@Override
//					public Object getRequestCache() {
//						return null;
//					}
//				});
			}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	/**
	 * 发送给应用层
	 */
	private void sendMessageReceived(Context context, Bundle bundle) {
		// 如果查看消息的MessageActivity在top，在此Activity里面已经有监听，将自动刷出消息出来，不再发送MessageReceiver.MESSAGE_RECEIVED_ACTION。
//		if (MessageActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MessageReceiver.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MessageReceiver.KEY_MESSAGE, message);
//			if (StringUtil.isEnter(context, extras, " message")) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (null != extraJson && extraJson.length() > 0) {
//						msgIntent.putExtra(MessageReceiver.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//			}
//			context.sendBroadcast(msgIntent);
//		}
	}

	/**
	 * 直接打开，查看消息。
	 */
	private void openMessageActivity(Context context, Bundle bundle) {
		// 打开自定义的Activity
		Intent i = new Intent(context, MainActivity.class);
		i.putExtras(bundle);
		// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(i);
	}

}
