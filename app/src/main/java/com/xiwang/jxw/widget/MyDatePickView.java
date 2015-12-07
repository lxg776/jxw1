package com.xiwang.jxw.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiwang.jxw.R;
import com.xiwang.jxw.util.DisplayUtil;

/**
 * Created by sunshine on 15/12/8.
 */
public class MyDatePickView extends LinearLayout{
    TextView hint_tv;
    TextView date_tv;
    View line;

    public MyDatePickView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }



    public MyDatePickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        View contentView = View.inflate(context, R.layout.view_my_datepick,null);
        date_tv = (TextView) contentView.findViewById(R.id.date_tv);
        hint_tv = (TextView) contentView.findViewById(R.id.hint_tv);
        line=contentView.findViewById(R.id.line);

        Drawable rightArrow=getResources().getDrawable(R.mipmap.arrow_down);
        rightArrow.setBounds(0,0, DisplayUtil.dip2px(context,16),DisplayUtil.dip2px(context,16));
        date_tv.setCompoundDrawables(null, null, rightArrow, null);


        LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        addView(contentView, params);

    }
}
