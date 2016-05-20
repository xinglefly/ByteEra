package com.zhicai.byteera.activity.product.P2P;

import android.content.Context;

import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.product.entity.CompanyEntity;
import com.zhicai.byteera.activity.product.entity.ProductEntity;

import java.util.List;

/** Created by bing on 2015/6/27. */
public interface P2PIV {
    Context getContext();

    void openDrawerRight(List<ProductEntity> productList,boolean isFirst);

    void loadData(List<ProductEntity> productList,boolean isFirst);

    void finishRefresh();

    void loadComplete(String listViewName);

    void refreshListView(List<FinancingCompanyEntity> companyEntities);


}
