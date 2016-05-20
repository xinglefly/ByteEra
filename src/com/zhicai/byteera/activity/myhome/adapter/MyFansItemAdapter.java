package com.zhicai.byteera.activity.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bing on 2015/5/16.
 */
public class MyFansItemAdapter extends BaseAdapter {
    private List<UserInfoEntity> mList;
    private Context mContext;

    public MyFansItemAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
    }

    public void setData(List<UserInfoEntity> mList) {
        this.mList = mList;
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
        UserInfoEntity entity = mList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.my_fans_item, null);
        }

        ImageView ivAvatar = ViewHolder.get(convertView, R.id.iv_avatar);
        TextView tvTitle = ViewHolder.get(convertView, R.id.tv_title);


        ImageLoader.getInstance().displayImage(entity.getAvatarUrl(), ivAvatar);
        tvTitle.setText(entity.getNickName());
        // tvContent.setText(data.getContent());

        return convertView;
    }
}
