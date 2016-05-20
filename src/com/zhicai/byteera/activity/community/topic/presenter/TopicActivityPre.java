package com.zhicai.byteera.activity.community.topic.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.community.topic.actiivty.TopicDetailActivity;
import com.zhicai.byteera.activity.community.topic.entity.TopicEntity;
import com.zhicai.byteera.activity.community.topic.viewinterface.TopicFragmentIV;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.service.topic.Topic;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/6/28. */
public class TopicActivityPre {
    private TopicFragmentIV topicFragmentIV;
    private Context mContext;
    private int totalNum;
    private List<TopicEntity> topicEntities;

    public TopicActivityPre(TopicFragmentIV topicFragmentIV) {
        this.topicFragmentIV = topicFragmentIV;
        mContext = topicFragmentIV.getContext();
    }


    public void intentToTopicDetailActivity(int position) {
        Intent intent = new Intent(mContext, TopicDetailActivity.class);
        intent.putParcelableArrayListExtra("topicList", (ArrayList<? extends Parcelable>) topicEntities);
        intent.putExtra("totalNum", totalNum);
        intent.putExtra("position", topicEntities.get(position).getTopicNum());
        ActivityUtil.startActivity((Activity) mContext, intent);
    }

    public void getDataFromNet(boolean flag) {
        final Topic.GetAllTopicReq req = Topic.GetAllTopicReq.newBuilder().build();
        TiangongClient.instance().send("chronos", "get_all_topic", req.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final Topic.GetAllTopicResponse response = Topic.GetAllTopicResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override public void run() {
                            topicFragmentIV.refreshFinish();
                            if (response.getErrorno() == 0) {
                                topicEntities = ModelParseUtil.TopicEntityParse(response.getItemList());
                                totalNum = response.getTotalNum();
                                topicFragmentIV.freshListView(topicEntities);
                                topicFragmentIV.loadComplete();
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
