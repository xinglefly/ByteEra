package com.zhicai.byteera.activity.product;


import android.util.SparseArray;

import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.product.P2P.P2pFragment;

public final class ProductFragmentManager {

    private static final int ZHICAI_P2P = 2;
    private static final int ZHICAI_ZXYH = 5;
    private SparseArray<BaseFragment> mFragmentMap = new SparseArray<BaseFragment>();

    public BaseFragment createFragment(int index, ProductFragment productFragment) {
        BaseFragment fragment = mFragmentMap.get(index);
        if (fragment == null) {
            switch (index) {
                case 0:
                    fragment = new P2pFragment(productFragment,0,ZHICAI_P2P);
                    break;
                case 1:
                    fragment = new P2pFragment(productFragment,1,ZHICAI_ZXYH);
                    break;
            }
            mFragmentMap.put(index,fragment);
        }
        return fragment;
    }
}
