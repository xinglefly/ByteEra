package com.zhicai.byteera.activity.community.classroom.view;

import android.content.Context;
import android.view.View;

/** Created by bing on 2015/4/16. */
public abstract class BaseHolder<Data> {
    protected View mRootView;
    protected int mPosition;
    protected Data mData;
    protected Context context;

    public BaseHolder(Context context) {
        this.context = context;
        mRootView = initView();
        mRootView.setTag(this);
        initEvent();
    }

    public BaseHolder(Context context, Data mData) {
        this.context = context;
        mRootView = initView();
        mRootView.setTag(this);
        initEvent();
        setData(mData);
    }

    protected abstract void initEvent();

    protected abstract View initView();

    public View getRootView() {
        return mRootView;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data mData) {
        this.mData = mData;
        refreshView();
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }


    public abstract void refreshView();


    public void recycle() {

    }
}
