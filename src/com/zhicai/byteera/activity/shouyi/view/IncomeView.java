package com.zhicai.byteera.activity.shouyi.view;

import com.zhicai.byteera.activity.product.entity.ProductEntity;

import java.util.List;

/**
 * Created by chenzhenxing on 15/12/26.
 */
public interface IncomeView {

    void loadComplete();

    void setData(List<ProductEntity> productEntities);

    void addAllItem(List<ProductEntity> productEntities);
}
