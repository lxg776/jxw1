package com.xiwang.jxw.widget;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiwang.jxw.R;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.util.DisplayUtil;
import java.util.Calendar;

/**
 * Created by sunshine on 15/12/8.
 */
public class MyDatePickView extends LinearLayout{
    TextView hint_tv;
    TextView date_tv;
    View line;

    Context context;

    /** 默认横线背景*/
    int default_line_c;
    /** 改变背景*/
    int change_line_c;
    

    FragmentManager fragmentManager;

    public MyDatePickView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }



    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public MyDatePickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.context=context;
        View contentView = View.inflate(context, R.layout.view_my_datepick,null);
        date_tv = (TextView) contentView.findViewById(R.id.date_tv);
        hint_tv = (TextView) contentView.findViewById(R.id.hint_tv);
        line=contentView.findViewById(R.id.line);

        Drawable rightArrow=getResources().getDrawable(R.mipmap.arrow_down);
        rightArrow.setBounds(0, 0, DisplayUtil.dip2px(context, 16), DisplayUtil.dip2px(context, 16));
        date_tv.setCompoundDrawables(null, null, rightArrow, null);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyDialogView);
        hint_tv.setText(getTypeArrayText(a, R.styleable.MyDialogView_topHinttext));
        date_tv.setHint(getTypeArrayText(a, R.styleable.MyDialogView_inputHinttext));

        default_line_c=a.getColor(R.styleable.MyDialogView_default_lColor, getResources().getColor(R.color.black_transparent_26));
        change_line_c=a.getColor(R.styleable.MyDialogView_change_lColor, getResources().getColor(R.color.orange_500));

        line.setBackgroundColor(default_line_c);



        date_tv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });


        LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        addView(contentView, params);

    }


    /**
     * 显示日期
     */
    private void showDateDialog(){
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        line.setBackgroundColor(change_line_c);

        if(TApplication.sdk>= Build.VERSION_CODES.ICE_CREAM_SANDWICH&&fragmentManager!=null){
            /**
             * 大于4.0
             */
            com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog=com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                    date_tv.setText(bulidTime(year, monthOfYear, dayOfMonth));
                }
            },year,month,day);
            datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    line.setBackgroundColor(default_line_c);
                }
            });

           datePickerDialog.show(fragmentManager,"日期选择");

        }else{
            DatePickerDialog datePickerDialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    date_tv.setText(bulidTime(year, monthOfYear, dayOfMonth));

                }


            },year,month,day);
            datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    line.setBackgroundColor(default_line_c);
                }
            });

            datePickerDialog.show();
        }
    }

    /**
     * 组装日期
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    private  String bulidTime(int year, int monthOfYear, int dayOfMonth){
        StringBuffer sb=new StringBuffer();
        sb.append(year).append("-").append(monthOfYear).append("-").append(dayOfMonth);

        return sb.toString();
    }

    private String getTypeArrayText(TypedArray a, int index) {
        String txt = a.getString(index);


        if (txt == null) {
            return "";
        }
        return txt;
    }


    public String getText(){
       return date_tv.getText().toString();
    }
}
