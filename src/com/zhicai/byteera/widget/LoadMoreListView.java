package com.zhicai.byteera.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zhicai.byteera.MyApp;

/** Created by bing on 2015/6/11. */
public class LoadMoreListView extends ListView implements AbsListView.OnScrollListener {

    private ZhiCaiFootView footView;
    private int lastVisibleItem;
    private int totalItemCount;
    private boolean isLoading;

    public interface LoadMoreDataListener {
        void loadMore();
    }

    private LoadMoreDataListener loadMoreDataListener;

    public void setLoadMoreDataListener(LoadMoreDataListener loadMoreDataListener) {
        this.loadMoreDataListener = loadMoreDataListener;
    }

    public LoadMoreListView(Context context) {
        this(context, null);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        footView = new ZhiCaiFootView(getContext());
        this.addFooterView(footView);
        this.setFooterDividersEnabled(false);
        this.setOnScrollListener(this);
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
            footView.setVisibility(View.GONE);
        }
    }


    public void loadComplete() {
        isLoading = false;
        MyApp.getMainThreadHandler().post(new Runnable() {
            @Override public void run() {
                footView.setLoadComplete();
            }
        });
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
