package com.zhicai.byteera.activity.community.dynamic.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.activity.AdditionActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord.CoinStoreActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.presenter.DynamicFragmentPre;
import com.zhicai.byteera.activity.community.dynamic.view.DynamicListItem;
import com.zhicai.byteera.activity.community.topic.actiivty.TopicActivity;
import com.zhicai.byteera.activity.traincamp.DailyTaskActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/4/19. */
public class DynamicAdapter<Data> extends BaseAdapter {
    private static final int BANNER_TYPE = 0;
    private static final int LIST_TYPE = 1;
    private List<Data> mList;
    private Context mContext;
    private DynamicFragmentPre dynamicFragmentPre;


    public DynamicAdapter(Context mContext, DynamicFragmentPre dynamicFragmentPre) {
        this.dynamicFragmentPre = dynamicFragmentPre;
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    @Override public int getCount() {
        return mList.size();
    }

    @Override public Object getItem(int position) {
        return mList.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            switch (getItemViewType(position)) {
                case BANNER_TYPE:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.banner_dynamic, parent, false);
                    break;
                case LIST_TYPE:
                    convertView = new DynamicListItem(mContext);
                    break;
            }
        }
        switch (getItemViewType(position)) {
            case BANNER_TYPE:
                setBanner(position, convertView);
                break;
            case LIST_TYPE:
                final DynamicEntity entity = (DynamicEntity) mList.get(position);
                if (convertView != null) {
                    ((DynamicListItem) convertView).refreshView(entity, position, dynamicFragmentPre);
                }
                break;
        }
        return convertView;
    }

    private void setBanner(int position, View convertView) {
        LinearLayout llRank = ViewHolder.get(convertView, R.id.ll_rank);    //排行榜
        LinearLayout llSubject = ViewHolder.get(convertView, R.id.ll_subject);      //专题
        LinearLayout llAdd = ViewHolder.get(convertView, R.id.ll_add);              //添加
        LinearLayout llTopic = ViewHolder.get(convertView, R.id.ll_topic);              //添加
        LinearLayout llCoinTask = ViewHolder.get(convertView, R.id.ll_cointask);       //财币任务
        LinearLayout llShangChen = ViewHolder.get(convertView, R.id.ll_shangcheng);     //商城

        llRank.setOnClickListener(new View.OnClickListener() {  //进入排行榜
            @Override public void onClick(View v) {

            }
        });

        llSubject.setOnClickListener(new View.OnClickListener() {       //专题
            @Override public void onClick(View v) {
                ActivityUtil.startActivity((Activity) mContext, new Intent(mContext, SubjectActivity.class));
            }
        });

        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ActivityUtil.startActivity((Activity) mContext, new Intent(mContext, AdditionActivity.class));
            }
        });

        llTopic.setOnClickListener(new View.OnClickListener() {   //添加
            @Override public void onClick(View v) {
                ActivityUtil.startActivity((Activity) mContext, new Intent(mContext, TopicActivity.class));
            }
        });
        llCoinTask.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ActivityUtil.startActivity((Activity) mContext,new Intent(mContext, DailyTaskActivity.class));
            }
        });
        llShangChen.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ActivityUtil.startActivity((Activity) mContext, new Intent(mContext, CoinStoreActivity.class));
            }
        });
    }

    @Override public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return BANNER_TYPE;
        return LIST_TYPE;
    }

    public void addItem(Object obj) {
        mList.add((Data) obj);
        notifyDataSetChanged();
    }

    public void addAllItem(List list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void removeAllView() {
        mList.clear();
    }

    public void removeItemAtPosition(int position) {
        mList.remove(position);
    }

    public void refreshItem(int position, DynamicEntity entity) {
        mList.remove(position);
        mList.set(position, (Data) entity);
        notifyDataSetChanged();
    }
}
