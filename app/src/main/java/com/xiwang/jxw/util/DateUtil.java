package com.xiwang.jxw.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理工具
 * 
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2015年1月5日
 * @Copyright: Copyright (c) 2015 Shenzhen Utoow Technology Co., Ltd. All rights reserved.
 * 
 */
public class DateUtil {

	/**
	 * 获取系统当前时间
	 * 
	 * @version 1.0
	 * @createTime 2013-10-24,上午9:58:21
	 * @updateTime 2013-10-24,上午9:58:21
	 * @createAuthor liujingguo
	 * @updateAuthor liujingguo
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return 时间字符串
	 */
	public static String getDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @version 1.0
	 * @createTime 2015-3-25,下午1:14:23
	 * @updateTime 2015-3-25,下午1:14:23
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param str "yyyy-MM-dd HH:mm:ss"
	 * @return 时间字符串
	 */
	public static String getDate(String str) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(str);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String getDate(String fotmat, long time) {
		Date currentTime = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat(fotmat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String getDate(String fotmat, String time) {
		try {
			Date currentTime = new Date(Long.parseLong(time));
			SimpleDateFormat formatter = new SimpleDateFormat(fotmat);
			String dateString = formatter.format(currentTime);
			return dateString;
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 获取两个日期之间的间隔天数
	 *
	 * @version 1.0
	 * @createTime 2015年7月3日,下午6:04:21
	 * @updateTime 2015年7月3日,下午6:04:21
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param startDate yyyy-MM-dd
	 * @param endDate yyyy-MM-dd
	 * @return
	 */
	public static int getGapCount(String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date_start = null;
		Date date_end = null;
		try {
			date_start = sdf.parse(startDate);
			date_end = sdf.parse(endDate);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(date_start);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(date_end);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);

		return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 比较时间
	 *
	 * @version 1.0
	 * @createTime 2015年7月3日,下午3:33:06
	 * @updateTime 2015年7月3日,下午3:33:06
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int bijiao(String s1, String s2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(s1));
			c2.setTime(df.parse(s2));
		} catch (java.text.ParseException e) {
			return 0;
		}

		// c1.compareTo(c2);// 0=(c1相等c2) <0=(c1小于c2) >0=(c1大于c2)
		return c1.compareTo(c2);
	}

	/**
	 * 获取系统当前日期
	 * 
	 * @version 1.0
	 * @createTime 2014年6月3日,上午11:40:09
	 * @updateTime 2014年6月3日,上午11:40:09
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return 当前日期
	 */
	public static String getDay() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取当前时间点.
	 * 
	 * @return 当前时间.
	 * @version 1.0
	 * @createTime 2013年11月25日,下午5:44:39
	 * @updateTime 2013年11月25日,下午5:44:39
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String time = formatter.format(currentTime);
		return time;
	}

	/**
	 * 时间是否大于今天.
	 * 
	 * @param date 时间.
	 * @return 大于返回true,否则返回false.
	 * @version 1.0
	 * @createTime 2014年2月18日,下午8:34:46
	 * @updateTime 2014年2月18日,下午8:34:46
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static boolean isGreaterToday(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return System.currentTimeMillis() > d.getTime() ? false : true;
	}

	/** 传的数据格式必须是 2010-08-05 12:55:31 */
	@SuppressWarnings("deprecation")
	public static float getData(String date) {
		int month = 0;
		int day = 0;
		boolean b = false;
		float hours = 0;
		try {
			Date dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
			// Log.d("123456", "    年==" + (dates.getYear() + 1900)
			// + "月==" +
			// (dates.getMonth() + 1)
			// +"          日=="+dates.getDate()+"       hous==" +
			// dates.getHours());
			b = isLeapYear(dates.getYear() + 1900);
			day = dates.getDate();
			month = dates.getMonth() + 1;
			if (dates.getHours() == 0) {
				hours = 0;
			} else {
				hours = ((float) ((dates.getHours() + 1) * 4)) / 100;// 换算一天的小时在百分比里面占多少，以每天25个小时计算
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 因为x轴显示是1-365而实际下标是0-364，所以上面显示的点是根据实际下标来的，所以要减1
		if (month == 1) {
			return day - 1 + hours;
		} else if (month == 2) {
			return day + 31 - 1 + hours;
		} else if (month == 3) {
			if (b) {
				return day + 60 - 1 + hours;
			} else {
				return day + 59 - 1 + hours;
			}
		} else if (month == 4) {
			if (b) {
				return day + 91 - 1 + hours;
			} else {
				return day + 90 - 1 + hours;
			}
		} else if (month == 5) {
			if (b) {
				return day + 121 - 1 + hours;
			} else {
				return day + 120 - 1 + hours;
			}
		} else if (month == 6) {
			if (b) {
				return day + 152 - 1 + hours;
			} else {
				return day + 151 - 1 + hours;
			}
		} else if (month == 7) {
			if (b) {
				return day + 182 - 1 + hours;
			} else {
				return day + 181 - 1 + hours;
			}
		} else if (month == 8) {
			if (b) {
				return day + 213 - 1 + hours;
			} else {
				return day + 212 - 1 + hours;
			}
		} else if (month == 9) {
			if (b) {
				return day + 244 - 1 + hours;
			} else {
				return day + 243 - 1 + hours;
			}
		} else if (month == 10) {
			if (b) {
				return day + 274 - 1 + hours;
			} else {
				return day + 273 - 1 + hours;
			}
		} else if (month == 11) {
			if (b) {
				return day + 305 - 1 + hours;
			} else {
				return day + 304 - 1 + hours;
			}
		} else if (month == 12) {
			if (b) {
				return day + 335 - 1 + hours;
			} else {
				return day + 334 - 1 + hours;
			}
		}
		return 0;
	}

	/**
	 * 获取一年的天数，用集合来装
	 * 
	 * @version 1.0
	 * @createTime 2014年8月25日,下午3:44:10
	 * @updateTime 2014年8月25日,下午3:44:10
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static ArrayList<String> getdateDay() {
		ArrayList<String> arraylists = new ArrayList<String>();
		Date date = new Date();
		for (int j = 1; j <= 31; j++) {
			arraylists.add("1-" + j);
		}
		if (isLeapYear((date.getYear() + 1900))) {// 判断是否闰年
			for (int j = 1; j <= 29; j++) {
				arraylists.add("2-" + j);
			}
		} else {
			for (int j = 1; j <= 28; j++) {
				arraylists.add("2-" + j);
			}
		}
		for (int j = 1; j <= 31; j++) {
			arraylists.add("3-" + j);
		}
		for (int j = 1; j <= 30; j++) {
			arraylists.add("4-" + j);
		}
		for (int j = 1; j <= 31; j++) {
			arraylists.add("5-" + j);
		}
		for (int j = 1; j <= 30; j++) {
			arraylists.add("6-" + j);
		}
		for (int j = 1; j <= 31; j++) {
			arraylists.add("7-" + j);
		}
		for (int j = 1; j <= 31; j++) {
			arraylists.add("8-" + j);
		}
		for (int j = 1; j <= 30; j++) {
			arraylists.add("9-" + j);
		}
		for (int j = 1; j <= 31; j++) {
			arraylists.add("10-" + j);
		}
		for (int j = 1; j <= 30; j++) {
			arraylists.add("11-" + j);
		}
		for (int j = 1; j <= 31; j++) {
			arraylists.add("12-" + j);
		}
		return arraylists;
	}

	/**
	 * 判断是否闰年
	 * 
	 * @version 1.0
	 * @createTime 2014年8月25日,下午3:43:59
	 * @updateTime 2014年8月25日,下午3:43:59
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 400) == 0) {
			return true;
		}
		if ((year % 4) == 0) {
			if ((year % 100) == 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 判断当前日期是星期几
	 * 
	 * @version 1.0
	 * @createTime 2015年7月13日,上午11:18:52
	 * @updateTime 2015年7月13日,上午11:18:52
	 * @createAuthor gushiyong
	 * @updateAuthor gushiyong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param pTime
	 * @return
	 * @throws Exception
	 */
	public static int dayForWeek(String pTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

}
