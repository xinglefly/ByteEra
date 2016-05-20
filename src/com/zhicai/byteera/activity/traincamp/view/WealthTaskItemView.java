package com.zhicai.byteera.activity.traincamp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;

/**
 * Created by bing on 2015/4/27.
 */
public class WealthTaskItemView extends FrameLayout{
    private Context mContext;
    private boolean flag;

    public interface  OnRightButtomClickListener{
        void onRightClick(boolean flag);
    }
    private OnRightButtomClickListener rightButtomClickListener;

    public void setRightButtomClickListener(OnRightButtomClickListener rightButtomClickListener) {
        this.rightButtomClickListener = rightButtomClickListener;
    }

    public WealthTaskItemView(Context context) {
        this(context, null);
    }

    public WealthTaskItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WealthTaskItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.WealthTaskItem);
        int buttonColor = attributes.getColor(R.styleable.WealthTaskItem_ButtonColor, getResources().getColor(R.color.orange));
        Drawable buttonImage = attributes.getDrawable(R.styleable.WealthTaskItem_ButtonImage);
        String leftText = attributes.getString(R.styleable.WealthTaskItem_leftText);
        String buttonText = attributes.getString(R.styleable.WealthTaskItem_ButtonText);

        LayoutInflater.from(mContext).inflate(R.layout.wealth_task_item, this, true);
        ((TextView) this.findViewById(R.id.tv_login)).setText(leftText);
        ((TextView) this.findViewById(R.id.tv_get)).setText(buttonText);
        ((ImageView) this.findViewById(R.id.iv_coin)).setImageDrawable(buttonImage);
        RelativeLayout rel_button =  (RelativeLayout)this.findViewById(R.id.rl_buton);
        rel_button.setBackgroundColor(buttonColor);
        rel_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                rightButtomClickListener.onRightClick(flag);
            }
        });

    }

}
