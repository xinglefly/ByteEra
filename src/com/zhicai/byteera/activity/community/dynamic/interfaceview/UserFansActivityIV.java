package com.zhicai.byteera.activity.community.dynamic.interfaceview;

import android.app.Activity;

import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;

import java.util.List;

/** Created by bing on 2015/6/30. */
public interface UserFansActivityIV {

    Activity getContext();

    void showEmpty();

    void goneEmpty();

    void setData(List<UserInfoEntity> userInfoEntities);
}
