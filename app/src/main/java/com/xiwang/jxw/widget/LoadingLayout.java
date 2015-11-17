package com.xiwang.jxw.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.xiwang.jxw.R;
import com.xiwang.jxw.config.TApplication;

import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;

/**
 * Created by sunshine on 15/11/9.
 */
public class LoadingLayout extends RelativeLayout{



    LinearLayout loading_layout;
    LinearLayout content_layout;

    public LoadingLayout(Context context) {
        super(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        View contentView = View.inflate(context, R.layout.view_content_loading, null);
        loading_layout= (LinearLayout) contentView.findViewById(R.id.loading_layout);
        content_layout= (LinearLayout) contentView.findViewById(R.id.content_layout);

        if(TApplication.sdk>android.os.Build.VERSION_CODES.HONEYCOMB){
            ProgressBar indeterminate_progress_library= (ProgressBar) contentView.findViewById(R.id.progress_bar);
            IndeterminateProgressDrawable drawable=new IndeterminateProgressDrawable(context);
            drawable.setTint(context.getResources().getColor(R.color.orange_500));
            indeterminate_progress_library.setIndeterminateDrawable(drawable);
        }


        addView(contentView);
    }

    public void setContentLayout(View view){
        content_layout.addView(view);
    }


    /**
     * 设置是否显示loading
     * @param fla
     */
    public void setLoadView(boolean fla){

        if(fla){
            loading_layout.setVisibility(View.VISIBLE);
            content_layout.setVisibility(View.INVISIBLE);
        }else{
            loading_layout.setVisibility(View.GONE);
            content_layout.setVisibility(View.VISIBLE);
        }

    }



}
