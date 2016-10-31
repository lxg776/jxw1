package com.xiwang.jxw.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast操作工具类
 * 
 * @Description
 * @author liangxg
 * @date 2015-6-22
 */
public class ToastUtil {


	public static void showToast(Context context, Object text) {
		if (text == null) {
			return;
		}
 		Toast.makeText(context, text.toString(), Toast.LENGTH_SHORT).show();
	}



}