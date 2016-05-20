package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhicai.byteera.R;

/** Created by bing on 2015/4/21. */
public class UserHomeTitleView extends FrameLayout {
    private Context mContext;
    private TextView tvTitle;
    private ImageView leftImg;
    private TextView tvRight;
    private View rlChange;
    private ImageView ivRight;

    public UserHomeTitleView(Context context) {
        this(context, null);
    }

    public UserHomeTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserHomeTitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.UserHomeTitleView);
        Drawable leftDrawable = attributes.getDrawable(R.styleable.UserHomeTitleView_leftImg);
        Drawable rightDrawable = attributes.getDrawable(R.styleable.UserHomeTitleView_rightImg);
        String rightText = attributes.getString(R.styleable.UserHomeTitleView_rightText);
        String tvText = attributes.getString(R.styleable.UserHomeTitleView_titleName);

        LayoutInflater.from(mContext).inflate(R.layout.user_home_title, this, true);
        leftImg = (ImageView) this.findViewById(R.id.iv_left);
        tvRight = (TextView) this.findViewById(R.id.tv_right);
        tvTitle = (TextView) this.findViewById(R.id.tv_title);
        ivRight = (ImageView) this.findViewById(R.id.iv_right);
        View topHead = findViewById(R.id.top_head);
        rlChange = findViewById(R.id.rl_change);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topHead.setVisibility(View.VISIBLE);
        }

        leftImg.setImageDrawable(leftDrawable);
        ivRight.setImageDrawable(rightDrawable);
        tvTitle.setText(tvText);
        tvRight.setText(rightText);
        attributes.recycle();
    }

    public void setTitle(String name) {
        tvTitle.setText(name);
    }

    public ImageView getLeftImg() {
        return leftImg;
    }

    public TextView getRightText() {
        return tvRight;
    }

    public ImageView getRightImg() {
        return ivRight;
    }

    public void setTitleBackgroundColor(int color) {
        rlChange.setBackgroundColor(color);
    }

    public void setUserName(String nickname) {
        tvTitle.setText(nickname);
    }

    public void setTextAlpha(float alpha) {
//        leftImg.setAlpha(alpha);
//        ivRight.setAlpha(alpha);
//        tvRight.setAlpha(alpha);
        tvTitle.setAlpha(alpha);
    }
}
