package com.xiwang.jxw.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;

import com.xiwang.jxw.R;

import com.xiwang.jxw.util.Log;
import com.xiwang.jxw.util.ToastUtil;

/**
 * 百分比view
 * Created by sunshine on 15/12/12.
 */
public class PercentView extends View {

    /** 百分百*/
    int percent=0;


    int backgroundColor;
    int drawColor;
    Context context;
    PercentListener percentlistener;

    boolean is_rect;


    public PercentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public PercentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }


    private void init(Context context,AttributeSet attrs){

        this.context=context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PercentView);
        backgroundColor=a.getColor(R.styleable.PercentView_background_color, getResources().getColor(R.color.trans50));
        drawColor=a.getColor(R.styleable.PercentView_draw_color, getResources().getColor(R.color.trans));
        is_rect=a.getBoolean(R.styleable.PercentView_is_rect,true);
        a.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /** w h*/
        if(is_rect){
            drawRect(canvas);
        }else{
            drawArc(canvas);
        }




    }



    private void drawArc(Canvas canvas){

        int c=getWidth()/4;

        int w=getWidth();
        int h=getHeight();
        RectF oval2 = new RectF(w/2-c, h/2-c, w/2+c, h/2+c);
        /** 画笔*/
        Paint p = new Paint();
        //画背景
        p.setColor(backgroundColor);
       // canvas.drawArc(oval2, 0, 360, true, p);
        //画进度
       // p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        float percent_angle=360*percent/100;
        canvas.drawArc(oval2,270+percent_angle,360-percent_angle,true,p);
       // p.setColor(drawColor);
       // canvas.drawArc(oval2, 270, percent_angle, true, p);

    }


    private void drawRect(Canvas canvas){
        int w=getWidth();
        int h=getHeight();

        /** 画笔*/
        Paint p = new Paint();
        p.setColor(drawColor);

        /** 画进度*/
        float percent_h=h*percent/100;
        canvas.drawRect(0, 0, w, percent_h, p);


        /** 画背景*/
        p.setColor(backgroundColor);
        canvas.drawRect(0, percent_h, w, h, p);
    }

    /**
     * 设置百分比
     * @param percent
     */
    public void setPercent(int percent){

        if(percent>=100){
            this.percent=100;
        }
        if(percent<0){
            this.percent=0;
        }
        this.percent=percent;

        if(null!=percentlistener){
            percentlistener.onPercent(percent);
            if(percent==100){
                percentlistener.finish();
            }
        }
        Log.d(percent + "");
        invalidate();
    }


    public int getPercent() {
        return percent;
    }

    public PercentListener getPercentlistener() {
        return percentlistener;
    }

    public void setPercentlistener(PercentListener percentlistener) {
        this.percentlistener = percentlistener;
    }

    public interface PercentListener{

        public void finish();

        public void onPercent(int percent);


    }

}
