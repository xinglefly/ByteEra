package com.zhicai.byteera.activity.myhome.interfaceview;

import android.app.Activity;

import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;

import java.util.List;

/** Created by bing on 2015/6/29. */
public interface MyDynamicActivityIV {
    Activity getContext();

    void setData(List<DynamicEntity> dynamicItemList);
}
