package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.classroom.view.BaseHolder;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.product.FinancingProduct;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

/** Created by bing on 2015/6/2. */
public class OrganizationHomeProductItem extends BaseHolder {

    private ImageView ivHead;
    private TextView tvTitle;
    private TextView tvIncome;
    private TextView tvIncomeDetail;
    private ProductEntity entity;
    private String userId;

    public OrganizationHomeProductItem(Context context) {
        super(context);
    }

    @Override protected void initEvent() { }

    @Override protected View initView() {
        userId = PreferenceUtils.getInstance(context).readUserInfo().getUser_id();
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, null);
        ivHead = (ImageView) view.findViewById(R.id.iv_head);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvIncome = (TextView) view.findViewById(R.id.tv_income);
        tvIncomeDetail = (TextView) view.findViewById(R.id.tv_income_detail);
        return view;
    }



    @Override public void refreshView() {
        entity = (ProductEntity) getData();
        if (entity != null) {
            ImageLoader.getInstance().displayImage( entity.getSmallImage(), ivHead);
            tvTitle.setText(entity.getProductTitle());
            tvIncome.setText(String.format("年化收益%.2f", (float) entity.getProductIncome()) + "%");
            tvIncomeDetail.setText(String.format("融资金额%d万 期限%d个月", entity.getProductCoin(), entity.getProductLimit()));
        }
    }
}
