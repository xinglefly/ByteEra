package com.zhicai.byteera.activity.community.dynamic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.presenter.GroupHomeActivityPre;
import com.zhicai.byteera.activity.community.dynamic.view.GroupDynamicItem;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/4/23. */
public class GroupHomeAdapter extends BaseAdapter {
    private Context mContext;
    private List<DynamicEntity> mList;

    private GroupHomeActivityPre groupHomeActivityPre;

    public GroupHomeAdapter(Context mContext, GroupHomeActivityPre groupHomeActivityPre) {
        this.mContext = mContext;
        this.groupHomeActivityPre = groupHomeActivityPre;
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
        if (convertView == null) {convertView = new GroupDynamicItem(mContext);}
        DynamicEntity dynamicEntity = mList.get(position);
        ((GroupDynamicItem) convertView).refreshView(dynamicEntity, position, groupHomeActivityPre);
        return convertView;
    }

    public void setData(List<DynamicEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addAllItem(List<DynamicEntity> dynamicEntities) {
        this.mList.addAll(dynamicEntities);
        notifyDataSetChanged();
    }
}
