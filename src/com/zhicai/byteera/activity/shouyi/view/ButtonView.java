package com.zhicai.byteera.activity.shouyi.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.zhicai.byteera.R;


public class ButtonView extends FrameLayout implements View.OnClickListener {
    private Context mContext;


    public interface ButtonViewCheckedListener {
        void checkedIncome();
        void checkedBid();
    }

    private ButtonViewCheckedListener listener;

    public void setButtonViewCheckedListener(ButtonViewCheckedListener listener) {
        this.listener = listener;
    }

    public static final int INCOME = 0;
    public static final int BID = 1;
    private TextView tvIncome;
    private TextView tvBid;

    public ButtonView(Context context) {
        this(context, null);
    }

    public ButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.shouyi_button_view, this, true);
        tvIncome = (TextView) this.findViewById(R.id.tv_income);
        tvBid = (TextView) findViewById(R.id.tv_bid);
        tvIncome.setOnClickListener(this);
        tvBid.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_income:
                select(0);
                listener.checkedIncome();
                break;
            case R.id.tv_bid:
                select(1);
                listener.checkedBid();
                break;
        }
    }

    public void select(int type) {
        switch (type) {
            case INCOME:
                tvIncome.setBackgroundResource(R.drawable.shouyi_buttonview_unchecked_left);
                tvBid.setBackgroundResource(R.drawable.shouyi_buttonview_checked_right);
                tvBid.setTextColor(getResources().getColor(R.color.white));
                tvIncome.setTextColor(getResources().getColor(R.color.head_view_bg));
                break;
            case BID:
                tvIncome.setBackgroundResource(R.drawable.shouyi_buttonview_checked_left);
                tvBid.setBackgroundResource(R.drawable.shouyi_buttonview_unchecked_right);
                tvIncome.setTextColor(getResources().getColor(R.color.white));
                tvBid.setTextColor(getResources().getColor(R.color.head_view_bg));
                break;
        }
    }
}
