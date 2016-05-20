package com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.entity.ExchangeEntity;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.List;

/**
 * Created by chenzhenxing on 15/12/14.
 */
public class GridAdapter extends ArrayAdapter<ExchangeEntity> {
    private int res;
    private List<ExchangeEntity> entityList;

    public GridAdapter(Context context, int textViewResourceId, List<ExchangeEntity> entityList) {
        super(context, textViewResourceId, entityList);
        res = textViewResourceId;
        this.entityList = entityList;
    }

    @Override public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {convertView = LayoutInflater.from(getContext()).inflate(res, null);}
        ImageView iv_shopping_pic = ViewHolder.get(convertView, R.id.iv_shopping_pic);
        TextView tv_shopping_name = ViewHolder.get(convertView, R.id.tv_shopping_name);
        TextView tv_shopping_coin = ViewHolder.get(convertView, R.id.tv_shopping_coin);
        TextView tv_shopping_remain = ViewHolder.get(convertView, R.id.tv_shopping_remain);

        ExchangeEntity exchangeEntity = entityList.get(position);
        ImageLoader.getInstance().displayImage(exchangeEntity.getItem_image(), iv_shopping_pic);
        tv_shopping_name.setText(exchangeEntity.getItem_name());
        tv_shopping_coin.setText(exchangeEntity.getItem_coin()+"");
        tv_shopping_remain.setText("剩余"+exchangeEntity.getItem_left_count());

        return convertView;
    }

    @Override public int getCount() {
        return entityList.size();
    }
}
