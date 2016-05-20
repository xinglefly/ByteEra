package com.zhicai.byteera.activity.shouyi.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.StringUtil;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.jpush.JpushProductEntity;

import java.util.ArrayList;
import java.util.List;

/** Created by lieeber on 15/8/26. */
public class BidPushAdapter extends BaseAdapter {
    private List<JpushProductEntity> jpushProductEntityList;
    private Activity mContext;

    public BidPushAdapter(Activity mContext) {
        this.mContext = mContext;
        this.jpushProductEntityList = new ArrayList<>();
    }

    public void setData(List<JpushProductEntity> mList) {
        this.jpushProductEntityList = mList;
        notifyDataSetChanged();
    }


    @Override public int getCount() {
        return jpushProductEntityList.size();
    }

    @Override public Object getItem(int position) {
        return jpushProductEntityList.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.bidpush_item, null);

        JpushProductEntity productEntity = jpushProductEntityList.get(position);
        ImageView ivAvatar = ViewHolder.get(convertView, R.id.iv_avatar);
        TextView tvName = ViewHolder.get(convertView, R.id.tv_name);
        TextView tvIncome = ViewHolder.get(convertView, R.id.tv_income);
        TextView tvLimitTime = ViewHolder.get(convertView, R.id.tv_limittime);
        TextView tvBidTime = ViewHolder.get(convertView, R.id.tv_bidtime);

        ImageLoader.getInstance().displayImage(productEntity.getSmall_image(), ivAvatar);
        tvName.setText(productEntity.getTitle());
        tvIncome.setText("收益"+String.format("%.02f", productEntity.getIncome() * 100) + "%");
        tvLimitTime.setText(productEntity.getTime_limit()+"天");
        tvBidTime.setText(StringUtil.checkYMDTime(productEntity.getProduct_time()*1000)+"发标");

        return convertView;
    }


}
