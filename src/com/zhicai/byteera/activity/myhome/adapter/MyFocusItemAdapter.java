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

/** Created by bing on 2015/5/16. */
public class MyFocusItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<UserInfoEntity> mList;

    public void setData(List<UserInfoEntity> mList) {
        this.mList = mList;
    }

    public MyFocusItemAdapter(Context mContext) {
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
        UserInfoEntity entity = mList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.my_focus_item, null);
        }

        ImageView ivAvatar = ViewHolder.get(convertView, R.id.iv_avatar);
        TextView tvTitle = ViewHolder.get(convertView, R.id.tv_title);
        TextView tvContent = ViewHolder.get(convertView, R.id.tv_content);
        ImageView ivStatus = ViewHolder.get(convertView, R.id.iv_status);

        ImageLoader.getInstance().displayImage(entity.getAvatarUrl(), ivAvatar);
        tvTitle.setText(entity.getNickName());
        // tvContent.setText(entity.getContent());
        if (entity.getmStatus() == UserInfoEntity.Status.ATTENTION) {
            ivStatus.setImageResource(R.drawable.card_icon_attention);
        } else {
            ivStatus.setImageResource(R.drawable.card_icon_arrow);
        }
        return convertView;
    }
}
