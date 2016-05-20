package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.classroom.view.BaseHolder;

/**
 * Created by bing on 2015/5/21.
 */
public class ClickToLoadMoreView extends BaseHolder {

    private RelativeLayout clickView;
    private TextView clickText;
    private RelativeLayout loadView;

    public enum State {
        IDLE, LOAD, ERROR
    }

    public ClickToLoadMoreView(Context context) {
        super(context);
    }

    public void setLoadState(State state) {
        switch (state) {
            case IDLE:
                clickView.setVisibility(View.VISIBLE);
                clickText.setText("点击加载更多");
                loadView.setVisibility(View.INVISIBLE);
                break;
            case LOAD:
                clickView.setVisibility(View.INVISIBLE);
                loadView.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                clickView.setVisibility(View.VISIBLE);
                clickText.setText("加载失败，请重试");
                loadView.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public interface LoadMoreListener {
        void loadingMore();
    }

    public void setLoadMoreListener(LoadMoreListener listener) {
        this.moreListener = listener;
    }

    private LoadMoreListener moreListener;

    @Override
    protected void initEvent() {
        clickView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreListener != null&&clickView.getVisibility() == View.VISIBLE) {
                    moreListener.loadingMore();
                }
            }
        });
    }

    @Override
    protected View initView() {
        View inflate = LayoutInflater.from(context).inflate(R.layout.list_more_loading, null);
        clickView = (RelativeLayout) inflate.findViewById(R.id.rl_more_error);
        loadView = (RelativeLayout) inflate.findViewById(R.id.rl_more_loading);
        clickView.setVisibility(View.VISIBLE);
        loadView.setVisibility(View.INVISIBLE);
        clickText = (TextView) inflate.findViewById(R.id.loading_error_txt);
        clickText.setText("点击加载更多");
        return inflate;
    }

    @Override
    public void refreshView() {

    }
}
