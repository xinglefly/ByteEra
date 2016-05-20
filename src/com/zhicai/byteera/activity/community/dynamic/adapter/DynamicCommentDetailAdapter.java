package com.zhicai.byteera.activity.community.dynamic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.core.BitmapSize;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicCommentEntity;
import com.zhicai.byteera.commonutil.StringUtil;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/4/15. */
public class DynamicCommentDetailAdapter extends BaseAdapter {
    private Context mContext;
    private List<DynamicCommentEntity> mList;

    public DynamicCommentDetailAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
    }

    public void setData(List<DynamicCommentEntity> mList) {
        this.mList = mList;
    }

    @Override public int getCount() {
        return mList.size();
    }

    @Override public DynamicCommentEntity getItem(int position) {
        return mList.get(position);
    }

    @Override public long getItemId(int position) {
        return 0;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        DynamicCommentEntity dynamicCommentEntity = mList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.know_wealth_detail_item, null);
        }

        ImageView iv_comment_user = ViewHolder.get(convertView, R.id.iv_comment_user);
        TextView tv_user = ViewHolder.get(convertView, R.id.tv_user);
        TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
        TextView tv_content = ViewHolder.get(convertView, R.id.tv_content);

        tv_user.setText(dynamicCommentEntity.getNickName());
        tv_time.setText(StringUtil.checkTime(dynamicCommentEntity.getPublishTime() * 1000));
        tv_content.setText(dynamicCommentEntity.getContent());
        BitmapUtils bitUtils = new BitmapUtils(mContext);
        BitmapDisplayConfig displayConfig = new BitmapDisplayConfig();
        displayConfig.setLoadingDrawable(mContext.getResources().getDrawable(R.drawable.consult_default));
        displayConfig.setBitmapMaxSize(new BitmapSize(100, 100));
        bitUtils.configDefaultDisplayConfig(displayConfig);
        bitUtils.display(iv_comment_user, dynamicCommentEntity.getAvatarUrl());
        return convertView;
    }
}
