package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;


/**
 * Created by bing on 2015/4/27.
 */
public class ShoppingButtonView extends FrameLayout implements View.OnClickListener {
    private Context mContext;


    public interface ButtonViewCheckedListener {
        void checkedXuNi();
        void checkedShiWu();
    }

    private ButtonViewCheckedListener listener;

    public void setButtonViewCheckedListener(ButtonViewCheckedListener listener) {
        this.listener = listener;
    }

    public static final int XUNI = 0;
    public static final int SHIWU = 1;
    private TextView tvXuNi;
    private TextView tvShiWu;

    public ShoppingButtonView(Context context) {
        this(context, null);
    }

    public ShoppingButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShoppingButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.shopping_button_view, this, true);
        tvXuNi = (TextView) this.findViewById(R.id.tv_xuni);
        tvShiWu = (TextView) findViewById(R.id.tv_shiwu);
        tvXuNi.setOnClickListener(this);
        tvShiWu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_xuni:
                select(0);
                listener.checkedXuNi();
                break;
            case R.id.tv_shiwu:
                select(1);
                listener.checkedShiWu();
                break;
        }
    }

    public void select(int type) {
        switch (type) {
            case XUNI:
                tvXuNi.setBackgroundResource(R.drawable.shoppingbutton_view_background_checked_left);
                tvShiWu.setBackgroundResource(R.drawable.shoppingbutton_view_background_unchecked_right);

                tvXuNi.setTextColor(getResources().getColor(R.color.white));
                tvShiWu.setTextColor(Color.parseColor("#646464"));
                break;
            case SHIWU:
                tvXuNi.setBackgroundResource(R.drawable.shoppingbutton_view_background_unchecked_left);
                tvShiWu.setBackgroundResource(R.drawable.shoppingbutton_view_background_checked_right);

                tvShiWu.setTextColor(getResources().getColor(R.color.white));
                tvXuNi.setTextColor(Color.parseColor("#646464"));
                break;
        }
    }
}
