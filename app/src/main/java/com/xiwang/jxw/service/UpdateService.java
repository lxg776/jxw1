package com.xiwang.jxw.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;


import com.loopj.android.http.BinaryHttpResponseHandler;
import com.xiwang.jxw.R;
import com.xiwang.jxw.config.FileConfig;
import com.xiwang.jxw.network.AppHttpClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

/**
 * 更新
 *
 * @author 綦巍
 * @Description
 * @date 2015-6-1
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 */
public class UpdateService extends Service {
	/**
	 * 通知栏服务
	 */
	private NotificationManager notificationManager;
	/**
	 * 通知栏对象
	 */
	private Notification notification;
	/**
	 * 当前下载百分比
	 */
	private double currentPercent = 0;

	/** apk包的大小 */
	// private Float apkSize;
	/**
	 * apk包的路径
	 */
	private String apkPath;
	/**
	 * 本地apk
	 */
	private String apkLocal;
	/**
	 * 缓存apk路径
	 */
	private String tempApkPath;
	private static final int NOTIFYCATION_DOWNLOAD_ID = 20150531;
	private static final int LOAD_APK_UPDATE = 0;
	private static final int LOAD_APK_FINASH = 1;
	private static final int LOAD_APK_ERROR = 2;
	/**
	 * 异步消息处理对象
	 *
	 * @version 1.0
	 * @date 2013-4-26
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
				case LOAD_APK_UPDATE:
					updateNotification();

					break;
				case LOAD_APK_FINASH:
					finish();
					break;
				case LOAD_APK_ERROR:
					loadError();
					break;
			}
		}
	};
	/**
	 * 更新下载进度的线程
	 */
	private Runnable update = new Runnable() {
		@Override
		public void run() {
			if (currentPercent < 100) {
				handler.sendEmptyMessage(LOAD_APK_UPDATE);
				handler.postDelayed(update, 100);
			}
		}
	};

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		try {
			Bundle bundle = intent.getExtras();
			String path = bundle.getString("path");
			String size = bundle.getString("size");
			if (path == null) {
				stopService(new Intent(this, UpdateService.class));
			} else {
				showNotification();
				// apkSize = Float.parseFloat(size);
				apkPath = path;
				apkLocal = FileConfig.PATH_DOWNLOAD
						+ apkPath.substring(apkPath.lastIndexOf("/") + 1);
				tempApkPath = apkLocal.replace(".apk", ".temp");
				startDownLoad();
			}
		} catch (Exception e) {

		}

	}

	/**
	 * 开始下载文件
	 *
	 * @author 罗文忠
	 * @version 1.0
	 */
	private void startDownLoad() {
		handler.postDelayed(update, 100);
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				downloadFile(apkPath, apkLocal);
////				if (currentPercent < 100) {
////					loadError();
////				}
//			}
//		}).start();
		downloadFile(apkPath, apkLocal);
	}

	/**
	 * 更新通知栏进度条
	 *
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-26
	 */
	private void updateNotification() {
		// currentPercent = new File(tempApkPath).length() / apkSize * 100;
		notification.contentView.setTextViewText(R.id.notice_title, "当前下载" + (int) currentPercent
				+ "%");
		notification.contentView.setProgressBar(R.id.notice_progressbar, 100, (int) currentPercent,
				false);
		notification.contentView.setViewVisibility(R.id.notice_progressbar, View.VISIBLE);
		notification.contentView.setViewVisibility(R.id.notice_content, View.INVISIBLE);
		notificationManager.notify(NOTIFYCATION_DOWNLOAD_ID, notification);
	}

	public void downloadFile(String path, final String savePath) {

		AppHttpClient.client.get(path, new BinaryHttpResponseHandler() {


			@Override
			public void onSuccess(int i, Header[] headers, byte[] bytes) {
				File tempFile = new File(tempApkPath);
					if (!new File(FileConfig.PATH_DOWNLOAD).exists()) {
						new File(FileConfig.PATH_DOWNLOAD).mkdirs();
					}
					try {
						for (File file : new File(FileConfig.PATH_DOWNLOAD).listFiles()) {
							file.delete();
						}

					} catch (Exception e) {

						e.printStackTrace();
					}

				FileOutputStream  output = null;
				BufferedOutputStream bufferedOutput = null;

				try {
					tempFile.createNewFile(); // 创建文件
					output = new FileOutputStream(tempFile);
					bufferedOutput= new BufferedOutputStream(output);
					bufferedOutput.write(bytes);
					tempFile.renameTo(new File(savePath));
					handler.sendEmptyMessage(LOAD_APK_FINASH);
				} catch (FileNotFoundException e) {
					handler.sendEmptyMessage(LOAD_APK_ERROR);
					e.printStackTrace();
				} catch (IOException e) {
					handler.sendEmptyMessage(LOAD_APK_ERROR);
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
				handler.sendEmptyMessage(LOAD_APK_ERROR);
			}

			@Override
			public void onProgress(long bytesWritten, long totalSize) {
				super.onProgress(bytesWritten, totalSize);
				currentPercent=(int) ((bytesWritten * 1.0 / totalSize) * 100);
			}
		});
	}




//	/**
//	 * 下载文件.下载文件存储至指定路径.
//	 *
//	 * @param path 下载路径.
//	 * @param savePath 存储路径.
//	 * @throws MalformedURLException 建立连接发生错误抛出MalformedURLException.
//	 * @throws IOException 下载过程产生错误抛出IOException.
//	 * @return 下载成功返回true,若下载失败则返回false.
//	 * @author 刘艺谋
//	 * @version 1.0, 2013-3-6
//	 */
//	public void downloadFile(String path, String savePath) {
//
//
//
//
//		File tempFile = new File(tempApkPath);
//		if (!new File(FileConfig.PATH_DOWNLOAD).exists()) {
//			new File(FileConfig.PATH_DOWNLOAD).mkdirs();
//		}
////		 if (new File(savePath).exists()) {
////		 handler.sendEmptyMessage(LOAD_APK_FINASH);
////		 currentPercent = 100;
////		 return;
////		 } else {
//		// 清理之前下载的旧版APK文件
//		try {
//			for (File file : new File(FileConfig.PATH_DOWNLOAD).listFiles()) {
//				file.delete();
//			}
//		} catch (Exception e) {
//
//		}
//		// }
//		InputStream input = null;
//		OutputStream output = null;
//		try {
//			URL url = new URL(path);
//			HttpURLConnection conn = null;// (HttpURLConnection)
//											// url.openConnection();
//			try {
//				conn = NetUtil.getUrlConnectionByUrl(url);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			 if (TApplication.iSOUTNET == 0) {//添加HTTP 请求头密文
//
//				 conn.setRequestProperty("Authorization", "Basic YndvaWw6YndvaWwxMjM=");// 主要是为了添加服务器验证
//				 }
//			conn.setRequestMethod("GET");
//			conn.setDoInput(true);
//			conn.connect();
//			int code = conn.getResponseCode();
//			if (code == 200) {
//				/** 总大小 */
//				int size = conn.getContentLength();
//				int threadSize = 0;
//				input = conn.getInputStream();
//				tempFile.createNewFile(); // 创建文件
//				output = new FileOutputStream(tempFile);
//				byte buffer[] = new byte[1024];
//				int read = 0;
//				while ((read = input.read(buffer)) != -1) { // 读取信息循环写入文件
//					output.write(buffer, 0, read);
//					threadSize = threadSize + read;
//					currentPercent = threadSize * 100 / size;
//				}
//				output.flush();
//				currentPercent = 100;
//				tempFile.renameTo(new File(savePath));
//				handler.sendEmptyMessage(LOAD_APK_FINASH);
//			} else {
//				handler.sendEmptyMessage(LOAD_APK_ERROR);
//			}
//		} catch (MalformedURLException e) {
//			handler.sendEmptyMessage(LOAD_APK_ERROR);
//		} catch (IOException e) {
//			handler.sendEmptyMessage(LOAD_APK_ERROR);
//		} finally {
//			try {
//				output.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}

	/**
	 * 显示通知栏
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-27
	 * 
	 */
	private void showNotification() {
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
		notificationManager = (NotificationManager) getSystemService(Activity.NOTIFICATION_SERVICE);
		notification = new Notification();
		notification.icon = R.mipmap.ic_launcher;
		notification.tickerText = "等待下载";
		notification.when = System.currentTimeMillis();
		// 通知栏显示所用到的布局文件
		notification.contentView = new RemoteViews(getPackageName(), R.layout.view_notice_progress);
		notification.contentView.setImageViewResource(R.id.notice_icon, R.mipmap.ic_launcher);
		// notification.contentView.setTextViewText(R.id.notice_title, "当前进度" + currentPercent + "%");
		notification.contentView.setTextViewText(R.id.notice_title, "正在下载中...");
		notification.contentView.setProgressBar(R.id.notice_progressbar, 100, (int) currentPercent,
				true);
		notification.contentIntent = pIntent;
		notification.flags = Notification.FLAG_NO_CLEAR;
		notificationManager.notify(NOTIFYCATION_DOWNLOAD_ID, notification);
	}

	/**
	 * 完成下载
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-27
	 * 
	 */
	private void finish() {
		currentPercent = 100;
		handler.removeCallbacks(update);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.contentView.setViewVisibility(R.id.notice_content, View.VISIBLE);
		notification.contentView.setViewVisibility(R.id.notice_progressbar, View.GONE);
		notification.contentView.setTextViewText(R.id.notice_title, "c2b_phone新版本下载完成");
		notification.contentView.setTextViewText(R.id.notice_content, "点击安装新版本");
		notificationManager.notify(NOTIFYCATION_DOWNLOAD_ID, notification);
		setInstallApk(apkLocal);
		stopService(new Intent(this, UpdateService.class));
	}

	/**
	 * 下载失败
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-5-12
	 * 
	 */
	private void loadError() {
		handler.removeCallbacks(update);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.contentView.setViewVisibility(R.id.notice_content, View.VISIBLE);
		notification.contentView.setViewVisibility(R.id.notice_progressbar, View.GONE);
		notification.contentView.setTextViewText(R.id.notice_title, "c2b_phone新版本下载失败");
		notification.contentView.setTextViewText(R.id.notice_content, "点击重新下载");
		Intent intent = new Intent(this, UpdateService.class);
		intent.putExtra("path", apkPath);
		// intent.putExtra("size", apkSize + "");
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
		notification.contentIntent = pendingIntent;
		notificationManager.notify(NOTIFYCATION_DOWNLOAD_ID, notification);
		stopService(new Intent(this, UpdateService.class));
	}

	/**
	 * 设置点击后安装apk
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-27
	 * 
	 * @param path
	 */
	private void setInstallApk(String path) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(new File(path)),
				"application/vnd.android.package-archive");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		notification.contentIntent = pendingIntent;
		notificationManager.notify(NOTIFYCATION_DOWNLOAD_ID, notification);
		startActivity(intent);
	}
}
