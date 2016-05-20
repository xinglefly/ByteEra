package com.zhicai.byteera.activity.knowwealth.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.widget.ColoredRatingBar;
import com.zhicai.byteera.widget.ZhiCaiFootView;

/** Created by bing on 2015/6/15. */
public class KnowWealthDetailListView extends ListView implements AbsListView.OnScrollListener {
    private WebView webView;
    private ColoredRatingBar mRatingBar;
    private TextView tvScore;
    private TextView tvCommentNum;
    private int lastVisibleItem;
    private int totalItemCount;

    private boolean isLoading;
    private ZhiCaiFootView footView;

    public int getheaderHeight() {
        return webView.getMeasuredHeight();
    }

    public interface LoadMoreDataListener {
        void loadMore();
    }

    private LoadMoreDataListener loadMoreDataListener;

    public void setLoadMoreDataListener(LoadMoreDataListener loadMoreDataListener) {
        this.loadMoreDataListener = loadMoreDataListener;
    }

    public interface WebViewLoadCompleteListener {
        void loadComplete();
    }


    public void setWebViewLoadCompleteListener(String url, final WebViewLoadCompleteListener listener) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setTextZoom(95);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (listener != null) {
                    listener.loadComplete();
                }
            }
        });
        webView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void loadComplete() {
        isLoading = false;
        MyApp.getMainThreadHandler().post(new Runnable() {
            @Override public void run() {
                footView.setLoadComplete();
            }
        });

    }

    public KnowWealthDetailListView(Context context) {
        this(context, null);
    }

    public KnowWealthDetailListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (totalItemCount == lastVisibleItem && scrollState == SCROLL_STATE_IDLE) {
            if (!isLoading) {
                isLoading = true;
                footView.setLoadMore();
                // 加载更多
                if (loadMoreDataListener != null) {
                    loadMoreDataListener.loadMore();
                }
            }
        }
    }

    @Override public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
        if (totalItemCount <= visibleItemCount) {
            footView.setLoadComplete();
        }
    }

    private void initView() {
        footView = new ZhiCaiFootView(getContext());
        this.addFooterView(footView);
        this.setOnScrollListener(this);
        this.setHeaderDividersEnabled(false);
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.know_wealth_detail_header_view, null);
        this.addHeaderView(headView);
        webView = (WebView) findViewById(R.id.web_view);
        mRatingBar = (ColoredRatingBar) findViewById(R.id.rating_bar);
        tvScore = (TextView) findViewById(R.id.tv_scroe);
        tvCommentNum = (TextView) findViewById(R.id.comment_num);
    }


    public void setRatingBar() {
        mRatingBar.setRating(4);
        mRatingBar.setOnRatingBarChangeListener(new ColoredRatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(ColoredRatingBar ratingBar, float rating, boolean fromUser) {
                tvScore.setText(String.format("%d分", (int) rating));
            }
        });
    }

    public void setComment(int commentCount) {
        tvCommentNum.setText(String.format("%d条", commentCount));
    }

    public void loadError() {
        isLoading = false;
        MyApp.getMainThreadHandler().post(new Runnable() {
            @Override public void run() {
                footView.setLoadError();
            }
        });
    }
}
