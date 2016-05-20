package com.zhicai.byteera.activity.knowwealth.viewinterface;

import android.content.Context;

/** Created by bing on 2015/6/27. */
public interface KnowWealthIV {
    Context getContext();

    void removeAllViews();

    void addItem(Object item);

    void loadComplete();

    void refreshFinish();

    void notifyDataSetChanged();

    Object getItem(int position);

}
