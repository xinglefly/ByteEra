package com.zhicai.byteera.activity.shouyi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.commonutil.ViewHolder;
import java.util.List;

public class InComeAdapter extends BaseAdapter {
    private Context mContext;
    private List<ProductEntity> mList;
    private int coin = 10000;


    public InComeAdapter(Context mContext, List<ProductEntity> mList) {
        this.mContext = mContext;
        this.mList = mList;
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
        if (convertView==null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.income_item,parent,false);

        ImageView ivHeader = ViewHolder.get(convertView, R.id.iv_header);
        TextView tvName = ViewHolder.get(convertView, R.id.tv_name);
        TextView tvRate = ViewHolder.get(convertView, R.id.tv_rate);
        TextView tvDay = ViewHolder.get(convertView, R.id.tv_day);
        TextView tvMoney = ViewHolder.get(convertView, R.id.tv_money);
        ImageView ivRight = ViewHolder.get(convertView, R.id.iv_right);

        ProductEntity productEntity = mList.get(position);
        ImageLoader.getInstance().displayImage(productEntity.getSmallImage(), ivHeader);
        if (!TextUtils.isEmpty(productEntity.getCompanyName()))
            tvName.setText(productEntity.getCompanyName() + "·" + productEntity.getProductTitle());
        else
            tvName.setText(productEntity.getProductTitle());
        tvRate.setText(String.format("%.02f", productEntity.getProductIncome() * 100)+"%");
        tvDay.setText("/" + productEntity.getProductLimit() + "天");
        tvMoney.setText(String.format("%.02f",(coin*productEntity.getProductIncome()*productEntity.getProductLimit()/360))+"元");
        ivRight.setBackgroundResource(!productEntity.getProductId().equals("") ? R.drawable.shouyi_right : R.drawable.shouyi_right_no);
        return convertView;
    }

    public void updateView(List<ProductEntity> mList,int coin) {
        this.mList = mList;
        this.coin = coin;
        notifyDataSetChanged();
    }

    public void setData(List<ProductEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addAllItem(List<ProductEntity> productEntities) {
        this.mList.addAll(productEntities);
        notifyDataSetChanged();
    }

}
