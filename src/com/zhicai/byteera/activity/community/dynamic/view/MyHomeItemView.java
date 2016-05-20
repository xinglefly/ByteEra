package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;

import butterknife.ButterKnife;


public class MyHomeItemView extends RelativeLayout {

    private Context mContext;
    private TextView rightText1;

    public MyHomeItemView(Context context) {
        this(context, null);
    }

    public MyHomeItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHomeItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MyHomeItem);
        Drawable leftDrawable = attributes.getDrawable(R.styleable.MyHomeItem_leftImg);
        String middleText = attributes.getString(R.styleable.MyHomeItem_middleText);
        String rightText = attributes.getString(R.styleable.MyHomeItem_rightText);

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.my_home_item, this, true);
        ImageView leftImg = ButterKnife.findById(inflate, R.id.iv_left);
        TextView middleText1 = ButterKnife.findById(inflate,R.id.tv_middle);
        rightText1 = ButterKnife.findById(inflate,R.id.tv_right);

        View view = new View(mContext);
        view.setBackgroundColor(getResources().getColor(R.color.line));
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
        view.setLayoutParams(params);
        this.addView(view);

        leftImg.setImageDrawable(leftDrawable);
        middleText1.setText(middleText);
        rightText1.setText(rightText);
    }

    public void setContent(String content){
        rightText1.setText(content);
    }
}
