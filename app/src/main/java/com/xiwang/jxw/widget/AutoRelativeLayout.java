package com.xiwang.jxw.widget;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 监听布局的的位置变化
 * @author liangxg
 *
 */
public class AutoRelativeLayout extends LinearLayout {
	
	
	public int old_l;
	public int old_t;
	public int old_r;
	public int old_b;
	OnLayoutChangeListener onLayoutChangeListener;

	OnKeyBoardListener keyboardListener;

	/**键盘标识*/
	public boolean isKeyBoard=false;


    int keyBoardHeight;

    int orgin_b;

    android.os.Handler  mhandler=new    android.os.Handler();


    Runnable runnable;


    public OnKeyBoardListener getKeyboardListener() {
        return keyboardListener;
    }

    public void setKeyboardListener(OnKeyBoardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

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

    /**
     * 获取键盘弹出高度
     * @return
     */
    public int getKeyBoardHeight() {
        return keyBoardHeight;
    }

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
        if(null!=runnable){
            mhandler.removeCallbacks(runnable);
        }
        if (old_b == 0) {
            orgin_b=b;
        }else{
            if(b==orgin_b){
                runnable=new Runnable() {
                    @Override
                    public void run() {
                        if(null!= keyboardListener&&isKeyBoard){
                            keyboardListener.onKeyBoardHide();
							isKeyBoard=false;
                        }
                    }
                };


            }else if(b<orgin_b){

                int distence=orgin_b-b;
                if(distence>keyBoardHeight){
                    keyBoardHeight=distence;
                }
                runnable =new Runnable() {
                    @Override
                    public void run() {
                        if(null!= keyboardListener){
							if(!isKeyBoard){
								isKeyBoard=true;
							}
                            keyboardListener.onKeyboardShow(keyBoardHeight);
                        }
                    }
                };
            }
        }
		if(null!=onLayoutChangeListener){
			onLayoutChangeListener.onChange(old_l, old_t, old_r, old_b, l, t, r, b);
		}

        this.old_l=l;
        this.old_r=r;
        this.old_t=t;
        this.old_b=b;
        if(runnable!=null){
            mhandler.postDelayed(runnable,150);
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


	/**
	 * view 位置变化更改监听事件
	 *
	 * @Description TODO
	 * @author liangxg
	 * @version 1.0
	 * @date 2013-11-28
	 *
	 */
	public interface OnKeyBoardListener {
		public void onKeyboardShow(int keyBoardHeight);
		public void onKeyBoardHide();

	}
	
	
	

}
