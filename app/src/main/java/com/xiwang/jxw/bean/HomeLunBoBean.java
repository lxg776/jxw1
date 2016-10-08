package com.xiwang.jxw.bean;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * 首页轮播
 * @author liangxg
 *
 */
public class HomeLunBoBean extends BaseBean {
	/**要轮播的图片*/
	ArrayList<HfPagerBean> dataList;
	/**多久更换轮播图*/
	long rotation_speed;

	
	 
	public ArrayList<HfPagerBean> getDataList() {
		return dataList;
	}



	public void setDataList(ArrayList<HfPagerBean> dataList) {
		this.dataList = dataList;
	}



	public long getRotation_speed() {
		return rotation_speed;
	}



	public void setRotation_speed(long rotation_speed) {
		this.rotation_speed = rotation_speed;
	}



	@Override
	protected void init(JSONObject jSon) throws JSONException {
		// TODO Auto-generated method stub
		setRotation_speed(jSon.optLong("rotation_speed"));
		setDataList((ArrayList<HfPagerBean>)toModelList(jSon.optString("dataList"), HfPagerBean.class));
	}

}
