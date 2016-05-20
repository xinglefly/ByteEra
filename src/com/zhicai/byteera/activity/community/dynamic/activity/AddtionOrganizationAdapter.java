package com.zhicai.byteera.activity.community.dynamic.activity;

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
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.service.dynamic.InstitutionAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.ArrayList;
import java.util.List;

/** Created by lieeber on 15/8/26. */
public class AddtionOrganizationAdapter extends BaseAdapter {
    private List<UserInfoEntity> mList;
    private Activity mContext;
    private String userId;
    private ImageView ivadd;

    public AddtionOrganizationAdapter(Activity mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
    }

    public void refresh(List<UserInfoEntity> list){
        mList = list;
        notifyDataSetChanged();
    }

    public void setData(List<UserInfoEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override public int getCount() {
        return mList.size();
    }

    @Override public UserInfoEntity getItem(int position) {
        return mList.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }



    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.addition_organization_item, null);
        }

        userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();

        final UserInfoEntity userInfo = mList.get(position);
        ImageView ivAvatar = ViewHolder.get(convertView, R.id.iv_avatar);
        TextView tvName = ViewHolder.get(convertView, R.id.tv_name);
        ivadd = ViewHolder.get(convertView, R.id.iv_add);

        if (TextUtils.isEmpty(userId)){
            ivadd.setImageResource(R.drawable.attention);
        }else{
            if (userInfo.isHasWatched()){
                ivadd.setImageResource(R.drawable.attentioned);
            }else{
                ivadd.setImageResource(R.drawable.attention);
            }
        }

        ImageLoader.getInstance().displayImage(userInfo.getAvatarUrl(), ivAvatar);
        tvName.setText(userInfo.getNickName());

        ivadd.setOnClickListener(new ListViewImageOnClickListener(position,userInfo.getUserId()));

        return convertView;
    }



    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }


    class ListViewImageOnClickListener implements View.OnClickListener{

        private int position;
        private String organization_id;
        public ListViewImageOnClickListener(int position,String organization_id) {
            this.position = position;
            this.organization_id = organization_id;
        }

        @Override
        public void onClick(View v) {
            doFocus(organization_id,position);
        }
    }


    public void doFocus(String organization_id,final int position) {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        if(TextUtils.isEmpty(userId)){
            ActivityUtil.startActivity(mContext, new Intent(mContext, LoginActivity.class));
            return;
        }
        final InstitutionAttribute.WatchInstitutionReq req = InstitutionAttribute.WatchInstitutionReq.newBuilder().setUserId(organization_id).setOtherUserId(userId).build();
        TiangongClient.instance().send("chronos", "watch_institution", req.toByteArray(), new BaseHandlerClass() {
            @Override public void onSuccess(byte[] buffer) {
                try {
                    final InstitutionAttribute.WatchInstitutionResponse response = InstitutionAttribute.WatchInstitutionResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        final boolean isFocus = response.getWatched();
                        sendBroadCast(position,isFocus);
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendBroadCast(int position,boolean isFocus) {
        mContext.sendBroadcast(new Intent(Constants.ORGANIZATIONACTION).putExtra("position", position).putExtra("isFocus", isFocus));
    }


}
