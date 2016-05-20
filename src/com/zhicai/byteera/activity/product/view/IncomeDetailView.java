package com.zhicai.byteera.activity.product.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.zhicai.byteera.R;

/**
 * Created by bing on 2015/4/29.
 */
public class IncomeDetailView extends FrameLayout {
    private Context mContext;
    public IncomeDetailView(Context context) {
        this(context, null);
    }

    public IncomeDetailView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IncomeDetailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.income_detail_view, this, true);
    }
}
