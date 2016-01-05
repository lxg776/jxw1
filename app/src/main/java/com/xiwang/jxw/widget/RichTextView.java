package com.xiwang.jxw.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.text.Html;
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
import com.xiwang.jxw.util.CommonUtil;
import com.xiwang.jxw.util.DisplayUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.widget.gif.GifAnimationDrawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
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
                               // bitmap=zoomImage(bitmap, DisplayUtil.dip2px(mContext,24),DisplayUtil.dip2px(mContext,24));
                                GifAnimationDrawable gifAnimationDrawable=new GifAnimationDrawable(bitmap2InputStream(bitmap));
                                ImageSpan imageSpan=new ImageSpan(gifAnimationDrawable);
                                spannableStringBuilder.setSpan(imageSpan,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }catch (IOException e){

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
        bm.compress(Bitmap.CompressFormat.WEBP, 100, baos);
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
