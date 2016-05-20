package com.zhicai.byteera.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/** Created by bing on 2015/4/21. */
public class MyScrollView extends ScrollView {
    public interface ScrollViewListener {
        void onScrollChanged(View view, int x, int y, int oldx, int oldy);
    }

    private ScrollBottomListener scrollBottomListener;

    public void setScrollBottomListener(ScrollBottomListener scrollBottomListener) {
        this.scrollBottomListener = scrollBottomListener;
    }

    public interface ScrollBottomListener {
         void scrollBottom();
    }

    private ScrollViewListener scrollViewListener;

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
        if (y + getHeight() >= computeVerticalScrollRange()) {
            //ScrollView滑动到底部了
            if (scrollBottomListener != null) {
                scrollBottomListener.scrollBottom();
            }
        }
    }
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }
}
