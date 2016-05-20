package com.zhicai.byteera.activity.product.recommend;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/4/24. */
public class RecommendAdapter extends BaseAdapter {
    private List<ProductEntity> mList = new ArrayList<>();
    private Context mContext;
    private RecommendPre recommendPre;

    public RecommendAdapter(Context context, RecommendPre recommendPre) {
        this.mContext = context;
        this.recommendPre = recommendPre;
    }

    @Override public int getCount() {
        return mList.size();
    }

    @Override public Object getItem(int position) {
        return null;
    }

    @Override public long getItemId(int position) {
        return 0;
    }


    public void setData(List<ProductEntity> list) {
        this.mList = list;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        final ProductEntity entity = mList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.drawer_right_item, parent, false);
        }
        ImageView ivHead = ViewHolder.get(convertView, R.id.iv_head);
        TextView tvTitle = ViewHolder.get(convertView, R.id.tv_title);
        TextView tvIncome = ViewHolder.get(convertView, R.id.tv_income);
        TextView tvIncomeDetail = ViewHolder.get(convertView, R.id.tv_income_detail);

        ImageLoader.getInstance().displayImage(entity.getSmallImage(), ivHead);
        tvTitle.setText(entity.getProductTitle());
        tvIncome.setText(String.format("年化收益%.2f", (float) entity.getProductIncome()) + "%");
        tvIncomeDetail.setText(String.format("融资金额%d万 期限%d个月", entity.getProductCoin(), entity.getProductLimit()));

        return convertView;
    }
}
