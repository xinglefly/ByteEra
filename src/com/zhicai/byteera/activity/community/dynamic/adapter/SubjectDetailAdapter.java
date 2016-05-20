package com.zhicai.byteera.activity.community.dynamic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/** Created by lieeber on 15/8/26. */
public class SubjectDetailAdapter extends BaseAdapter {
    private Context mContext;
    private List mList;

    public SubjectDetailAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList();
    }

    public void setData(List mList) {
        this.mList = mList;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.subject_detail_item, null);
        }
        TextView tvTitle = ViewHolder.get(convertView, R.id.tv_title);
        ImageView ivHead = ViewHolder.get(convertView, R.id.iv_head);
        String url = "http://cdn.duitang.com/uploads/item/201301/30/20130130185116_wCzRA.thumb.600_0.jpeg";
        ImageLoader.getInstance().displayImage(url, ivHead);
        return convertView;
    }
}
