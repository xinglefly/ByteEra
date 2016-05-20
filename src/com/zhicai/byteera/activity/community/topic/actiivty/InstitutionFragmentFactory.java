package com.zhicai.byteera.activity.community.topic.actiivty;

import android.os.Bundle;
import android.util.SparseArray;

import com.zhicai.byteera.activity.BaseFragment;

/** Created by bing on 2015/4/14. */
public class InstitutionFragmentFactory {
    private static SparseArray<BaseFragment> mFragmentMap = new SparseArray<>();

    public static BaseFragment createFragment(int index) {
        BaseFragment fragment = mFragmentMap.get(index);
        Bundle bundle = new Bundle();
        if (fragment == null) {
            switch (index) {// 2:p2p, 3:众筹, 4:票据，5:直销银行
                case 0:
                    fragment = new InsAllFragment();
                    bundle.putInt("company_type",2);
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new InsAllFragment();
                    bundle.putInt("company_type",5);
                    fragment.setArguments(bundle);
                    break;
            }
            mFragmentMap.put(index, fragment);
        }
        return fragment;
    }
}
