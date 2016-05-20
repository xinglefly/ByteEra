package com.zhicai.byteera.activity.community.dynamic.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.group.GroupEntity;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.service.dynamic.DynamicGroupV2;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.ArrayList;
import java.util.List;

/** Created by lieeber on 15/8/26. */
public class JoinGroupAdapter extends BaseAdapter {
    private List<GroupEntity> mList;
    private Activity mContext;
    private String userId;

    public JoinGroupAdapter(Activity mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
    }

    public void setData(List<GroupEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.addition_item, null);
        }

        userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();

        GroupEntity groupEntity = mList.get(position);
        ImageView ivAvatar = ViewHolder.get(convertView, R.id.iv_avatar);
        TextView tvName = ViewHolder.get(convertView, R.id.tv_name);
        TextView tvDes = ViewHolder.get(convertView, R.id.tv_des);
        ImageView ivadd = ViewHolder.get(convertView, R.id.iv_add);
        ivadd.setVisibility(View.GONE);
        ImageLoader.getInstance().displayImage(groupEntity.getAvatarUrl(), ivAvatar);
        tvName.setText(groupEntity.getName());
        tvDes.setText(groupEntity.getDescription());

        return convertView;
    }


}
