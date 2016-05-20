package com.zhicai.byteera.activity.product.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.StringUtil;

/**
 * Created by bing on 2015/4/29.
 */
public class IncomeSurveyView extends FrameLayout {
    private Context mContext;
    public IncomeSurveyView(Context context) {
        this(context, null);
    }

    public IncomeSurveyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IncomeSurveyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.income_survey_view, this, true);
    }
}
