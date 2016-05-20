package com.zhicai.byteera.activity.product.MyAttention;

import android.content.Context;

import com.zhicai.byteera.activity.product.entity.ProductEntity;

import java.util.List;

/** Created by bing on 2015/6/27. */
public interface MyAttentionFragmentIV {

    Context getContext();

    void finishRefresh();

    void listViewRefreshView(List<ProductEntity> mList);

    void showCheckLoginDialog();
}
