package com.zhicai.byteera.activity.community.topic.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.topic.entity.InstitutionUserEntity;
import com.zhicai.byteera.commonutil.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bing on 2015/5/15.
 */
public class InstitutionListAdapter extends BaseAdapter implements SectionIndexer {
    private Context ct;
    private List<InstitutionUserEntity> data = new ArrayList<>();

    public InstitutionListAdapter(Context ct) {
        this.ct = ct;
    }

    /** 当ListView数据发生变化时,调用此方法来更新ListView @param list */
    public void updateListView(List<InstitutionUserEntity> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    public void remove(InstitutionUserEntity user) {
        this.data.remove(user);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ct).inflate(R.layout.my_friends_item, null);
            viewHolder = new ViewHolder();
            viewHolder.group_title = (TextView) convertView.findViewById(R.id.group_title);
            viewHolder.tvUserNmae = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.ivAvatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        InstitutionUserEntity user = data.get(position);
        final String avatar = user.getHeadPortrait();
        final String userName = user.getNickName();

        viewHolder.tvUserNmae.setText(userName);
        ImageLoader.getInstance().displayImage(avatar, viewHolder.ivAvatar);
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.group_title.setVisibility(View.VISIBLE);
            viewHolder.group_title.setText(StringUtil.getPinYinHeadChar(user.getNickName()).substring(0, 1));
        } else {
            viewHolder.group_title.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView group_title;// 首字母提示
        TextView tvUserNmae;
        ImageView ivAvatar;
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
    @SuppressLint("DefaultLocale")
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
