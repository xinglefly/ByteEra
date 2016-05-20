package com.zhicai.byteera.activity.community.dynamic.interfaceview;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.util.List;

/** Created by bing on 2015/6/29. */
public interface DynamicFragmentIV<Data> {
    Activity getContext();

    void addListViewItem(Data data);

    void addAllItem(List<Data> dataList);

    void notifyDataSetChanged();

    void hidePage();


    void refreshFinish();

    void removeAllViews();

    void addItem(Data data);

    void loadComplete();

    Data getItem(int position);

    void removeItemAtPosition(int position);

    Fragment getFragment();

    void notifyDataInvidated();
}
