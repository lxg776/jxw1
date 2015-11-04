package com.xiwang.jxw.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.xiwang.jxw.R;
import com.xiwang.jxw.config.TApplication;


/**
 * 所有Bean的基类，需要处理Json解析的Bean都需要继承自该类
 * 
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2014年12月30日
 * 
 */
public abstract class BaseBean implements Serializable {

	/**
	 * 
	 * @author CodeApe
	 * @version 1.0
	 * @date 2014年12月30日
	 */
	private static final long serialVersionUID = 3611224074993323709L;

	/**
	 * 无参构造函数
	 * 
	 * @version 1.0
	 * @createTime 2014年12月30日,下午11:29:32
	 * @updateTime 2014年12月30日,下午11:29:32
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public BaseBean() {
	}

	/**
	 * 构造方法，根据传入的Json字符串生成对象
	 * 
	 * @version 1.0
	 * @createTime 2014年12月30日,下午11:28:54
	 * @updateTime 2014年12月30日,下午11:28:54
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param jsonSrc
	 */
	public BaseBean(String jsonSrc) {
		init(jsonSrc);
	}

	/**
	 * 将json数据解析为bean对象，需要实现这个方法
	 * 
	 * @version 1.0
	 * @createTime 2014年12月30日,下午11:24:05
	 * @updateTime 2014年12月30日,下午11:24:05
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param jSon 传入的json对象
	 * @throws JSONException 抛出的json异常
	 */
	protected abstract void init(JSONObject jSon) throws JSONException;

	/**
	 * 将json数据解析为bean对象
	 * 
	 * @version 1.0
	 * @createTime 2014年12月30日,下午11:25:31
	 * @updateTime 2014年12月30日,下午11:25:31
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param jsonSrc 输入的json字符串,用于转换成接送对象
	 */
	public void init(String jsonSrc) {
		if (jsonSrc == null || jsonSrc.equals("")) {
			return;
		}
		try {
			JSONObject jSon = new JSONObject(jsonSrc);
			init(jSon);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "BaseBean [toString()=" + super.toString() + "]";
	}

	/**
	 * 析json数据为cls类型的对象，并赋值于ResponseBean对象
	 * 
	 * @version 1.0
	 * @createTime 2014年12月30日,下午11:45:21
	 * @updateTime 2014年12月30日,下午11:45:21
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param responseBean 状态属性对象
	 * @param cls 需要解析的class类型
	 */
	public static void setResponseObject(ResponseBean responseBean, Class<? extends BaseBean> cls) {
		if (responseBean.isSuccess()) {
			try {
				BaseBean bean = newInstance(cls, (String) responseBean.getObject());
				responseBean.setObject(bean);
			} catch (JSONException e) {
				responseBean.setStatus(TApplication.context.getString(R.string.exception_local_json_code));
				responseBean.setInfo(TApplication.context.getString(R.string.exception_local_json_message));
				e.printStackTrace();
			}
		}
	}

	public static void setResponseObject(ResponseBean responseBean, Class<? extends BaseBean> cls, String listKeyName) {
		if (responseBean.isSuccess()) {
			try {
				String listStr = (String) responseBean.getObject();
				if (listKeyName != null) {
					listStr = new JSONObject(listStr).getString(listKeyName);
				}
				responseBean.setObject(newInstance(cls, listStr));
			} catch (JSONException e) {
				responseBean.setStatus(TApplication.context.getString(R.string.exception_local_json_code));
				responseBean.setInfo(TApplication.context.getString(R.string.exception_local_json_message));
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取缓存对象
	 * 
	 * @version 1.0
	 * @createTime 2015-9-22,上午11:40:48
	 * @updateTime 2015-9-22,上午11:40:48
	 * @createAuthor XiaoHuan
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param cls
	 * @param listKeyName
	 * @return
	 */
	public static ResponseBean getCacheObject(String cacheJson, Class<? extends BaseBean> cls, String listKeyName) {
		ResponseBean responseBean = new ResponseBean();
		try {
			String listStr = cacheJson;
			if (listKeyName != null) {
				listStr = new JSONObject(listStr).getString(listKeyName);
			}
			responseBean.setObject(newInstance(cls, listStr));
			return responseBean;
		} catch (JSONException e) {
			responseBean.setStatus(TApplication.context.getString(R.string.exception_local_json_code));
			responseBean.setInfo(TApplication.context.getString(R.string.exception_local_json_message));
			e.printStackTrace();
		}
		return responseBean;
	}
	/**
	 * 解析json数据为cls类型的对象列表，并赋值于ResponseBean对象
	 * 
	 * @version 1.0
	 * @createTime 2014年12月30日,下午11:34:07
	 * @updateTime 2014年12月30日,下午11:34:07
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param responseBean 状态属性对象
	 * @param cls 解析之后生成的对象类
	 * @param listKeyName 列表字段的名字(如果是解析子列表需要把自列表的key传入，如果直接是list对象可以不传次参数)
	 */
	public static void setResponseObjectList(ResponseBean responseBean, Class<? extends BaseBean> cls, String listKeyName) {
		if (responseBean.isSuccess()) {
			try {
				String listStr = (String) responseBean.getObject();
				if (listKeyName != null) {
					listStr = new JSONObject(listStr).getString(listKeyName);
				}
				responseBean.setObject(toModelList(listStr, cls));
			} catch (JSONException e) {
				responseBean.setStatus(TApplication.context.getString(R.string.exception_local_json_code));
				responseBean.setInfo(TApplication.context.getString(R.string.exception_local_json_message));
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析json数据为cls类型的对象列表，并赋值于ResponseBean对象
	 * 
	 * @version 1.0
	 * @createTime 2014年12月30日,下午11:34:07
	 * @updateTime 2014年12月30日,下午11:34:07
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param responseBean 状态属性对象
	 * @param cls 解析之后生成的对象类
	 */
	public static void setResponseObjectList(ResponseBean responseBean, Class<? extends BaseBean> cls) {
		if (responseBean.isSuccess()) {
			try {
				// //String listStr = ;
				// JSONObject jSonObj = new JSONObject((String) responseBean.getObject());
				// JSONArray dataList = (JSONArray) jSonObj.get("dataList");
				// //listStr.
				// responseBean.setObject(new ListBean(dataList.toString(), cls).getModelList());
				String listStr = (String) responseBean.getObject();
				responseBean.setObject(new ListBean(listStr, cls).getModelList());
			} catch (JSONException e) {
				responseBean.setStatus(TApplication.context.getString(R.string.exception_local_json_code));
				responseBean.setInfo(TApplication.context.getString(R.string.exception_local_json_message));
				e.printStackTrace();
			}
		}
	}

	/**
	 * 对象生成器，根据json字符串和cls类型，new一个BaseBean子类对象
	 * 
	 * @version 1.0
	 * @createTime 2014年12月30日,下午11:38:39
	 * @updateTime 2014年12月30日,下午11:38:39
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param cls 解析之后生成的对象类
	 * @param jsonSrc 传入的json字符串
	 * @return 解析之后的cls对象
	 * @throws JSONException json解析异常
	 */
	public static BaseBean newInstance(Class<? extends BaseBean> cls, String jsonSrc) throws JSONException {
		if (jsonSrc == null) {
			return null;
		}
		return newInstance(cls, new JSONObject(jsonSrc));
	}

	/**
	 * 对象生成器，根据json对象和clazz类型，new一个BaseBean对象
	 * 
	 * @version 1.0
	 * @createTime 2014年12月30日,下午11:42:07
	 * @updateTime 2014年12月30日,下午11:42:07
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param cls 需要解析成的class类对象
	 * @param jSon json对象
	 * @return 解析之后的cls对象
	 * @throws JSONException json解析异常
	 */
	public static BaseBean newInstance(Class<? extends BaseBean> cls, JSONObject jSon) throws JSONException {
		BaseBean model = null;
		try {
			model = cls.newInstance();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		if (model != null) {
			model.init(jSon);
		}
		return model;
	}

	/**
	 * 将json字符串解析成指定cls对象的列表
	 * 
	 * @version 1.0
	 * @createTime 2014年12月30日,下午11:53:04
	 * @updateTime 2014年12月30日,下午11:53:04
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param jsonSrc 需要解析的json字符串
	 * @param cls 需要解析成的class对象
	 * @return 解析后的cls对象列表
	 * 
	 * @throws JSONException json解析异常
	 */
	public static ArrayList<? extends BaseBean> toModelList(String jsonSrc, Class<? extends BaseBean> cls) throws JSONException {
		ArrayList<BaseBean> modelList = new ArrayList<BaseBean>();
		if (TextUtils.isEmpty(jsonSrc) || cls == null) {
			return modelList;
		}
		JSONArray jSonArray = new JSONArray(jsonSrc);
		for (int i = 0; i < jSonArray.length(); i++) {
			BaseBean model = newInstance(cls, jSonArray.getJSONObject(i));
			modelList.add(model);
		}
		return modelList;
	}

	/**
	 * 将json字符串转换成Arraylist
	 * 
	 * @version 1.0
	 * @createTime 2014年12月30日,下午11:48:45
	 * @updateTime 2014年12月30日,下午11:48:45
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param msgStr json字符串
	 * @return 解析后的ArrayList对象
	 * @throws JSONException json解析异常
	 */
	public static ArrayList<String> toStringList(String msgStr) throws JSONException {
		return toStringList(msgStr, null);
	}

	/**
	 * 根据json字符串->String对象列表
	 * 
	 * @version 1.0
	 * @createTime 2014年12月30日,下午11:50:24
	 * @updateTime 2014年12月30日,下午11:50:24
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param msgStr 传入的json字符串
	 * @param keyName 只包含一个字段的json数据的字段名
	 * @return 解析后的Arrsylist对象
	 * @throws JSONException json解析异常
	 */
	public static ArrayList<String> toStringList(String msgStr, String keyName) throws JSONException {
		ArrayList<String> modelList = new ArrayList<String>();
		if (TextUtils.isEmpty(msgStr)) {
			return modelList;
		}
		JSONArray jSonArray = new JSONArray(msgStr);
		for (int i = 0; i < jSonArray.length(); i++) {
			String model = keyName == null ? jSonArray.getString(i) : jSonArray.getJSONObject(i).optString(keyName);
			modelList.add(model);
		}
		return modelList;
	}
}
