package com.zhicai.byteera.activity.myhome.interfaceview;

import android.app.Activity;

import com.zhicai.byteera.service.information.InformationSecondary;

import java.util.List;

/** Created by bing on 2015/6/29. */
public interface MyShoucangActivityIV {
    Activity getContext();

    void setData(List<InformationSecondary.TZixun> zixunList);
}
