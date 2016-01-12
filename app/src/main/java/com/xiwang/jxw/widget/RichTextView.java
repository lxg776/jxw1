package com.xiwang.jxw.widget;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.bean.SmileBean;
import com.xiwang.jxw.bean.SmileListBean;
import com.xiwang.jxw.config.ServerConfig;
import com.xiwang.jxw.util.CommonUtil;
import com.xiwang.jxw.util.DisplayUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.widget.gif.GifAnimationDrawable;
import com.xiwang.jxw.widget.gif.GifImageSpan;
import com.xiwang.jxw.widget.gif.GifSpanChangeWatcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifTextView;


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
       // initGifSpanChangeWatcher();
    }

    public RichTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        //initGifSpanChangeWatcher();
    }

    public RichTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.mContext = context;
       // SpannableString ss=null;
        //this.append(ss);
        //initGifSpanChangeWatcher();
    }

//    private GifSpanChangeWatcher mGifSpanChangeWatcher;
//    private void initGifSpanChangeWatcher() {
//        mGifSpanChangeWatcher = new GifSpanChangeWatcher(this);
//        addTextChangedListener(mGifSpanChangeWatcher);
//    }

//    @Override
//    public void setText(CharSequence text, BufferType type) {
//        type = BufferType.EDITABLE;
//        CharSequence oldText = getText();
//        if (!TextUtils.isEmpty(oldText) && oldText instanceof Spannable) {
//            Spannable sp = (Spannable) oldText;
//            final GifImageSpan[] spans = sp.getSpans(0, sp.length(), GifImageSpan.class);
//            final int count = spans.length;
//            for (int i = 0; i < count; i++) {
//                spans[i].getDrawable().setCallback(null);
//            }
//
//            final GifSpanChangeWatcher[] watchers = sp.getSpans(0, sp.length(), GifSpanChangeWatcher.class);
//            final int count1 = watchers.length;
//            for (int i = 0; i < count1; i++) {
//                sp.removeSpan(watchers[i]);
//            }
//        }
//
//        if (!TextUtils.isEmpty(text) && text instanceof Spannable) {
//            Spannable sp = (Spannable) text;
//            final GifImageSpan[] spans = sp.getSpans(0, sp.length(), GifImageSpan.class);
//            final int count = spans.length;
//            for (int i = 0; i < count; i++) {
//                spans[i].getDrawable().setCallback(this);
//            }
//
//            final GifSpanChangeWatcher[] watchers = sp.getSpans(0, sp.length(), GifSpanChangeWatcher.class);
//            final int count1 = watchers.length;
//            for (int i = 0; i < count1; i++) {
//                sp.removeSpan(watchers[i]);
//            }
//
//            if (mGifSpanChangeWatcher == null) {
//                mGifSpanChangeWatcher = new GifSpanChangeWatcher(this);;
//            }
//
//            sp.setSpan(mGifSpanChangeWatcher, 0, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE | (100 << Spanned.SPAN_PRIORITY_SHIFT));
//        }
//        super.setText(text, type);
//    }

    /**
     * 设置富文本
     */
    public void setRichText(String text){
        if(!TextUtils.isEmpty(text)){
            text=Html.fromHtml(text).toString();
            SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder(text);
            replaceSmiles(spannableStringBuilder);
            setText(spannableStringBuilder);
        }
    }

//    private GifImageSpan getImageSpan(Drawable drawable) {
//        GifImageSpan imageSpan = null;
//        CharSequence text = getText();
//        if (!TextUtils.isEmpty(text)) {
//            if (text instanceof Spanned) {
//                Spanned spanned = (Spanned) text;
//                GifImageSpan[] spans = spanned.getSpans(0, text.length(), GifImageSpan.class);
//                if (spans != null && spans.length > 0) {
//                    for (GifImageSpan span : spans) {
//                        if (drawable == span.getDrawable()) {
//                            imageSpan = span;
//                        }
//                    }
//                }
//            }
//        }
//
//        return imageSpan;
//    }

//    @Override
//    public void invalidateDrawable(Drawable drawable) {
//        GifImageSpan imageSpan = getImageSpan(drawable);
//        if (imageSpan != null) {
//            CharSequence text = getText();
//            if (!TextUtils.isEmpty(text)) {
//                if (text instanceof Editable) {
//                    Editable editable = (Editable)text;
//                    int start = editable.getSpanStart(imageSpan);
//                    int end = editable.getSpanEnd(imageSpan);
//                    int flags = editable.getSpanFlags(imageSpan);
//
//                    editable.removeSpan(imageSpan);
//                    editable.setSpan(imageSpan, start, end, flags);
//                }
//            }
//
//        } else {
//            super.invalidateDrawable(drawable);
//        }
//    }



    /**
     * 替换表情标签
     * @param spannableStringBuilder
     */
    private void replaceSmiles(final SpannableStringBuilder spannableStringBuilder){

        Matcher matcher=IMAGE_TAG_PATTERN.matcher(spannableStringBuilder.toString());
        while(matcher.find()){
            final int start=matcher.start(0);
            final int end=matcher.end(0);
            String sId=matcher.group(1);
            String url=getSmilesUrlById(sId);

           // String url="http://pic.joke01.com/uppic/13-05/30/30215236.gif";
            if(!TextUtils.isEmpty(url)){
                /**
                 * 异步加载表情
                 */
                ImageLoader.getInstance().loadImage(url, getSmilesDisplayImageOptions(), new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String uri, View arg1) {
                        // TODO Auto-generated method stub
                        //插入一张默认图片
                        //addDefaultImage(localFilePath, matchStringStartIndex, matchStringEndIndex);
                        ImageSpan imageSpan =new ImageSpan(getResources().getDrawable(R.mipmap.trans));
                        spannableStringBuilder.setSpan(imageSpan,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onLoadingComplete(String uri, View arg1, Bitmap bitmap) {
                        // TODO Auto-generated method stub
                        try {
                            if(null!=bitmap){
                                bitmap=zoomImage(bitmap, DisplayUtil.dip2px(mContext,24),DisplayUtil.dip2px(mContext,24));
                              //  GifDrawable gifDrawable=new GifDrawable(getResources().getAssets(),"mgif.gif");
                                ImageSpan imageSpan=new ImageSpan(mContext,bitmap);
//                                imageSpan.getDrawable().setCallback(RichTextView.this);
////                                if (mGifSpanChangeWatcher == null) {
////                                    mGifSpanChangeWatcher = new GifSpanChangeWatcher(RichTextView.this);;
////                                }
//                                //spannableStringBuilder.setSpan(mGifSpanChangeWatcher, 0, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE | (100 << Spanned.SPAN_PRIORITY_SHIFT));
                                spannableStringBuilder.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            }
                        }catch (Exception e){
                                e.printStackTrace();
                        }


                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                        // TODO Auto-generated method stub
                    }
                });
            }



        }

    }

    // 将Bitmap转换成InputStream
    private InputStream bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }



    private String getSmilesUrlById(String sId){

       List<SmileListBean> sList= CommonUtil.getSmileList(mContext);
       if(sList!=null&&sList.size()>0){
            for(SmileListBean smileListBean:sList){
                    if(null!=smileListBean&&smileListBean.getList()!=null&&smileListBean.getList().size()>0){
                            List<SmileBean> smileBeans=smileListBean.getList();
                        for (SmileBean smileBean:smileBeans){
                            if(sId.equals(smileBean.getId())){
                                return smileBean.getImg();
                            }
                        }
                    }
            }
       }
        return "";
    }


    /**
     * 对图片进行缩放
     * @param bgimage
     * @param newWidth
     * @param newHeight
     * @return
     */
    private Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        //如果宽度为0 保持原图
        if(newWidth == 0){
            newWidth = width;
            newHeight = height;
        }
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    private DisplayImageOptions getSmilesDisplayImageOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.trans)
                .showImageForEmptyUri(R.mipmap.trans)
                .showImageOnFail(R.mipmap.trans)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true).build();
        return options;
    }


}
