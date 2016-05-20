package com.zhicai.byteera.activity.community.topic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.topic.entity.TopicEntity;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/5/27. */
public class TopicAdapter extends BaseAdapter {
    public TopicAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
    }

    private Context mContext;
    private List<TopicEntity> mList;
    private int[] imgs = new int[]{R.drawable.qipao1, R.drawable.qipao2, R.drawable.qipao3, R.drawable.qipao4};

    @Override public int getCount() {
        return mList.size();
    }

    @Override public Object getItem(int position) {
        return mList.get(position);
    }

    @Override public long getItemId(int position) {
        return 0;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        TopicEntity entity = mList.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.topic_item, null);

        ImageView ivContent = ViewHolder.get(convertView, R.id.iv_content);
        TextView tvColum = ViewHolder.get(convertView, R.id.tv_colum);
        TextView tvTitle = ViewHolder.get(convertView, R.id.tv_title);
        TextView tvArgue = ViewHolder.get(convertView, R.id.tv_argue);
        TextView tvAnswer = ViewHolder.get(convertView, R.id.tv_answer);

        int column = entity.getTopicNum();
        if (column <= 9) {
            tvColum.setText(String.format("%02d期", column));
        } else {
            tvColum.setText(String.format("0%d期", column));
        }
        switch (position%4){
            case 0:
                tvColum.setBackgroundResource(imgs[0]);
                break;
            case 1:
                tvColum.setBackgroundResource(imgs[1]);
                break;
            case 2:
                tvColum.setBackgroundResource(imgs[2]);
                break;
            case 3:
                tvColum.setBackgroundResource(imgs[3]);
                break;
        }

        ImageLoader.getInstance().displayImage(entity.getTopicImgUrl(), ivContent);
        tvAnswer.setText(entity.getAnswerNum() + "个答案");
        tvTitle.setText(entity.getTopicName());
        tvArgue.setText(entity.getHotNum() + "人热议");
        AlphaAnimation animation =new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1500);
        animation.setRepeatCount(0);
        ivContent.startAnimation(animation);
        return convertView;
    }

    public void setData(List<TopicEntity> topicItemEntities) {
        this.mList = topicItemEntities;
    }
}
