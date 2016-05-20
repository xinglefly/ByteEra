package com.zhicai.byteera.activity.product.recommend;

import android.content.Context;

import com.zhicai.byteera.activity.product.entity.ProductEntity;

import java.util.ArrayList;

/** Created by bing on 2015/6/27. */
public interface RecommendIV {
    Context getContext();

    void refreshListView(ArrayList<ProductEntity> productEntities);
}
