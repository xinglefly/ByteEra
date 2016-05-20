package com.zhicai.byteera.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.Constants;

public class LoadingPage extends FrameLayout {

    public LoadingPage(Context context) {
        this(context, null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflater = LayoutInflater.from(context);
        this.bringToFront();
        init();
    }

    public interface RetryClickListener {
        void retryClick();
    }

    private RetryClickListener mListener;

    public void setOnRetryClickListener(RetryClickListener listener) {
        this.mListener = listener;
    }

    private View mLoadingView;//加载时显示的View
    private View mErrorView;//加载出错显示的View
    private View mEmptyView;//加载没有数据显示的View

    private LayoutInflater inflater;


    private void init() {
        setBackgroundColor(getResources().getColor(R.color.bg_page));//设置背景
        //创建对应的View，并添加到布局中
        mLoadingView = createLoadingView();
        addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        mErrorView = createErrorView();
        addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        mEmptyView = createEmptyView();
        addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * 显示对应的View
     */
    public void showPage(int mState) {
        this.setVisibility(View.VISIBLE);
        if (null != mLoadingView) mLoadingView.setVisibility(mState == Constants.STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        if (null != mErrorView) mErrorView.setVisibility(mState == Constants.STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        if (null != mEmptyView) mEmptyView.setVisibility(mState == Constants.STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        this.bringToFront();
    }

    protected View createLoadingView() {
        return inflater.inflate(R.layout.loading_page_loading, null);
    }

    protected View createEmptyView() {
        return inflater.inflate(R.layout.loading_page_empty, null);
    }

    protected View createErrorView() {
        View view = inflater.inflate(R.layout.loading_page_error, null);
        view.findViewById(R.id.page_bt).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage(Constants.STATE_LOADING);
                if (mListener != null) mListener.retryClick();
            }
        });
        return view;
    }

    public void hidePage() {
        this.setVisibility(View.GONE);
    }
}
