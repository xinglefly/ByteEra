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

/**
 * Created by bing on 2015/4/21.
 */
public class GroupHomeHeadView extends FrameLayout {
    private Context mContext;
    private ImageView ivHead;
    private TextView tvDes;
    private TextView tvFocus;
    private TextView tvFansNum;
    private TextView tvFans;

    public GroupHomeHeadView(Context context) {
        this(context, null);
    }

    public GroupHomeHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupHomeHeadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }


    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.group_home_head_new, this, true);
        ivHead = (ImageView) findViewById(R.id.iv_head);
        tvDes = (TextView) findViewById(R.id.tv_des);
        tvFocus = (TextView) findViewById(R.id.tv_focus);
        tvFansNum = (TextView) findViewById(R.id.tv_fans_num);
        tvFans = (TextView) findViewById(R.id.tv_fans);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            findViewById(R.id.top_head).setVisibility(View.VISIBLE);
        }
    }

    public void setHead(String url) {
        ImageLoader.getInstance().displayImage(url, ivHead);
    }

    public TextView getDoFocusText() {
        return tvFocus;
    }

    public TextView getDoFansText() {
        return tvFans;
    }

    public void setFansNum(String num) {
        tvFansNum.setText(num);
    }

    public String getFansNum() {
        return tvFansNum.getText().toString();
    }
}
