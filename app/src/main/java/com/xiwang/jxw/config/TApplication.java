package com.xiwang.jxw.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.loopj.android.http.AsyncHttpClient;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.fileds.DataFields;
import com.xiwang.jxw.network.AppHttpClient;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.SpUtil;


/**
 * 全局公用Application类,单例初始化操作,静态成员储存
 * 
 * @Description
 * @author 綦巍
 * @date 2015-8-14
 */
public class TApplication extends Application  {
	/** 全局上下文，可用于文本、图片、sp数据的资源加载，不可用于视图级别的创建和展示 */
	public static Context context;
	/** 屏幕的宽 */
	public static int screenWidth = 0;
	/** 屏幕的高 */
	public static int screenHight = 0;

	/** 是否已经显示更新对话框 */
	public static boolean isShowUpdate = false;
	/** 应用版本名称 */
	public static String VERSION_NAME;
	/** 应用版本号 */
	public static int VERSION_CODE;
	/** 网络请求状态信息集合 */
	public static HashMap<String, String> mMapStatus;
	/** 应用是否在最前 */
	public static boolean IsOnTop = true;
	/** 应用是否在前台进程 */
	public static boolean IS_FRONT = true;

	/** 图片列表 */
	public static ArrayList<String> list_Images = new ArrayList<String>();
	/** 当前图片位置 */
	public static int current_Image_Position = 0;
	public static int authStatus = 0;
	/** 字体 */
	public static Typeface face;

	public static int sdk = android.os.Build.VERSION.SDK_INT;

	public static String token;
	public static SpUtil sp ;


	public static UserBean mUser;

	@Override
	public void onCreate() {
		context = getApplicationContext();
		screenWidth = getResources().getDisplayMetrics().widthPixels;
		screenHight = getResources().getDisplayMetrics().heightPixels;

		/** 初始化ImageLoad */
		initImageLoader(context);
		sp = SpUtil.getSpUtil(DataFields.DATA_MINE, Context.MODE_PRIVATE);
		AppHttpClient.setHttpClient(new AsyncHttpClient());

	}
	/**
	 * 初始化ImageLoader配置
	 * 
	 * @param context
	 */
	public  void initImageLoader(Context context) {

		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache");
		// 即保存的每个缓存文件的最大长宽
		ImageLoaderConfiguration config = ImgLoadUtil.getImageConfig(this);
		ImgLoadUtil.getInstance().init(config);
	}


}
