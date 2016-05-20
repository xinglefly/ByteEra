package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.zhicai.byteera.R;

/** Created by bing on 2015/4/21. */
public class TribetionHomeHeadView extends FrameLayout {
    private Context mContext;

    public TribetionHomeHeadView(Context context) {
        this(context, null);
    }

    public TribetionHomeHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TribetionHomeHeadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {

        LayoutInflater.from(mContext).inflate(R.layout.tribe_home_head, this, true);
    }
}
