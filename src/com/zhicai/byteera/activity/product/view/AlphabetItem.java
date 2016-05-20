package com.zhicai.byteera.activity.product.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.classroom.view.BaseHolder;
import com.zhicai.byteera.activity.product.entity.AlphaBetEntity;

/**
 * Created by bing on 2015/4/20.
 */
public class AlphabetItem extends BaseHolder {

    private TextView tvAlpha;

    public AlphabetItem(Context context) {
        super(context);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.alpha_bet_item, null);
        tvAlpha = (TextView) view.findViewById(R.id.tv_alpha);
        return view;
    }

    @Override
    public void refreshView() {
        AlphaBetEntity entity = (AlphaBetEntity) getData();
        tvAlpha.setText(entity.getAlphaBet());
    }
}
