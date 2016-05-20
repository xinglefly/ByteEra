package com.zhicai.byteera.activity.community.group;

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

import java.util.List;

/** Created by bing on 2015/6/1. */
public class SelelctGroupAdapter extends BaseAdapter {
    private List<GroupEntity> mList;
    private Context mContext;

    public SelelctGroupAdapter(Context context, List<GroupEntity> mList) {
        this.mList = mList;
        this.mContext = context;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_group_grid_view_item, parent, false);
        TextView tvName = ViewHolder.get(view, R.id.tv_name);
        TextView tvPoppleNum = ViewHolder.get(view, R.id.tv_peopleNum);
        ImageView ivHead = ViewHolder.get(view, R.id.iv_head);

        GroupEntity dynamicGropItem = mList.get(position);
        tvName.setText(dynamicGropItem.getName());
        tvPoppleNum.setText(dynamicGropItem.getPeopleCnt() + "盟友");
        ImageLoader.getInstance().displayImage(dynamicGropItem.getAvatarUrl(), ivHead);
        return view;
    }
}
