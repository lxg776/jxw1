package com.xiwang.jxw.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiwang.jxw.R;


/**
 * 自定义短信验证码输入框 Created by liangxg on 15/12/7.
 */
public class MsgCodeInputEditView extends RelativeLayout {

	/** 输入文本 */
	DeleteEditText input_edt;
	/** 提示文本 */
	TextView left_tv;
	/** 提示文本 */
	View view_line;
	/** 默认横线背景 */
	int default_line_c;
	/** 改变背景 */
	int change_line_c;
	/** 是否密码控件 */
	boolean isPwd;

	/** 发送校验码按钮 */
	TextView send_btn;

	/** 倒计时 */
	int maxTime = 0;
	Handler mHandler;

	/** 发送监听 */
	SendListener sendListener;

	Context context;
	/** 提示信息 */
	TextView msg_tv;

	/**
	 * 倒计时
	 */
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			maxTime--;
			if (maxTime == 0) {
				send_btn.setVisibility(View.VISIBLE);
				msg_tv.setVisibility(View.GONE);
				return;
			}
			send_btn.setVisibility(View.GONE);
			msg_tv.setVisibility(View.VISIBLE);
			msg_tv.setText(maxTime + "秒后重发");
			mHandler.postDelayed(runnable, 1000);
		}
	};

	public SendListener getSendListener() {
		return sendListener;
	}

	public void setSendListener(SendListener sendListener) {
		this.sendListener = sendListener;
	}

	public MsgCodeInputEditView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public MsgCodeInputEditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	/**
	 * 返回按钮
	 */
	public void recoveryBtn() {
		mHandler.removeCallbacks(runnable);
		send_btn.setVisibility(View.VISIBLE);
		msg_tv.setVisibility(View.GONE);
		maxTime = 0;
	}

	private void init(Context context, AttributeSet attrs) {
		mHandler = new Handler();
		this.context = context;
		View contentView = View.inflate(context,
				R.layout.view_msg_code_input_edit, null);
		msg_tv = (TextView) contentView.findViewById(R.id.msg_tv);
		input_edt = (DeleteEditText) contentView.findViewById(R.id.input_edt);
		input_edt.setInputType(InputType.TYPE_CLASS_NUMBER);
		left_tv = (TextView) contentView.findViewById(R.id.left_tv);
		send_btn = (TextView) contentView.findViewById(R.id.send_btn);
		view_line = contentView.findViewById(R.id.line);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MyInputEditView);
		left_tv.setText(getTypeArrayText(a,
				R.styleable.MyInputEditView_leftText));
		input_edt.setHint(getTypeArrayText(a,
				R.styleable.MyInputEditView_rightHintText));
		default_line_c = a.getColor(R.styleable.MyInputEditView_lineColor1,
				getResources().getColor(R.color.black_transparent_26));
		change_line_c = a.getColor(R.styleable.MyInputEditView_lineColor2,
				getResources().getColor(R.color.red_500));
		view_line.setBackgroundColor(default_line_c);
		// input_edt
		// .setLineChangeListener(new DeleteEditText.LineChangeListener() {
		// @Override
		// public void change() {
		// view_line.setBackgroundColor(change_line_c);
		// }
		//
		// @Override
		// public void back() {
		// view_line.setBackgroundColor(default_line_c);
		// }
		// });
		isPwd = a.getBoolean(R.styleable.MyInputEditView_isPwd, false);
		if (isPwd) {
			input_edt.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		addView(contentView, params);
		a.recycle();
		widgetListener();
	}

	/**
	 * 获取Edt控件
	 * 
	 * @return
	 */
	public DeleteEditText getInput_edt() {
		return input_edt;
	}

	private String getTypeArrayText(TypedArray a, int index) {
		String txt = a.getString(index);
		if (txt == null) {
			return "";
		}
		return txt;
	}

	public String getText() {
		return input_edt.getText().toString();
	}

	public void setText(String value) {
		input_edt.setText(value);
	}

	public void widgetListener() {
		/*
		 * 发送短信校验码事件
		 */
		send_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (maxTime == 0) {
					/*
					 * 倒计时为了0执行回调
					 */
					if (sendListener != null) {
						sendListener.sendCode();
					}
					maxTime = 120;
					mHandler.postDelayed(runnable, 1000);
				}
			}
		});
	}

	public interface SendListener {
		/**
		 * 发送验证码
		 */
		public void sendCode();
	}
}
