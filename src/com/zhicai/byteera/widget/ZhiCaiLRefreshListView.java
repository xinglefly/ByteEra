package com.zhicai.byteera.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

/** Created by bing on 2015/6/9. */
public class ZhiCaiLRefreshListView extends SwipeRefreshLayout {
    private int lastVisibleItem;
    private int totalItemCount;
    private boolean isLoading;

    private boolean isShort;

    public interface LoadMoreDataListener {
        void loadMore();
    }

    private LoadMoreDataListener loadMoreDataListener;

    public void setLoadMoreDataListener(LoadMoreDataListener loadMoreDataListener) {
        this.loadMoreDataListener = loadMoreDataListener;
    }

    public interface OnScrollListener {
        void onScrollStateChanged(AbsListView view, int scrollState);

        void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }

    private OnScrollListener onScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    private ListView listView;
    private ZhiCaiFootView footView;

    public ZhiCaiLRefreshListView(Context context) {
        super(context);
        init();
    }

    public ZhiCaiLRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        this.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.white,
                android.R.color.holo_red_light);
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.list_view, null);
        this.addView(view);
        listView = (ListView) view.findViewById(R.id.list_view);
        footView = new ZhiCaiFootView(getContext());
        listView.addFooterView(footView);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
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
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
                ZhiCaiLRefreshListView.this.lastVisibleItem = firstVisibleItem + visibleItemCount;
                ZhiCaiLRefreshListView.this.totalItemCount = totalItemCount;
                if (totalItemCount <= visibleItemCount) {
                    footView.setVisibility(View.GONE);
                    isShort = true;
                }
            }
        });
    }


    public ListView getListView() {
        return listView;
    }

    public void refreshFinish() {
        MyApp.getMainThreadHandler().post(new Runnable() {
            @Override public void run() {
                ZhiCaiLRefreshListView.this.setRefreshing(false);
            }
        });
    }

    public void loadComplete() {
        isLoading = false;
        MyApp.getMainThreadHandler().post(new Runnable() {
            @Override public void run() {
//                if (isShort) {
////                    footView.setVisibility(View.GONE);
//                } else {
//                    footView.setLoadComplete();
//                }
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

    public void setDivider(Drawable drawable) {
        listView.setDivider(drawable);
    }
}
