package com.xiwang.jxw.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
/**
 * 监听布局的的位置变化
 * @author liangxg
 *
 */
public class AutoRelativeLayout extends RelativeLayout {
	
	
	public int old_l;
	public int old_t;
	public int old_r;
	public int old_b;
	
	OnLayoutChangeListener onLayoutChangeListener;

	public AutoRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public AutoRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public AutoRelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
	
		if(null!=onLayoutChangeListener){
			onLayoutChangeListener.onChange(old_l, old_t, old_r, old_b, l, t, r, b);
			this.old_l=l;
			this.old_r=r;
			this.old_t=t;
			this.old_b=b;
		}
		super.onLayout(changed, l, t, r, b);
	}
	

	public void setOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
		this.onLayoutChangeListener = onLayoutChangeListener;
	}
	
	/**
	 * view 位置变化更改监听事件
	 * 
	 * @Description TODO
	 * @author liangxg
	 * @version 1.0
	 * @date 2013-11-28
	 * 
	 */
	public interface OnLayoutChangeListener {

		/**
		 * view 位置变化更改监听事件
		 * 
		 * @version 1.0
		 * @createTime 2013-11-28,下午4:18:33
		 * @updateTime 2013-11-28,下午4:18:33
		 * @createAuthor CodeApe
		 * @updateAuthor CodeApe
		 * @updateInfo (此处输入修改内容,若无修改可不写.)
		 * 
		 * @param l 新的宽度
		 * @param t 新的高度
		 * @param r 旧的宽度
		 * @param b 新的高度
		 */
		public void onChange(int old_l, int old_t, int old_r, int old_b, int l, int t, int r, int b);
	}
	
	
	
	

}