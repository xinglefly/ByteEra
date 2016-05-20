package com.zhicai.byteera.activity.knowwealth.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.activity.UserHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicCommentEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.StringUtil;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/4/15. */
public class KnowWealthDetailAdapter extends BaseAdapter {
    private List<DynamicCommentEntity> mList;
    private Context mContext;

    public KnowWealthDetailAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public void setData(List<DynamicCommentEntity> list) {
        mList = list;
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
        final DynamicCommentEntity entity = mList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.know_wealth_detail_item, null);
        }

        ImageView ivCommentUser = ViewHolder.get(convertView, R.id.iv_comment_user);
        TextView tvUser = ViewHolder.get(convertView, R.id.tv_user);
        TextView tvTime = ViewHolder.get(convertView, R.id.tv_time);
        TextView tvContent = ViewHolder.get(convertView, R.id.tv_content);

        tvUser.setText(entity.getNickName());
        tvTime.setText(StringUtil.checkTime(entity.getPublishTime() * 1000));
        tvContent.setText(entity.getContent());
        ImageLoader.getInstance().displayImage(entity.getAvatarUrl(), ivCommentUser);

        ivCommentUser.setOnClickListener(new View.OnClickListener() {   //跳转到用户详情界面
            @Override public void onClick(View v) {
                Intent intent = new Intent(mContext, UserHomeActivity.class);
                intent.putExtra("other_user_id", entity.getUserId());
                ActivityUtil.startActivity((Activity) mContext, intent);
            }
        });
        return convertView;
    }

    public void addAllItem(List<DynamicCommentEntity> commentList) {
        this.mList.addAll(commentList);
    }

    public void removeItemAtPosition(int position) {
        mList.remove(position);
    }
}
