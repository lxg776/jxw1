package com.xiwang.jxw.bean;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * 推送消息数据的基类
 * @Description TODO
 * @author   Xiaohuan
 * @date 2015年4月7日
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 */
public class MessageBean extends BaseBean {
	/** 变量描述*/
	private static final long serialVersionUID = 1L;
	/** 更推送消息id */
	private int id;
	/** user的Id  */
	private long user_id;
	/** 发送时间 */
	private String send_time;
	/** 推送消息内容 */
	private String Message_content;
	/** 更推送消息id */
	private int message_id;
	/** 推送消息标题 */
	private String title;
	/** 推送消息类型 */
	private String message_type;
	/** 推送消息链接网页 */
	private String path;
	/** 推送消息图片地址 */
	private String images;
	/** 消息是否已读（1未读，2已读） */
	private int is_read;
	
	@Override
	protected void init(JSONObject jSon) throws JSONException {
		// TODO 自动生成的方法存根

	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public int getIs_read() {
		return is_read;
	}

	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}

	public String getMessage_content() {
		return Message_content;
	}

	public void setMessage_content(String message_content) {
		Message_content = message_content;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	
}
