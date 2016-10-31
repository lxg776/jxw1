package com.xiwang.jxw.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.loopj.android.http.AsyncHttpClient;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.xiwang.jxw.bean.SmileListBean;
import com.xiwang.jxw.bean.ThreadTypeBean;
import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.fileds.DataFields;
import com.xiwang.jxw.network.AppHttpClient;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.SpUtil;

import cn.jpush.android.api.JPushInterface;


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
	/** 表情集合 */
	public static List<SmileListBean> smilesList;
	/** 表情集合 */
	public static ArrayList<ThreadTypeBean> threadTypeList;
	public static int sdk = android.os.Build.VERSION.SDK_INT;
	public static String token;
	public static SpUtil sp ;
	public static UserBean mUser;
	public static  boolean isUmeng=true;

    @Override
	public void onCreate() {
		context = getApplicationContext();
		screenWidth = getResources().getDisplayMetrics().widthPixels;
		screenHight = getResources().getDisplayMetrics().heightPixels;

		/** 初始化ImageLoad */
		initImageLoader(context);
		sp = SpUtil.getSpUtil(DataFields.DATA_MINE, Context.MODE_PRIVATE);
		AppHttpClient.setHttpClient(new AsyncHttpClient(), this);

		/** 初始化JPush推送 */
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
        initPlatform();
		//内存检查
		if (LeakCanary.isInAnalyzerProcess(this)) {
			// This process is dedicated to LeakCanary for heap analysis.
			// You should not init your app in this process.
			return;
		}
		refWatcher= LeakCanary.install(this);


	}

	private RefWatcher refWatcher;

	public static RefWatcher getRefWatcher(Context context) {
		TApplication application = (TApplication) context.getApplicationContext();
		return application.refWatcher;
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

		ImgLoadUtil.getInstance().clearMemoryCache();


	}

	private void initPlatform(){
		PlatformConfig.setQQZone("1105245649", "QBSIYLgWrlOnaLgR");
		PlatformConfig.setWeixin("wx91c87e3ac3509b67", "fc9215a9c4f11929ebadf8aa60055a8f");
		PlatformConfig.setSinaWeibo("3416662071","f6090926381113998384b3113f4ab241");
		/** 开启分享回流 */
        Config.isloadUrl = true;
	}


}
