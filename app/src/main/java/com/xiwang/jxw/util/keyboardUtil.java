package com.xiwang.jxw.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class keyboardUtil {
	/**
	 * 隐藏键盘
	 * @param context
	 * @param view
	 */
	public static void hideKeyBoard(Context context,View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);	
	}
	
	
	/**
	 * 显示键盘
	 * @param context
	 * @param view
	 */
	public static void showKeyBoard(Context context, View view) {
		InputMethodManager imm =((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE));
		imm.showSoftInput(view, 0); 
		
	}
}
