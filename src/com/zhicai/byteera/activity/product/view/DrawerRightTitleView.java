package com.zhicai.byteera.activity.product.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;

/** Created by bing on 2015/4/30. */
public class DrawerRightTitleView extends FrameLayout implements View.OnClickListener {
    private RelativeLayout rlIncome;
    private RelativeLayout rlPeriod;
    private RelativeLayout rlIndex;
    private ImageView rightJiantou;
    private ImageView leftJiantou;
    private ImageView middleJiantou;

    private boolean isRightUp = false;
    private boolean isMiddleUp = false;
    private boolean isLeftUp = false;

    public interface DrawerTitleListener {
        void clickLeft();

        void clickMiddle();

        void clickRight();

    }

    private DrawerTitleListener mListener;

    public void setDrawerTitleListener(DrawerTitleListener listener) {
        this.mListener = listener;
    }

    private Context mContext;
    private static final int INCOME = 0;
    private static final int PERIOD = 1;
    private static final int INDEX = 2;
    private TextView tvIncome;
    private TextView tvPeriod;
    private TextView tvIndex;

    public DrawerRightTitleView(Context context) {
        this(context, null);
    }

    public DrawerRightTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerRightTitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.drawer_right_title_view, this, true);
        tvIncome = (TextView) this.findViewById(R.id.tv_income);
        tvPeriod = (TextView) this.findViewById(R.id.tv_period);
        tvIndex = (TextView) this.findViewById(R.id.tv_index);
        rightJiantou = (ImageView) findViewById(R.id.right_jiantou);
        leftJiantou = (ImageView) findViewById(R.id.left_jiantou);
        middleJiantou = (ImageView) findViewById(R.id.middle_jiantou);
        rlIncome = (RelativeLayout) this.findViewById(R.id.rl_income);
        rlPeriod = (RelativeLayout) this.findViewById(R.id.rl_period);
        rlIndex = (RelativeLayout) this.findViewById(R.id.rl_index);
        rlIncome.setOnClickListener(this);
        rlPeriod.setOnClickListener(this);
        rlIndex.setOnClickListener(this);
    }

    public void select(int position) {
        resetbutton();
        switch (position) {
            case INCOME:
                isLeftUp = !isLeftUp;
                rlIncome.setBackgroundResource(R.drawable.income_background_shape_left_checked);
                tvIncome.setTextColor(getResources().getColor(R.color.white));
                leftJiantou.setImageResource(isLeftUp ? R.drawable.shang : R.drawable.xia);

                break;
            case PERIOD:
                isMiddleUp = !isMiddleUp;
                rlPeriod.setBackgroundResource(R.drawable.income_background_shape_mindum_checked);
                tvPeriod.setTextColor(getResources().getColor(R.color.white));
                middleJiantou.setImageResource(isMiddleUp ? R.drawable.shang : R.drawable.xia);

                break;
            case INDEX:
                isRightUp = !isRightUp;
                rlIndex.setBackgroundResource(R.drawable.income_background_shape_right_checked);
                tvIndex.setTextColor(getResources().getColor(R.color.white));
                rightJiantou.setImageResource(isRightUp ? R.drawable.shang : R.drawable.xia);

                break;
        }
    }

    private void resetbutton() {
        rlIncome.setBackgroundResource(R.drawable.income_background_shape_left_unchecked);
        tvIncome.setTextColor(getResources().getColor(R.color.head_view_bg));
        rlPeriod.setBackgroundResource(R.drawable.income_background_shape_mindum_unchecked);
        tvPeriod.setTextColor(getResources().getColor(R.color.head_view_bg));
        rlIndex.setBackgroundResource(R.drawable.income_background_shape_right_unchecked);
        tvIndex.setTextColor(getResources().getColor(R.color.head_view_bg));
    }

    @Override public void onClick(View v) {
        if (mListener != null) {
            switch (v.getId()) {
                case R.id.rl_income:
                    select(INCOME);
                    mListener.clickLeft();
                    break;
                case R.id.rl_period:
                    select(PERIOD);
                    mListener.clickMiddle();
                    break;
                case R.id.rl_index:
                    select(INDEX);
                    mListener.clickRight();
                    break;
            }
        }
    }
}
