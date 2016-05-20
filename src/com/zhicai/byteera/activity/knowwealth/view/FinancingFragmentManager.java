package com.zhicai.byteera.activity.knowwealth.view;


import android.util.SparseArray;

import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.commonutil.Constants;

@SuppressWarnings("unused")
public final class FinancingFragmentManager {

    private SparseArray<BaseFragment> mFragmentMap = new SparseArray();

    public BaseFragment createFragment(int index, FinancingFragment financingFragment) {
        BaseFragment fragment = mFragmentMap.get(index);
        if (fragment == null) {
            switch (index) {
                case 0:
                    fragment = new PageFragment(Constants.ZHICAI_ALL);
                    break;
                case 1:
                    fragment = new PageFragment(Constants.ZHICAI_P2P);
                    break;
                case 2:
                    fragment = new PageFragment(Constants.ZHICAI_XIAOFEI);
                    break;
                case 3:
                    fragment = new PageFragment(Constants.ZHICAI_HLWLC);
                    break;
                case 4:
                    fragment = new PageFragment(Constants.ZHICAI_ZXYH);
                    break;
                case 5:
                    fragment = new PageFragment(Constants.ZHICAI_ZHONGCHOU);
                    break;
            }
            mFragmentMap.put(index,fragment);
        }
        return fragment;
    }
}
