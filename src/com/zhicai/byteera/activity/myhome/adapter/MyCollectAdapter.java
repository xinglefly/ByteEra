package com.zhicai.byteera.activity.myhome.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhicai.byteera.activity.community.dynamic.view.MyCollectItem;
import com.zhicai.byteera.service.information.InformationSecondary;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/5/3. */
public class MyCollectAdapter extends BaseAdapter {
    private Context mContext;
    private List<InformationSecondary.TZixun> mList;

    public MyCollectAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setData(List<InformationSecondary.TZixun> mList) {
        this.mList = mList;
    }


    @Override public int getCount() {
        return mList.size();
    }

    @Override public InformationSecondary.TZixun getItem(int position) {
        return mList.get(position);
    }

    @Override public long getItemId(int position) {
        return 0;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        InformationSecondary.TZixun tZixun = mList.get(position);
        if (convertView == null) {
            convertView = new MyCollectItem(mContext);
        }
        ((MyCollectItem) convertView).freshView(tZixun);
        return convertView;
    }
}
