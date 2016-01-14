package com.xiwang.jxw.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.TextAdapter;
import com.xiwang.jxw.util.DialogUtil;
import com.xiwang.jxw.util.DisplayUtil;

/**
 * Created by sunshine on 15/12/9.
 */
public class MyTextSelectView extends LinearLayout {
    /** 上下文*/
    Context context;

    TextView text_tv;
    TextView hint_tv;
    View line;

    int default_line_c;
    int change_line_c;
    boolean is_hint;

    /** 下拉的文本*/
    String [] showItemes;

    /** 显示的标题*/
    String titleText;
    /**选项监听*/
    OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public String[] getShowItemes() {
        return showItemes;
    }

    public void setShowItemes(String[] showItemes) {
        this.showItemes = showItemes;
    }

    public MyTextSelectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context=context;

        View contentView = View.inflate(context, R.layout.view_my_textselect, null);
        text_tv = (TextView) contentView.findViewById(R.id.text_tv);
        hint_tv = (TextView) contentView.findViewById(R.id.hint_tv);
        line=contentView.findViewById(R.id.line);

        Drawable rightArrow=getResources().getDrawable(R.mipmap.arrow_down);
        rightArrow.setBounds(0, 0, DisplayUtil.dip2px(context, 16), DisplayUtil.dip2px(context, 16));
        text_tv.setCompoundDrawables(null, null, rightArrow, null);



        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyDialogView);
        hint_tv.setHint(getTypeArrayText(a, R.styleable.MyDialogView_topHinttext));
        text_tv.setHint(getTypeArrayText(a, R.styleable.MyDialogView_inputHinttext));
        titleText=getTypeArrayText(a, R.styleable.MyDialogView_dialog_title);
       // text_tv.setTextSize(a.getDimension(R.styleable.MyDialogView_textSize,16));
       // text_tv.getLayoutParams().height=a.getDimensionPixelSize(R.styleable.MyDialogView_input_height, DisplayUtil.dip2px(context, 48));



        is_hint=a.getBoolean(R.styleable.MyDialogView_is_hint, true);
        if(is_hint){
            hint_tv.setVisibility(View.VISIBLE);
        }else{
            hint_tv.setVisibility(View.GONE);
        }


        default_line_c=a.getColor(R.styleable.MyDialogView_default_lColor, getResources().getColor(R.color.black_transparent_26));
        change_line_c=a.getColor(R.styleable.MyDialogView_change_lColor, getResources().getColor(R.color.orange_500));
        line.setBackgroundColor(default_line_c);



        text_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (showItemes != null && showItemes.length > 0) {
                    showListView();
                }
            }
        });
        LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        addView(contentView, params);
    }

    /**
     * 显示listViwe
     */
    private void showListView(){
        TextAdapter textAdapter=new TextAdapter(context);
        textAdapter.setTextArray(showItemes);
        DialogUtil.defaultDialogListView(context, textAdapter, titleText, new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                text_tv.setText(showItemes[position]);
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(view,position);
                }
            }
        }, new DialogUtil.DialogLinstener() {
            @Override
            public void onShow() {
                line.setBackgroundColor(change_line_c);
            }

            @Override
            public void onDismiss() {
                line.setBackgroundColor(default_line_c);
            }
        });
    }



    public MyTextSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private String getTypeArrayText(TypedArray a, int index) {
        String txt = a.getString(index);


        if (txt == null) {
            return "";
        }
        return txt;
    }

    public interface  OnItemClickListener{
        public  void onItemClick(View view,int postion);
    }




}
