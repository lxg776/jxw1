package com.xiwang.jxw.bean;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class HfPagerBean extends BaseBean {
	/** 轮播图地址 */
	private String instruction;
	/** 位置 */
	private String position;
	/** 状态 */
	private String status;
	/** image_id */
	private String image_url;
	/** 关联图片地址 */
	private String image_id;
	/** 关联图片地址 */
	private String add_time;
	/** ad_id */
	private String ad_id;
	/** 图片连接地址 */
	private String image_hyperlink;
	/**标题显示*/
	private String ad_title;

	/** 分享内容 */
	private String share_content = "";
	/**是否分享*/
	private String share = "";
	private boolean isShare = false;

	public String getImage_hyperlink() {
		return image_hyperlink;
	}

	public void setImage_hyperlink(String image_hyperlink) {
		this.image_hyperlink = image_hyperlink;
	}

	@Override
	protected void init(JSONObject jSon) throws JSONException {
		// TODO Auto-generated method stub
		setInstruction(jSon.optString("instruction"));
		// setPosition(jSon.optString("position"));
		// setStatus(jSon.optString("status"));
		setImage_url(jSon.optString("image_url"));
		setImage_id(jSon.optString("image_id"));
		setAdd_time(jSon.optString("add_time"));
		setAd_id(jSon.optString("ad_id"));
		setImage_hyperlink(jSon.optString("image_hyperlink"));
		setAd_title(jSon.optString("ad_title"));
		setShare_content(jSon.optString("share_content"));
		setShare(jSon.optString("share"));
	}

	public boolean isShare() {
		if (!TextUtils.isEmpty(getShare())) {
			isShare = Boolean.valueOf(getShare()).booleanValue();
		}
		return isShare;
	}



	public String getShare_content() {
		return share_content;
	}

	public void setShare_content(String share_content) {
		this.share_content = share_content;
	}

	private String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getAd_title() {
		return ad_title;
	}

	public void setAd_title(String ad_title) {
		this.ad_title = ad_title;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getImage_id() {
		return image_id;
	}

	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getAd_id() {
		return ad_id;
	}

	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}

}
