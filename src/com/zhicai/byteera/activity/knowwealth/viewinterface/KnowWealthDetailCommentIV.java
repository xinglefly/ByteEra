package com.zhicai.byteera.activity.knowwealth.viewinterface;

import android.app.Activity;

import com.zhicai.byteera.activity.community.dynamic.entity.DynamicCommentEntity;

import java.util.List;

/** Created by bing on 2015/6/27. */
public interface KnowWealthDetailCommentIV {
    Activity getContext();

    void showEmptyComment();

    void goneListView();

    void showListView();

    void goneEmptyComment();

    void refreshListView(List<DynamicCommentEntity> commentList);

    void toastEmptyComment();

    boolean startLoginActivity();

    void setHerViewRightTextViewEnabled(boolean b);

    void toastErrorComment();

    void setMoreList(List<DynamicCommentEntity> commentItemEntities);

    void loadComplete();
}
