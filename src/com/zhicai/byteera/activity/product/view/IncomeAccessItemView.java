package com.zhicai.byteera.activity.product.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;

/**
 * Created by bing on 2015/4/29.
 */
public class IncomeAccessItemView extends FrameLayout {
    private Context mContext;
    public IncomeAccessItemView(Context context) {
        this(context,null);
    }

    public IncomeAccessItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IncomeAccessItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.IncomeAccessItemView);
        String leftText = attributes.getString(R.styleable.IncomeAccessItemView_leftText);
        LayoutInflater.from(mContext).inflate(R.layout.income_access_item, this, true);
        ((TextView)this.findViewById(R.id.risk_control)).setText(leftText);
    }
}
