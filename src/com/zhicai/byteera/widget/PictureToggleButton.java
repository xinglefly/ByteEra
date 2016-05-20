package com.zhicai.byteera.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ToggleButton;

public class PictureToggleButton extends ToggleButton {

    public PictureToggleButton(Context context) {
        super(context, null);
    }

    public PictureToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureToggleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getBackground();
        setMeasuredDimension(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

}
