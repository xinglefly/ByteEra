package com.zhicai.byteera.activity.community.dynamic.interfaceview;

import android.app.Activity;

import java.util.List;

/** Created by bing on 2015/6/29. */
public interface UserHomeActivityIV {
    Activity getContext();

    void setCoinNum(String coinNum);

    void SetFansNum(String fansNum);

    void setFocusNum(String focusNum);

    void setHeadImage(String headPortrait);

    void setUserName(String nickname);

    void setUerId(String userId);

    void changeFocus(boolean isFocus);

    void setData(List list);

    void addAllItem(List list);

    void loadComplete();

    void removeAllViews();

    void notifyDataSetChanged();
}
