package com.zhicai.byteera.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {


    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        //Typeface face = Typeface.createFromAsset(getContext().getAssets(), "ziti.TTF");
        //this.setTypeface(face);

    }
}
