package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.presenter.OrganizationHomePre;
import com.zhicai.byteera.activity.product.entity.ProductEntity;

/**
 * Created by bing on 2015/6/2.
 */
public class OrganizationProductListItem extends FrameLayout {
    private ImageView ivHead;
    private TextView tvTitle;
    private TextView tvIncome;
    private TextView tvIncomeDetail;

    public OrganizationProductListItem(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.product_item, this, true);
        ivHead = (ImageView) findViewById(R.id.iv_head);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvIncome = (TextView) findViewById(R.id.tv_income);
        tvIncomeDetail = (TextView) findViewById(R.id.tv_income_detail);

    }

    public void refreshView(final ProductEntity productEntity, final int position, final OrganizationHomePre organizationHomePre) {
        if (productEntity != null) {
            ImageLoader.getInstance().displayImage(productEntity.getSmallImage(), ivHead);
            tvTitle.setText(productEntity.getProductTitle());
            tvIncome.setText(String.format("年化收益%.1f", (float) (productEntity.getProductIncome() * 100)) + "%");
            tvIncomeDetail.setText(String.format("融资金额%.1f万 期限%d个月", productEntity.getProductCoin() / 10000.0, productEntity.getProductLimit() / 30));
        }

    }

}
