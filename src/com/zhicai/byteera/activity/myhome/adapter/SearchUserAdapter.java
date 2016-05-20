package com.zhicai.byteera.activity.myhome.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.commonutil.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class SearchUserAdapter extends BaseAdapter implements Filterable {

    private List<UserInfoEntity> mAllUsers;
    private List<UserInfoEntity> mResultUsers;
    private LayoutInflater mInflater;


    public SearchUserAdapter(Context context, List<UserInfoEntity> allUsers) {
        Context mContext = context;
        mAllUsers = allUsers;
        mResultUsers = new ArrayList<UserInfoEntity>();
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mResultUsers.size();
    }

    @Override
    public UserInfoEntity getItem(int position) {
        return mResultUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.search_user_item, null);
        }
        ImageView ivAvatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
        ImageLoader.getInstance().displayImage(mResultUsers.get(position).getAvatarUrl(), ivAvatar);
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        tvName.setText(mResultUsers.get(position).getNickName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mResultUsers = (ArrayList<UserInfoEntity>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

            protected FilterResults performFiltering(CharSequence s) {
                String str = s.toString().toUpperCase();
                FilterResults results = new FilterResults();
                ArrayList<UserInfoEntity> userList = new ArrayList<UserInfoEntity>();
                if (mAllUsers != null && mAllUsers.size() != 0) {
                    for (UserInfoEntity user : mAllUsers) {
                        String pinYinHeadChar = StringUtil.getPinYinHeadChar(user.getNickName());
                        String pinYinChar = StringUtil.getPingYin(user.getNickName());
                        if (pinYinHeadChar.contains(str)
                                || pinYinChar.contains(str)
                                || user.getNickName().contains(str)) {
                            userList.add(user);
                        }
                    }
                }
                results.values = userList;
                results.count = userList.size();
                return results;
            }
        };
        return filter;
    }
}
