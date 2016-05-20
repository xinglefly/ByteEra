package com.zhicai.byteera.activity.myhome.interfaceview;

import android.app.Activity;
import android.graphics.Bitmap;

import com.zhicai.byteera.activity.bean.ZCUser;

/** Created by bing on 2015/7/1. */
public interface ModifyUserInfoActivityIV {
    Activity getContext();

    void setUserInfo(ZCUser userInfo);

    void setTextNickName(String dialogNickName);

    void setTextSex(String dialogSex);

    String getDialogBirtyday();

    void setTextBirthday(String dialogBirtyday);

    void setTextCard(String content);

    void setTextCity(String content);

    void setIcon(Bitmap photo);
}
