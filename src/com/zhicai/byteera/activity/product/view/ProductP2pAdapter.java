package com.zhicai.byteera.activity.product.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.product.entity.CompanyEntity;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/4/20. */
public class ProductP2pAdapter extends BaseAdapter {
    private Context mContext;
    private List<FinancingCompanyEntity> mList;
    private int selectItem = -1;

    public ProductP2pAdapter(Context mContext) {
        mList = new ArrayList<>();
        this.mContext = mContext;
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
        if (convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.product_p2p_item, parent, false);
        ImageView ivHead = ViewHolder.get(convertView, R.id.iv_head);
        TextView tvName = ViewHolder.get(convertView, R.id.tv_name);
        TextView tvSize = ViewHolder.get(convertView, R.id.tv_size);

        FinancingCompanyEntity entity = mList.get(position);
        ImageLoader.getInstance().displayImage(entity.getCompany_image(), ivHead);
        tvName.setText(entity.getCompany_name());
        tvSize.setText(entity.getCompany_number()+"");
        if (position == selectItem) convertView.setBackgroundColor(Color.parseColor("#eeeeee"));
        else convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        return convertView;
    }

    public void setData(List<FinancingCompanyEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addAllItem(List<FinancingCompanyEntity> mList){
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
        notifyDataSetChanged();
    }

}
