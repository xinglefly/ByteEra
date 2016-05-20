package com.zhicai.byteera.activity.community.dynamic.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.activity.UserFansActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.UserFocusActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;

/** Created by bing on 2015/4/21. */
public class UserHomeHeadView extends FrameLayout implements OnClickListener {
    private Context mContext;
    private TextView tvFansNum;
    private TextView tvFocusNum;
    private TextView tvCoinNum;
    private ImageView ivHead;
    private ImageView ivDoFocus;
    //  private ImageView ivLiuyan;
    private String userId;


    public UserHomeHeadView(Context context) {
        this(context, null);
    }

    public UserHomeHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserHomeHeadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.user_home_head, this, true);

        tvFansNum = (TextView) findViewById(R.id.tv_fan_num);
        tvFocusNum = (TextView) findViewById(R.id.tv_focus_num);
        tvCoinNum = (TextView) findViewById(R.id.tv_coin_num);
        ivHead = (ImageView) findViewById(R.id.iv_head);
        ivDoFocus = (ImageView) findViewById(R.id.iv_do_focus);

        //ivLiuyan = (ImageView) findViewById(R.id.iv_liuyan);
        ((View) tvFansNum.getParent()).setOnClickListener(this);
        ((View) tvFocusNum.getParent()).setOnClickListener(this);
        ((View) tvCoinNum.getParent()).setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            findViewById(R.id.top_head).setVisibility(View.VISIBLE);
        }
    }

    public void setFansNum(String num) {
        tvFansNum.setText(num);
    }

    public void setCoinNum(String num) {
        tvCoinNum.setText(num);
    }

    public void setFocusNum(String num) {
        tvFocusNum.setText(num);
    }

    public void setIheadImage(String url) {
        ImageLoader.getInstance().displayImage(url, ivHead);
    }


    public ImageView getDoFocusView() {
        return ivDoFocus;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_fans:
                intentToUserFansActivity();
                break;
            case R.id.rl_focus:
                intentToUserFocusActivity();
                break;
            case R.id.rl_caibi:
                intentToUserCaibiActivity();
                break;
        }
    }

    private void intentToUserCaibiActivity() {

    }

    private void intentToUserFocusActivity() {
        Intent intent = new Intent(mContext, UserFocusActivity.class);
        intent.putExtra("user_id", userId);
        ActivityUtil.startActivity((Activity) mContext, intent);
    }

    private void intentToUserFansActivity() {
        Intent intent = new Intent(mContext, UserFansActivity.class);
        intent.putExtra("user_id", userId);
        ActivityUtil.startActivity((Activity) mContext, intent);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
