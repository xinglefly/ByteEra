package com.zhicai.byteera.activity.traincamp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

@SuppressWarnings("unused")
public class GuessGridView extends GridView {

    public GuessGridView(Context context) {
        super(context);
    }

    public GuessGridView(Context context, AttributeSet att) {
        super(context, att);
    }

    public GuessGridView(Context context, AttributeSet att, int defStyle) {
        super(context, att, defStyle);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


    //通过重新dispatchTouchEvent方法来禁止滑动
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //禁止Gridview进行滑动
        return ev.getAction() == MotionEvent.ACTION_MOVE || super.dispatchTouchEvent(ev);
    }

}
