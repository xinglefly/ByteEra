package com.zhicai.byteera.activity.traincamp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.zhicai.byteera.R;


/**
 * Created by bing on 2015/4/27.
 */
public class ButtonView extends FrameLayout implements View.OnClickListener {
    private Context mContext;


    public interface ButtonViewCheckedListener {
        void checkedDayTask();
        void checkedAchievementPrise();
    }

    private ButtonViewCheckedListener listener;

    public void setButtonViewCheckedListener(ButtonViewCheckedListener listener) {
        this.listener = listener;
    }

    public static final int DAY_TASK = 0;
    public static final int ACHIEVEMENT_PRISE = 1;
    private TextView tvDayTask;
    private TextView tvPrase;

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
        LayoutInflater.from(mContext).inflate(R.layout.button_view, this, true);
        tvDayTask = (TextView) this.findViewById(R.id.tv_day_task);
        tvPrase = (TextView) findViewById(R.id.tv_praise);
        tvDayTask.setOnClickListener(this);
        tvPrase.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_day_task:
                select(0);
                listener.checkedDayTask();
                break;
            case R.id.tv_praise:
                select(1);
                listener.checkedAchievementPrise();
                break;
        }
    }

    public void select(int type) {
        switch (type) {
            case DAY_TASK:
                tvDayTask.setBackgroundResource(R.drawable.button_view_background_checked_left);
                tvPrase.setBackgroundResource(R.drawable.button_view_background_unchecked_right);

                tvDayTask.setTextColor(getResources().getColor(R.color.white));
                tvPrase.setTextColor(getResources().getColor(R.color.button_view_backgrond));
                break;
            case ACHIEVEMENT_PRISE:
                tvDayTask.setBackgroundResource(R.drawable.button_view_background_unchecked_left);
                tvPrase.setBackgroundResource(R.drawable.button_view_background_checked_right);

                tvPrase.setTextColor(getResources().getColor(R.color.white));
                tvDayTask.setTextColor(getResources().getColor(R.color.button_view_backgrond));
                break;
        }
    }
}
