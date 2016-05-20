package com.zhicai.byteera.activity.community.topic.adapter;

import android.content.Context;

import com.zhicai.byteera.activity.community.classroom.view.BaseHolder;
import com.zhicai.byteera.activity.community.classroom.view.LieeberAdapter;
import com.zhicai.byteera.activity.community.topic.view.MoreTopicItemView;

/**
 * Created by bing on 2015/6/3.
 */
public class MoreTopicAdapter extends LieeberAdapter {
    private static final int LIST_TYPE = 1;

    public MoreTopicAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseHolder checkType(int position) {
        return new MoreTopicItemView(mContext);
    }

    @Override
    protected int getItemType(int position) {
        return LIST_TYPE;
    }

    @Override
    public int getItemTypeCount() {
        return 1;
    }
}
