package com.zhicai.byteera.activity.community.dynamic.interfaceview;

import android.app.Activity;

import java.util.List;

/** Created by bing on 2015/6/29. */
public interface OrganizationHomeIV {
    Activity getContext();

    void setHead(String headPortrait);

    void setFansCount(String fansCount);

    void setMoneyNum(String num);

    void setPeopleNum(String num);

    void setHeadUnFocus();

    void setHeadIsFocus();

    void setDynamicButtomChecked();

    void setProductButtomChecked();

    void setData(List list);

    void addAllItem(List list);

    void loadComplete();

    void removeAllViews();

    void setTitleName(String institutionName);

    String getFansCount();

}
