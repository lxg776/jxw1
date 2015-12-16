package com.xiwang.jxw.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiwang.jxw.R;

/**
 * Created by sunshine on 15/12/7.
 */
public class MyInputEditView extends LinearLayout {

    /** 输入文本*/
    DeleteEditText input_edt;
    /** 提示文本*/
    TextView hint_tv;
    /** 提示文本*/
    View view_line;

    /** 默认横线背景*/
    int default_line_c;
    /** 改变背景*/
    int change_line_c;

    public MyInputEditView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public MyInputEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        View contentView = View.inflate(context, R.layout.view_my_input_edit,null);
        input_edt = (DeleteEditText) contentView.findViewById(R.id.input_edt);
        hint_tv = (TextView) contentView.findViewById(R.id.hint_tv);
        view_line=contentView.findViewById(R.id.line);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyDialogView);
        hint_tv.setText(getTypeArrayText(a, R.styleable.MyDialogView_topHinttext));
        input_edt.setHint(getTypeArrayText(a, R.styleable.MyDialogView_inputHinttext));

        default_line_c=a.getColor(R.styleable.MyDialogView_default_lColor, getResources().getColor(R.color.black_transparent_26));
        change_line_c=a.getColor(R.styleable.MyDialogView_change_lColor, getResources().getColor(R.color.orange_500));

        view_line.setBackgroundColor(default_line_c);

        input_edt.setLineChangeListener(new DeleteEditText.LineChangeListener() {
            @Override
            public void change() {
                view_line.setBackgroundColor(change_line_c);
            }
            @Override
            public void back() {
                view_line.setBackgroundColor(default_line_c);
            }
        });






        LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        addView(contentView,params);
        a.recycle();
    }

    private String getTypeArrayText(TypedArray a, int index) {
        String txt = a.getString(index);


        if (txt == null) {
            return "";
        }
        return txt;
    }


    public String getText(){
        return input_edt.getText().toString();
    }

    public void setText(String value){
        input_edt.setText(value);
    }
}
