package com.xiwang.jxw.widget;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.imageaware.ViewAware;
import com.nostra13.universalimageloader.utils.L;

import java.lang.reflect.Field;

/**
 * Created by liangxg on 2016/1/12.
 */
public class ZoomImageViewAware   extends ImageViewAware {
    ZoomImageView imageView;


    public ZoomImageViewAware(ZoomImageView view) {
        super(view);
        this.imageView=view;
    }

    public ZoomImageViewAware(ZoomImageView view, boolean checkActualViewSize) {
        super(view, checkActualViewSize);
        this.imageView=view;
    }

    /**
     * {@inheritDoc}
     * <br />
     * 3) Get <b>maxWidth</b>.
     */
    @Override
    public int getWidth() {
        int width = super.getWidth();
        if (width <= 0) {
            ZoomImageView imageView = (ZoomImageView) viewRef.get();
            if (imageView != null) {
                width = getImageViewFieldValue(imageView, "mMaxWidth"); // Check maxWidth parameter
            }
        }
        return width;
    }


    /**
     * {@inheritDoc}
     * <br />
     * 3) Get <b>maxHeight</b>
     */
    @Override
    public int getHeight() {
        int height = super.getHeight();
        if (height <= 0) {
            ZoomImageView imageView = (ZoomImageView) viewRef.get();
            if (imageView != null) {
                height = getImageViewFieldValue(imageView, "mMaxHeight"); // Check maxHeight parameter
            }
        }
        return height;
    }


    @Override
    public ViewScaleType getScaleType() {
        ZoomImageView imageView = (ZoomImageView) viewRef.get();
        if (imageView != null) {
            return ViewScaleType.fromImageView(imageView);
        }
        return super.getScaleType();
    }


    @Override
    public ZoomImageView getWrappedView() {
        return (ZoomImageView) super.getWrappedView();
    }


    @Override
    protected void setImageDrawableInto(Drawable drawable, View view) {
        ((ZoomImageView) view).setImageDrawable(drawable);
        if (drawable instanceof AnimationDrawable) {
            ((AnimationDrawable)drawable).start();
        }
    }



    @Override
    protected void setImageBitmapInto(Bitmap bitmap, View view) {
        ((ZoomImageView) view).setImageBitmap(bitmap);
    }

    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
            L.e(e);
        }
        return value;
    }
}
