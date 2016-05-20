package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;

import butterknife.ButterKnife;

/** Created by bing on 2015/4/21. */
public class OrganizationHomeHeadView extends FrameLayout {
    private Context mContext;
    private ImageView ivHead;
    private TextView tvMoneyNum;
    private TextView tvInverstorNum;
    private TextView tvFansNum;
    private TextView tvFocus;


    public OrganizationHomeHeadView(Context context) {
        this(context, null);
    }

    public OrganizationHomeHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrganizationHomeHeadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.organzation_home_head, this, true);
        ivHead = ButterKnife.findById(view, R.id.iv_head);
        tvMoneyNum = ButterKnife.findById(view,R.id.tv_moneynum);
        tvInverstorNum = ButterKnife.findById(view,R.id.tv_inverstornum);
        tvFansNum = ButterKnife.findById(view,R.id.tv_fans_num);
        tvFocus = ButterKnife.findById(view,R.id.tv_focus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ButterKnife.findById(view, R.id.top_head).setVisibility(View.VISIBLE);
        }
    }

    public void setHead(String url) {
        ImageLoader.getInstance().displayImage(url, ivHead);
    }


    public TextView getDoFocusText() {
        return tvFocus;
    }


    public void setFansNum(String num) {
        tvFansNum.setText(num);
    }

    public String getFansNum() {
        return tvFansNum.getText().toString();
    }


}
