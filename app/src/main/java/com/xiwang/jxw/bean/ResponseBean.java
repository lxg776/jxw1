package com.xiwang.jxw.bean;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.xiwang.jxw.config.ServerConfig;


/**
 * 网络请求返回状态属性
 * 
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2014年12月30日
 * 
 */
public class ResponseBean extends BaseBean {
	/**
	 * 用于序列化
	 * 
	 * @author CodeApe
	 * @version 1.0
	 * @date 2014年12月31日
	 */
	private static final long serialVersionUID = 1L;
	/** 返回状态码 */
	private String status;
	/** 返回消息 */
	private String info;
	/** 返回数据 */
	private Object object;

	/**
	 * 请求是否返回成功
	 * 
	 * @version 1.0
	 * @createTime 2014年12月30日,下午11:19:49
	 * @updateTime 2014年12月30日,下午11:19:49
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return ServerConfig.RESPONSE_STATUS_SUCCESS.equals(getStatus());
	}

	public ResponseBean() {
	}

	public ResponseBean(String msgStr) {
		super(msgStr);
	}

	/**
	 * 构造函数
	 * 
	 * @version 1.0
	 * @createTime 2014年4月19日,下午4:21:43
	 * @updateTime 2014年4月19日,下午4:21:43
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param status 状态码
	 * @param info 状态描述符
	 * @param object 附带信息
	 */
	public ResponseBean(String status, String info, Object object) {
		this.status = status;
		this.info = info;
		this.object = object;
	}

	@Override
	protected void init(JSONObject jSon) throws JSONException {
		status = jSon.optString("status");
		info = jSon.optString("info");
		object = jSon.optString("object");
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> getArraylist(Class<T> clazz) {
		if (object instanceof ListBean) {
			return (ArrayList<T>) (((ListBean) object).getModelList());
		} else if (object instanceof ArrayList) {
			return (ArrayList<T>) object;
		} else {
			return new ArrayList<T>();
		}
	}

	@Override
	public String toString() {
		return info.toString();
	}
}