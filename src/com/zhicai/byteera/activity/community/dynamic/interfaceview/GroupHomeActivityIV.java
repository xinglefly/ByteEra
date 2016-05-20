package com.zhicai.byteera.activity.community.dynamic.interfaceview;

import android.app.Activity;

import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;

import java.util.List;

/** Created by bing on 2015/6/29. */
public interface GroupHomeActivityIV {
    Activity getContext();

    void setData(List<DynamicEntity> dynamicEntities);

    void addAllItem(List<DynamicEntity> dynamicEntities);

    void loadComplete();

    void setTitleName(String name);

    void setAvatar(String avatarUrl);

    void setDescrition(String descrition);

    void setFansCount(int fansCount);

    void setJoined(boolean joined);
}
