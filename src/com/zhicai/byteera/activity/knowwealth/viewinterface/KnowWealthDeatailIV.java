package com.zhicai.byteera.activity.knowwealth.viewinterface;

import android.app.Activity;

import com.zhicai.byteera.activity.community.dynamic.entity.DynamicCommentEntity;
import com.zhicai.byteera.service.information.InformationSecondary;

import java.util.List;

/** Created by bing on 2015/6/27. */
public interface KnowWealthDeatailIV {
    Activity getContext();

    void showErrorPage();

    void setComment(int commentCount);

    void freshListView(List<DynamicCommentEntity> commentList);

    void initListView(InformationSecondary.DetailsPage page, List<DynamicCommentEntity> commentItemEntities);


    int getCommentIndex(int position);

    void removeItemAtPosition(int position);

    void loadMoreListView(List<DynamicCommentEntity> commentItemEntities);

    void loadComplete();

    void loadError();

    boolean startLoginActivity();

    void changeCollectionButomUnCollect();

    void changeCollectionButomCollect();

    void dismissShareDialog();
}
