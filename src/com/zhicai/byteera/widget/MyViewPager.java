package com.zhicai.byteera.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/** Created by lieeber on 15/8/3. */
public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }


    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
//        this.setOffscreenPageLimit(10); //一次加载所有界面
    }
}
