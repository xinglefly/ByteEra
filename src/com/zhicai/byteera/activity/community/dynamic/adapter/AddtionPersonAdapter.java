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
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.group.GroupEntity;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.service.dynamic.DynamicGroupV2;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AddtionPersonAdapter extends BaseAdapter {
    private List<GroupEntity> mList;
    private Activity mContext;
    private String userId;

    public AddtionPersonAdapter(Activity mContext) {
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
        userId = MyApp.getInstance().getUserId();
        GroupEntity groupEntity = mList.get(position);
        ImageView ivAvatar = ViewHolder.get(convertView, R.id.iv_avatar);
        TextView tvName = ViewHolder.get(convertView, R.id.tv_name);
        TextView tvDes = ViewHolder.get(convertView, R.id.tv_des);
        ImageView ivadd = ViewHolder.get(convertView, R.id.iv_add);
        ImageLoader.getInstance().displayImage(groupEntity.getAvatarUrl(), ivAvatar);
        tvName.setText(groupEntity.getName());
        tvDes.setText(groupEntity.getDescription());

        if (TextUtils.isEmpty(userId)){
            ivadd.setImageResource(R.drawable.attention);
        }else{
            if (groupEntity.isJoined()){
                ivadd.setImageResource(R.drawable.attentioned);
            }else{
                ivadd.setImageResource(R.drawable.attention);
            }
        }
        ivadd.setOnClickListener(new ListViewImageOnClickListener(position,groupEntity.getGroupId(),groupEntity.isJoined()));

        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }


    class ListViewImageOnClickListener implements View.OnClickListener{

        private int position;
        private String group_id;
        private boolean isJoined;
        public ListViewImageOnClickListener(int position,String group_id,boolean isJoined) {
            this.position = position;
            this.group_id = group_id;
            this.isJoined = isJoined;
        }

        @Override
        public void onClick(View v) {
            doJoinGruop(position, group_id, isJoined);
            notifyDataSetChanged();
        }
    }


    public void doJoinGruop(final int position,String group_id,boolean isJoined) {
        if(TextUtils.isEmpty(userId)){
            ActivityUtil.startActivity(mContext, new Intent(mContext, LoginActivity.class));
            return;
        }

        if (isJoined){
            toLeaved(position,group_id, userId);
        }else{
            toJoined(position,group_id, userId);
        }

    }

    /**加入小组**/
    private void toJoined(final int position,String group_id, String userId) {
        final DynamicGroupV2.LicaiquanGroupJoinReq req = DynamicGroupV2.LicaiquanGroupJoinReq.newBuilder().setMyUserId(userId).setLicaiquanGroupId(group_id).build();
        TiangongClient.instance().send("chronos", "licaiquan_group_join", req.toByteArray(), new BaseHandlerClass() {
            @Override public void onSuccess(byte[] buffer) {
                try {
                    final DynamicGroupV2.LicaiquanGroupJoinResponse response = DynamicGroupV2.LicaiquanGroupJoinResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        final boolean isFocus = true;
                        mContext.sendBroadcast(new Intent(Constants.GROUPACTION));
                        ToastUtil.showToastText("关注成功");
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**离开小组**/
    private void toLeaved(final int position,String group_id, String userId) {
        final DynamicGroupV2.LicaiquanGroupLeaveReq req = DynamicGroupV2.LicaiquanGroupLeaveReq.newBuilder().setMyUserId(userId).setLicaiquanGroupId(group_id).build();
        TiangongClient.instance().send("chronos", "licaiquan_group_leave", req.toByteArray(), new BaseHandlerClass() {
            @Override public void onSuccess(byte[] buffer) {
                try {
                    final DynamicGroupV2.LicaiquanGroupLeaveResponse response = DynamicGroupV2.LicaiquanGroupLeaveResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        final boolean isFocus = false;
                        mContext.sendBroadcast(new Intent(Constants.GROUPACTION));
                        ToastUtil.showToastText("取消关注");
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendBroadCast(int position,boolean isFocus) {
        mContext.sendBroadcast(new Intent(Constants.GROUPACTION).putExtra("position", position).putExtra("isFocus", isFocus));
    }
}
