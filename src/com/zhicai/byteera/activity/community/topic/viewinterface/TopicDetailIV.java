package com.zhicai.byteera.activity.community.topic.viewinterface;

import android.app.Activity;

import java.util.List;

/** Created by bing on 2015/6/28. */
public interface TopicDetailIV<Data> {
    Activity getContext();

    void refreshFinish();

    void setTitleButtonEnable(boolean b);

    void hidePage();

    void initListView();

    void setLeftText(String s);

    void setMiddleText(String s);

    void setRIghtText(String s);

    void removeAllViews();

    void addItem(Object item);

    void addAllItem(List<Data> items);

    void notifyDataSetChanged();

    boolean titleButtonIsEnabled();

    void showPage(int state);

    void openEditCommentView();

    void setComment(String topicId, String opinionId, String user_id, String toUserId, int position, String nickName);

    void setZaning(boolean zaning, int position);

    void setRightTextGone();

    void setLeftTextVisible();

    void setRightTextVisible();

    void setLeftTextGone();
}
