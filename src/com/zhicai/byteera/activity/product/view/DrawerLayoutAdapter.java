package com.zhicai.byteera.activity.product.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.product.P2P.P2PPre;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/4/20. */
public class DrawerLayoutAdapter extends BaseAdapter {
    private P2PPre p2pPre;
    private Context mContext;
    private List<ProductEntity> mList;

    public DrawerLayoutAdapter(Context mContext, P2PPre p2PPre) {
        this.mContext = mContext;
        this.p2pPre = p2PPre;
        this.mList = new ArrayList<>();
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {convertView = LayoutInflater.from(mContext).inflate(R.layout.drawer_right_item, parent, false);}
        ImageView ivHead = ViewHolder.get(convertView, R.id.iv_head);
        ImageView ivBid = ViewHolder.get(convertView, R.id.iv_bid);
        TextView tvTitle = ViewHolder.get(convertView, R.id.tv_title);
        TextView tvIncome = ViewHolder.get(convertView, R.id.tv_income);
        TextView tvIncomeDetail = ViewHolder.get(convertView, R.id.tv_income_detail);

        final ProductEntity licaiProduct = mList.get(position);
        ivBid.setImageResource(licaiProduct.getProgress() == 1 ? R.drawable.bid_end : R.drawable.bid);
        ImageLoader.getInstance().displayImage(licaiProduct.getSmallImage(), ivHead);
        tvTitle.setText(licaiProduct.getProductTitle());
        tvIncome.setText(String.format("年化收益%.1f", licaiProduct.getProductIncome() * 100.0) + "%");
        tvIncomeDetail.setText(String.format("融资金额%.2f万 期限%d个月", licaiProduct.getProductCoin() / 10000.0, licaiProduct.getProductLimit() / 30));

        return convertView;
    }

    public void setData(List<ProductEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addAllItem(List<ProductEntity> productEntityList) {
        this.mList.addAll(productEntityList);
        notifyDataSetChanged();
    }

    public void clearData(){
        Log.d("adpter","clear adapter");
        mList.clear();
        notifyDataSetChanged();
    }
}
