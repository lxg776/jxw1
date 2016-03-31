package com.xiwang.jxw.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiwang.jxw.R;


/**
 * 自定义输入框 Created by liangxg on 15/12/7.
 */
public class MyInputEditView2 extends RelativeLayout {

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

	public MyInputEditView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public MyInputEditView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		
		View contentView = View.inflate(context, R.layout.view_my_input_edit2,
				null);
		input_edt = (DeleteEditText) contentView.findViewById(R.id.input_edt);
		left_tv = (TextView) contentView.findViewById(R.id.left_tv);
		view_line = contentView.findViewById(R.id.line);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MyInputEditView);
		left_tv.setText(getTypeArrayText(a,
				R.styleable.MyInputEditView_leftText));
		input_edt.setHint(getTypeArrayText(a,
				R.styleable.MyInputEditView_rightHintText));
		default_line_c = a.getColor(R.styleable.MyInputEditView_lineColor1,
				getResources().getColor(R.color.black_transparent_26));
		change_line_c = a.getColor(R.styleable.MyInputEditView_lineColor1,
				getResources().getColor(R.color.red_500));
		view_line.setBackgroundColor(default_line_c);
//		input_edt
//				.setLineChangeListener(new DeleteEditText.LineChangeListener() {
//					@Override
//					public void change() {
//						view_line.setBackgroundColor(change_line_c);
//					}
//
//					@Override
//					public void back() {
//						view_line.setBackgroundColor(default_line_c);
//					}
//				});
		isPwd = a.getBoolean(R.styleable.MyInputEditView_isPwd, false);
		if (isPwd) {
			input_edt.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		addView(contentView, params);
		a.recycle();
		
		
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
}
