package com.xiwang.jxw.widget;

import android.content.Context;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.regex.Pattern;


/**
 * @author liangxg
 * @description
 * @date 2016/1/5
 * @modifier
 */
public class RichTextView extends TextView{
    private static Pattern IMAGE_TAG_PATTERN = Pattern.compile("\\[s:(.*?)\\]");


    Context mContext;

    public RichTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        this.mContext = context;
    }

    public RichTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.mContext = context;
    }

    public RichTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        SpannableString ss=null;


        this.append(ss);

    }


}
