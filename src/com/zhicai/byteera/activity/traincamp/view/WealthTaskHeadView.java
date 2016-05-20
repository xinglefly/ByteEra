package com.zhicai.byteera.activity.traincamp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.zhicai.byteera.R;

/**
 * Created by bing on 2015/4/27.
 */
public class WealthTaskHeadView extends FrameLayout {
    public static final String TAG = WealthTaskHeadView.class.getSimpleName();

    private Context mContext;
    public WealthTaskHeadView(Context context) {
        super(context,null);
    }

    public WealthTaskHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WealthTaskHeadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;

        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.wealth_task_head_view,this,true);
    }
}
