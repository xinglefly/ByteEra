package com.zhicai.byteera.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;

import butterknife.ButterKnife;

/** Created by bing on 2015/6/9. */
public class ZhiCaiFootView extends FrameLayout {

    private RelativeLayout rlMoreLoading;
    private RelativeLayout rlError;
    private TextView errorText;

    public ZhiCaiFootView(Context context) {
        this(context, null);
    }

    public ZhiCaiFootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_more_loading, this, true);
        rlMoreLoading = ButterKnife.findById(view,R.id.rl_more_loading);
        rlError = ButterKnife.findById(view,R.id.rl_more_error);
        errorText = ButterKnife.findById(view,R.id.loading_error_txt);
        rlMoreLoading.setEnabled(false);
    }
    public void setLoadMore(){
        this.setVisibility(View.VISIBLE);
        rlMoreLoading.setVisibility(View.VISIBLE);
        rlError.setVisibility(View.GONE);
    }
    public void setLoadComplete(){
        this.setVisibility(View.VISIBLE);
        rlMoreLoading.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
        errorText.setText("加载完成");
    }
    public void setLoadError(){
        this.setVisibility(View.VISIBLE);
        rlMoreLoading.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
        errorText.setText("加载失败");
    }
}
