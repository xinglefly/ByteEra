package com.zhicai.byteera.activity.community.dynamic.interfaceview;

import android.app.Activity;

/** Created by bing on 2015/6/30. */
public interface PublishDynamicActivityIV {
    Activity getContext();

    void setSendEnabled(boolean b);

    String getContent();

    void showPushDialog();

    void showPushSuccessDialog();

    void showPushFialeDialog();

    void hidePushDialog();

    void isShowSlectionOptions(boolean isSelected);
}
