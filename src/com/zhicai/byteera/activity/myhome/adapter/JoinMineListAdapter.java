package com.zhicai.byteera.activity.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.activity.myhome.User;
import com.zhicai.byteera.commonutil.StringUtil;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class JoinMineListAdapter extends BaseAdapter {
    private Context ct;
    private List<UserInfoEntity> data;

    public JoinMineListAdapter(Context ct) {
        this.ct = ct;
        this.data = new ArrayList<>();
    }

    /** 当ListView数据发生变化时,调用此方法来更新ListView @param list */
    public void updateListView(List<UserInfoEntity> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    public void remove(User user) {
        this.data.remove(user);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public UserInfoEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {convertView = LayoutInflater.from(ct).inflate(R.layout.my_friends_item, null);}

        ImageView ivAvatar = ViewHolder.get(convertView, R.id.iv_avatar);
        TextView tvName = ViewHolder.get(convertView, R.id.tv_name);

        UserInfoEntity user = data.get(position);
        tvName.setText(user.getNickName());
        ImageLoader.getInstance().displayImage(user.getAvatarUrl(),ivAvatar);

        return convertView;
    }


}
