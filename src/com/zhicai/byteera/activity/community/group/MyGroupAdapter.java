package com.zhicai.byteera.activity.community.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.service.dynamic.DynamicGroup;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/** Created by bing on 2015/6/11. */
public class MyGroupAdapter extends BaseAdapter {
    private List<GroupEntity> mList;
    private Context mContext;

    public MyGroupAdapter(Context mCotext, List<GroupEntity> mList) {
        this.mList = mList;
        this.mContext = mCotext;
    }

    public MyGroupAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    @Override public int getCount() {
        return mList.size();
    }

    @Override public GroupEntity getItem(int position) {
        return mList.get(position);
    }

    @Override public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GroupEntity entity = mList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.all_group_item, parent, false);
        }
        CircleImageView ivAvatar = ViewHolder.get(convertView, R.id.iv_head);
        TextView tvName = ViewHolder.get(convertView, R.id.tv_name);
        TextView tvPeopleNum = ViewHolder.get(convertView, R.id.tv_peopleNum);
        final TextView tvWatch = ViewHolder.get(convertView, R.id.tv_watch);

        ImageLoader.getInstance().displayImage(entity.getAvatarUrl(), ivAvatar);
        tvName.setText(entity.getName());
        tvPeopleNum.setText(entity.getPeopleCnt() + "");
//        tvWatch.setText(entity.isWatched() ? "已关注" : "关注");
        tvWatch.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                changeFocus(entity);
            }
            private void changeFocus(final GroupEntity entity) {
                String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
                final DynamicGroup.EnterGroupApplyReq req = DynamicGroup.EnterGroupApplyReq.newBuilder().setGroupId(entity.getGroupId()).setMyUserId(userId).build();
                TiangongClient.instance().send("chronos", "licaiquan_enter_group_apply", req.toByteArray(), new BaseHandlerClass() {
                    @Override public void onSuccess(byte[] buffer) {
                        try {
                            final DynamicGroup.EnterGroupApplyResponse response = DynamicGroup.EnterGroupApplyResponse.parseFrom(buffer);
                            if (response.getErrorno() == 0) {
                                MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                                    @Override public void run() {
//                                        entity.setWatched(response.getWatched());
//                                        tvWatch.setText(response.getWatched() ? "已关注" : "关注");
                                    }
                                });
                            }
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        return convertView;
    }

    public void setData(List<GroupEntity> mList) {
        this.mList = mList;
    }
}
