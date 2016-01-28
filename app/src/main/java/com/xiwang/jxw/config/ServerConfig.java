package com.xiwang.jxw.config;

/**
 * 服务端配置类
 * 
 * @Description 此处定义服务器的链接地址配置和接口请求的方法，
 * @author CodeApe
 * @version 1.0
 * @date 2014年4月4日
 * 
 */
public class ServerConfig {
	/**
	 * 内网切换，取消注释：“服务器地址_内网”、“API接口方法模块_内网”、“SERVER_TYPE_VALUE”
	 */
	// *****************************网请求消息状态码 ******************************//
	/** 请求接口数据成功状态码 */
	public static final String RESPONSE_STATUS_SUCCESS = "10000";
	/** 本地上传失败 */
	public static final String EXCEPTION_UPLOAD_ERROR_STATUS = "805";
	/** json数据解析错误 */
	public static final String JSON_DATA_ERROR="204";

	// **************************失败状态码*****************************//
	/** 注册需要手机号码 */
	public static final String STATUS_REGISTER_NEED_PHONE = "60001";
	/** 登录需要手机号码 */
	public static final String STATUS_LOGIN_NEED_PHONE = "60003";
	/** Tickey 过期失效 */
	public static final String STATUS_TICKEY_UNVALID = "60004";

	// ***************************接口请求配置 ****************************//
	/** 服务器连接方法key */
	public static final String SERVER_METHOD_KEY = "token";
	/** 服务器连接类型key */
	public static final String SERVER_TYPE_KEY = "key";
	/** 服务器连接类型数据 */
	public static final String SERVER_TYPE_VALUE = "123test";
	/** 服务器连接版本key */
	public static final String SERVER_VESRTION_KEY = "v";
	/** 服务器连接版本 数据 */
	public static final String SERVER_VESRTION_VAULE = "1.0";
	/** 服务器升级版本key */
	public static final String SERVER_UPDATE_KEY = "app_v";
	/** 服务器升级版本 数据 */
	public static final String SERVER_UPDATE_VAULE = "2.0";
	/** 服务器超时时间 */
	public static final int SERVER_CONNECT_TIMEOUT = 20000;
	/** 请求数据条数 */
	public static final String PAGE_COUNT = "10";
	/** 管理后台分配给此系统的连接ID的key */
	public static final String SERVER_CAS_KEY = "res_key";
	/** 管理后台分配给此系统的连接ID */
	public static final String SERVER_CAS_VALUE = "100";

	/*************************** 服务器地址_外网 ****************************/

	/** Api服务器地址 */
	// public final static String SERVER_API_URL = "http://www.brightoilonline.cn/b2b/";
	public final static String SERVER_API_URL = "http://m.jingxi.net/";
	/** 图片根地址 */
	public final static String IMAGE_BASE_URL = "http://images.jingxi.net/";
	/** 西网服务协议 */
	public final static String PROTOCOL_URL = "http://www.baidu.com";
	/** 获取用户信息*/
	public final static String MYINFO_URL=SERVER_API_URL+"getapp.php?a=myinfo";
	/**发贴 回复*/
	public final static String PUBLISH_URL=SERVER_API_URL+"getapp.php?a=post";
	/**表情链接*/
	public final static String SMILES_URL=SERVER_API_URL+"getapp.php?a=smiles";
	/**主题分类连接*/
	public final static String THREAD_TYPE_URL=SERVER_API_URL+"getapp.php?a=threadtype&type=fight&fid=2";

	/**点赞或者反对*/
	public final static String DIG_FIGHT_URL=SERVER_API_URL+"getapp.php";

	// public final static String SERVER_API_URL = "http://192.168.202.75/b2b/";
	// public final static String SERVER_API_URL = "http://192.168.202.75/b2b/";
	// public final static String SERVER_API_URL = "http://www.brightoilonline.cn/b2b/";
	// public final static String SERVER_API_URL = "http://192.168.200.178/b2b/";
	/** 启动界面图片 */
	public final static String START_APP_IMG=SERVER_API_URL+"getapp.php?a=openimg";
	/** 帖子详情接口 */
	public final static String NEWS_DETAIL=SERVER_API_URL+"getapp.php?a=read";
	/** 登录接口 */
	public final static String USER_LOGIN=SERVER_API_URL+"getapp.php?a=login";
	/** 注册接口 */
	public final static String USER_REG=SERVER_API_URL+"getapp.php?a=reg";
	/** 获取栏目接口 */
	public final static String MENUTOP_TOP=SERVER_API_URL+"getapp.php?a=menutop";
	/** 图片上传接口 */
	public final static String UPLOAD_IMG=SERVER_API_URL+"hack/uploadappimg.php";
	/** 发帖接口 */
	public final static String TOPIC_URL=SERVER_API_URL+"getapp.php?a=post";




	/** 启动页面图片 */
	public static final String START_APP_IMAGE = "http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1312/14/c25/29628904_1387010953139_1024x1024.jpg";
}
