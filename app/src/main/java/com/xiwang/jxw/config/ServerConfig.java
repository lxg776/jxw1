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
	public final static String MYINFO_URL="getapp.php?a=myinfo";

	// public final static String SERVER_API_URL = "http://192.168.202.75/b2b/";
	// public final static String SERVER_API_URL = "http://192.168.202.75/b2b/";
	// public final static String SERVER_API_URL = "http://www.brightoilonline.cn/b2b/";
	// public final static String SERVER_API_URL = "http://192.168.200.178/b2b/";
	/** 启动界面图片 */
	public final static String START_APP_IMG="getapp.php?a=openimg";
	/** 帖子详情接口 */
	public final static String NEWS_DETAIL="getapp.php?a=read";
	/** 登录接口 */
	public final static String USER_LOGIN="getapp.php?a=login";
	/** 注册接口 */
	public final static String USER_REG="getapp.php?a=reg";
	/** 获取栏目接口 */
	public final static String MENUTOP_TOP="getapp.php?a=menutop";
	/** 图片上传接口 */
	public final static String UPLOAD_IMG="hack/uploadappimg.php";

	/** 文件服务器地址 */
	public final static String SERVER_FILE_URL = "http://buddyfile.t.com";
	/** 图片服务器地址 */
	public final static String SERVER_IMAGE_URL = "http://buddyfile.t.com";
	/** 版本更新服务器 */
	public static final String SERVER_VERSION_URL = "http://buddy.t.com/Api/version/typeid/0";
	/*************************** 服务器地址_内网 ****************************/
	// /** Api服务器地址 */
	// public final static String SERVER_API_URL = "http://192.168.101.101/b2c/index.php/b2cphone/";
	// /** 文件服务器地址 */
	// public final static String SERVER_FILE_URL = "http://buddyfile.t.com";
	// /** 图片服务器地址 */
	// public final static String SERVER_IMAGE_URL = "http://buddyfile.t.com";
	// /** 版本更新服务器 */
	// public static final String SERVER_VERSION_URL = "http://buddy.t.com/Api/version/typeid/0";
	/*************************** 接口 ****************************/
	/** 登录 */
	public static final String METHOD_LOGIN = "index.php/b2bphone/user/login";
	/** 登录 */
	public static final String METHOD_VWEIFY = "index.php/b2bphone/user/verify_phone";



	/*************************** 企业账户 ****************************/
	/** 企业账户是否认证 */
	public static final String COMPANY_ISAUTH = "index.php/b2bphone/manage/isAuth";
	/** 企业 订单管理列表 */
	public static final String COMPANY_ORDER = "index.php/b2bphone/manage/orderList";
	/** 企业 订单详情 */
	public static final String COMPANY_DETAILS_ORDER = "index.php/b2bphone/manage/orderDetail";
	/** 企业 取消 订单 */
	public static final String COMPANY_CANCELORDER_ORDER = "index.php/b2bphone/manage/cancelOrder";

	/** 企业 认证 */
	public static final String COMPANY_ENTERPRISE = "index.php/b2bphone/manage/auth";

	/** 增加、编辑银行卡的填充数据 */
	public static final String COMPANY_BANK_MANAGEREADY = "index.php/b2bphone/common/manageReady";
	/** 企业 银行账户管理 */
	public static final String COMPANY_BANK_MANAGEMENT = "index.php/b2bphone/manage/bankList";
	/** 企业 增加银行账户 */
	public static final String COMPANY_BANK_ADD = "index.php/b2bphone/manage/editBank";
	/** 企业 删除账户管理 */
	public static final String COMPANY_BANK_DETELE = "index.php/b2bphone/manage/delBank";
	/** 企业 编辑账户管理 */
	public static final String COMPANY_BANK_EDIT = "index.b2bphone/manage/editBank";
	/** 企业 消息中心 */
	public static final String COMPANY_MESSAGE = "index.php/b2bphone/manage/noticeList";
	/** 企业 消息清空 */
	public static final String COMPANY_CLEAR_MESSAGE = "index.php/b2bphone/manage/clearNotice";

	/** 企业 帮助中心 */
	public static final String COMPANY_HELP_CENTER = "index.php/b2bphone/common/helpCenter";
	/** 企业 法律申明 */
	public static final String COMPANY_LEGAL_NOTICE = "index.php/b2bphone/common/legalNotice";
	/** 企业 法律申明 */
	public static final String COMPANY_VERSION = "index.php/b2bphone/manage/version";

	/** 省市地区 */
	public static final String COMPANY_BANK_ADDLIST = "index.php/b2bphone/common/addList";

	/** 上传图片 */
	public static final String UPLOAD_FILE = "index.php/b2bphone/file/upload";

	/** 版本更新 */
	public static final String COMPANY_UPDATE = "index.php/b2bphone/manage/version";

	/*************************** 财富中心 ****************************/

	/** 财富中心首页 */
	public static final String COMPANY_INFO = "index.php/b2bphone/wealth/info";
	/** 交易与收益列表 */
	public static final String COMPANY_DEALLIST = "index.php/b2bphone/wealth/dealList";
	/** 交易与收益列表详情 */
	public static final String COMPANY_DEALDETAIL = "index.php/b2bphone/wealth/dealDetail";
	/** 我的储油通列表 */
	public static final String COMPANY_MYSTORAGE_LIST = "index.php/b2bphone/wealth/myStorage";
	/** 我的储油通列表详情 */
	public static final String COMPANY_MYSTORAGE_DETAIL = "index.php/b2bphone/wealth/storageDetail";

	/** 提交兑油 前 计算金额与收益 */
	public static final String COMPANY_PRICE_SUPPORT = "index.php/b2bphone/common/priceSupport";
	/** 兑油 回购 转让 提油 填充数据 */
	public static final String COMPANY_WEALTH_WEALTHREAY = "index.php/b2bphone/common/wealthReady";
	/** 提交兑油 */
	public static final String COMPANY_WEALTH_TOCASH = "index.php/b2bphone/wealth/toCash";

	/** 修改价格 */
	public static final String COMPANY_WEALTH_EDITTRANSFER = "index.php/b2bphone/wealth/editTransfer";
	/** 取消转让 */
	public static final String COMPANY_WEALTH_CANCELTRANSFER = "index.php/b2bphone/wealth/cancelTransfer";
	/** 我的转让列表 */
	public static final String COMPANY_WEALTH_MYTRANSFER = "index.php/b2bphone/wealth/myTransfer";
	/** 我的转让列表详情 */
	public static final String COMPANY_WEALTH_TRANSFERDETAIL = "index.php/b2bphone/wealth/transferDetail";

	/** 提交回购 */
	public static final String COMPANY_WEALTH_BUYBACK = "index.php/b2bphone/wealth/buyback";
	/** 提交转让 */
	public static final String COMPANY_WEALTH_TRANSFER = "index.php/b2bphone/wealth/transfer";

	/** 提油 填充数据 */
	public static final String COMPANY_WEALTH_EXTRACTLIST = "index.php/b2bphone/wealth/myExtractList";
	/** 提交提油数据 */
	public static final String COMPANY_WEALTH_EXTRACTOIL = "index.php/b2bphone/wealth/extractOil";
	/** 提油进度列表详情 */
	public static final String COMPANY_WEALTH_EXTRACTDETAIL = "index.php/b2bphone/wealth/extractDetail";

	/** 转让区油品选择 */
	public static final String COMMON_TRANSFER_READY = "index.php/b2bphone/common/transferReady";
	/** 转让区列表 */
	public static final String WEALTH_TRANSFER_LIST = "index.php/b2bphone/wealth/transferList";
	/** 转让区详情 */
	public static final String WEALTH_TRANSFER_DETAIL = "index.php/b2bphone/wealth/transferDetail";

	/** 提现 */
	public static final String WEALTH_READY = "index.php/b2bphone/common/wealthReady";

	/*************************** 首页 ****************************/

	/** 商品卡列表 */
	public static final String HOME_MAIN = "index.php/b2bphone/index/main";
	/** 商品卡详情 */
	public static final String PRODUCT_DETAIL = "index.php/b2bphone/product/productDetail";
	/** 选择油品详情 */
	public static final String PRODUCT_SELLPRICE = "index.php/b2bphone/product/sellPrice";
	/** 油价走势 */
	public static final String INDEX_OIL_TREND = "index.php/b2bphone/index/oilTrend";
	/** 所有走势城市 */
	public static final String COMMON_TREND_READY = "index.php/b2bphone/common/trendReady";
	/** 创建订单 */
	public static final String PRODUCT_CREATEORDER = "index.php/b2bphone/product/createOrder";
	/** 首页全国油价走势 */
	public static final String INDEX_OIL_OILAVG = "index.php/b2bphone/index/oilAvg";

	/*************************** 支付 ****************************/
	/** 支付方式 */
	public static final String PAY_MODE = "index.php/b2bphone/pay/payList";
	/** 余额支付 */
	public static final String PAY_DEPOSIT_DATA = "index.php/b2bphone/pay/depositData";
	/** 余额提交支付 */
	public static final String PAY_BACK = "index.php/b2bphone/pay/payBack";
	/** 银联支付 获取tn 交易流水号 */
	public static final String PAY_UPPAY_TN = "index.php/b2bphone/pay/onlinePay";

	/*************************** 靖西网 ****************************/

	/** 启动页面图片 */
	public static final String START_APP_IMAGE = "http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1312/14/c25/29628904_1387010953139_1024x1024.jpg";
}
