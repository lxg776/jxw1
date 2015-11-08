package com.xiwang.jxw.bean;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用于解析Json数据列表的bean
 *
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2014年12月31日
 *
 */
public class ListBean extends BaseBean {

	/**
	 * 
	 * @author CodeApe
	 * @version 1.0
	 * @date 2014年8月8日
	 */
	private static final long serialVersionUID = -2244381413323684472L;

	/** 需要进行json解析的数据类型 */
	private Class<? extends BaseBean> clazz;

	/** 记录总数 */
	private int count;
	/** 当前面 */
	private int page;
	/** 总页数 */
	private int pages;

	/** 列表数据 */
	private ArrayList<? extends BaseBean> modelList;

	public ListBean(String msgStr, Class<? extends BaseBean> clazz) throws JSONException {
		this.clazz = clazz;
		init(new JSONObject(msgStr));
	}
	
	public ListBean(String msgStr, Class<? extends BaseBean> clazz ,String listKey) throws JSONException{
		this.clazz = clazz;
		init(msgStr , listKey);
	}

	@Override
	protected void init(JSONObject jSon) throws JSONException {
		count = jSon.optInt("count", 0);
		page = jSon.optInt("page", 0);
		pages = jSon.optInt("pages", 0);
		modelList = toModelList(jSon.optString("list"), clazz);
	}
	
	protected void init(String jSon , String key) throws JSONException {
		count = 0;
		page = 0;
		pages =  0;
		modelList = toModelList(jSon, clazz);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public ArrayList<? extends BaseBean> getModelList() {
		return modelList;
	}

	public void setModelList(ArrayList<? extends BaseBean> modelList) {
		this.modelList = modelList;
	}

	@Override
	public String toString() {
		return "ListBean [clazz=" + clazz + ", count=" + count + ", page=" + page + ", pages=" + pages + ", modelList=" + modelList + "]";
	}

	
}
