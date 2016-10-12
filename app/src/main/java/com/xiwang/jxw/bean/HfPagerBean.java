package com.xiwang.jxw.bean;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class HfPagerBean extends BaseBean {



//	"url": "//m.jingxi.net/read-htm-tid-9204.html",
//			"title": "2016年，平江村秋收美景！",
//			"tid": "9204",
//			"image": "https://images.jingxi.net/s0/u=360,160&c=1&fn=Mon_1610/2_3498_18503a81796a77a.jpg"




	/** 轮播图地址 */
	private String url;
	/** 位置 */
	private String title;
	/** 状态 */
	private String tid;
	/** image_id */
	private String image;


	@Override
	protected void init(JSONObject jSon) throws JSONException {
		// TODO Auto-generated method stub
		setUrl(jSon.optString("url"));
		setTitle(jSon.optString("title"));
		setTid(jSon.optString("tid"));
		setImage(jSon.optString("image"));

	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
