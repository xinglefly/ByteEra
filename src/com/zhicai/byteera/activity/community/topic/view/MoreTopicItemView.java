package com.zhicai.byteera.activity.community.topic.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.classroom.view.BaseHolder;
import com.zhicai.byteera.activity.community.topic.entity.TopicEntity;
import com.zhicai.byteera.widget.MyCircleStrokeView;

/** Created by bing on 2015/6/3. */
public class MoreTopicItemView extends BaseHolder {
    private MyCircleStrokeView mCsvView;
    private TextView tvTitle;
    private TextView tvPeopleNum;
    private TextView tvAnswerNum;

    public MoreTopicItemView(Context context) {
        super(context);
    }

    @Override protected void initEvent() {
    }

    @Override protected View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.more_topic_item_view, null);
        mCsvView = (MyCircleStrokeView) view.findViewById(R.id.csv_view);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvPeopleNum = (TextView) view.findViewById(R.id.tv_popple_num);
        tvAnswerNum = (TextView) view.findViewById(R.id.tv_answer_num);
        return view;
    }

    @Override public void refreshView() {
        TopicEntity data = (TopicEntity) getData();
        mCsvView.setText(data.getTopicNum() + "期");
        if (data.getTopicNum() <= 9) {
            mCsvView.setText(String.format("%02d期", data.getTopicNum()));
        } else {
            mCsvView.setText(String.format("0%d期", data.getTopicNum()));
        }
        mCsvView.setCircleType(data.isSelected() ? 1 : 0);
        mCsvView.setTextColor(!data.isSelected() ? Color.parseColor("#30d0df") : Color.WHITE);
        tvTitle.setText(data.getTopicName());
        tvPeopleNum.setText(data.getHotNum() + "人参与");
        tvAnswerNum.setText(data.getAnswerNum() + "个答案");
    }
}