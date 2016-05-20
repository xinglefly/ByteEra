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

/** Created by bing on 2015/5/15. */
public class MyFriendAdapter extends BaseAdapter implements SectionIndexer {
    private Context ct;
    private List<UserInfoEntity> data;

    public MyFriendAdapter(Context ct) {
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
        TextView groupTitle = ViewHolder.get(convertView, R.id.group_title);
        TextView tvName = ViewHolder.get(convertView, R.id.tv_name);

        UserInfoEntity user = data.get(position);
        tvName.setText(user.getNickName());
        ImageLoader.getInstance().displayImage(user.getAvatarUrl(),ivAvatar);

        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            groupTitle.setVisibility(View.VISIBLE);
            groupTitle.setText(StringUtil.getPinYinHeadChar(user.getNickName()).substring(0, 1));
        } else {
            groupTitle.setVisibility(View.GONE);
        }

        return convertView;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return StringUtil.getPinYinHeadChar(data.get(position).getNickName()).charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            char firstChar = StringUtil.getPinYinHeadChar(data.get(i).getNickName()).charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}
