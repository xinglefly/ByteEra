package com.zhicai.byteera.activity.community.dynamic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.view.MyDynamicItem;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/5/3. */
public class MyDynamicAdapter extends BaseAdapter {

    private Context mContext;
    private List<DynamicEntity> mList;

    public MyDynamicAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
    }

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
        DynamicEntity dynamicEntity = mList.get(position);
        if (convertView == null) {
            convertView = new MyDynamicItem(mContext);
        }
        ((MyDynamicItem) convertView).refreshView(dynamicEntity, position);
        return convertView;
    }

    public void setData(List<DynamicEntity> dynamicItemList) {
        mList = dynamicItemList;
    }

    public void refreshItem(int position, DynamicEntity entity) {
        mList.remove(position);
        mList.set(position, entity);
        notifyDataSetChanged();
    }
}
