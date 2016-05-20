package com.zhicai.byteera.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.commonutil.UIUtils;

/** Created by bing on 2015/6/10. */
public class HasHeadLoadMoreListView extends ListView implements AbsListView.OnScrollListener {
    private ZhiCaiFootView footView;
    private int lastVisibleItem;
    private int totalItemCount;
    private boolean isLoading;
    private View header;


    public interface LoadMoreDataListener {
        void loadMore();
    }

    public interface ScrollYListener {
        void scrollY(float y);
    }

    private ScrollYListener scrollYListener;

    public void setScrollYListener(ScrollYListener listener) {
        this.scrollYListener = listener;
    }

    private LoadMoreDataListener loadMoreDataListener;

    public void setLoadMoreDataListener(LoadMoreDataListener loadMoreDataListener) {
        this.loadMoreDataListener = loadMoreDataListener;
    }

    public void setHeaderView(View view) {
        header = view;
        this.addHeaderView(view);
    }

    public HasHeadLoadMoreListView(Context context, AttributeSet att) {
        super(context, att);
        initView();
    }

    private void initView() {
        this.setFooterDividersEnabled(false);
        //添加脚布局
        footView = new ZhiCaiFootView(getContext());
        this.addFooterView(footView);
        this.setOnScrollListener(this);
    }


    public void loadComplete() {
        isLoading = false;
        MyApp.getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
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
        if (totalItemCount <= visibleItemCount) {   //如果高度不够一屏的话 是不会显示加载更多的
           // footView.setVisibility(View.GONE);
            footView.setLoadComplete();
        }
        //得到头部的显示高度的变化
        if (scrollYListener != null) {
            scrollYListener.scrollY(header.getY());
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        if (header != null) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                if (inViewInBounds(header, (int) ev.getRawX(), (int) ev.getRawY())) {
                    return false;
                } else {
                    if (getContext() instanceof Activity) {
                        UIUtils.hideKeyboard((Activity) getContext());
                    }
                }
            }
        }
        return super.onTouchEvent(ev);
    }

    private boolean inViewInBounds(View view, int x, int y) {
        Rect outRect = new Rect();
        int[] location = new int[2];
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
