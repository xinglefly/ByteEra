package com.zhicai.byteera.activity.community.topic.viewinterface;

import android.content.Context;

import com.zhicai.byteera.activity.community.topic.entity.TopicEntity;

import java.util.List;

/** Created by bing on 2015/6/28. */
public interface TopicFragmentIV {

    Context getContext();

    void refreshFinish();

    void freshListView(List<TopicEntity> topicItemEntities);

    void loadComplete();
}
