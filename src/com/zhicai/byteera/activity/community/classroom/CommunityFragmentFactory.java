package com.zhicai.byteera.activity.community.classroom;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.zhicai.byteera.activity.community.dynamic.activity.DynamicFragment;
import com.zhicai.byteera.activity.knowwealth.view.FinancingFragment;

/** Created by bing on 2015/4/14. */
public class CommunityFragmentFactory {
    public static final int DYNAMIC = 1;
    public static final int FINANCING = 0;

    private SparseArray<Fragment> mFragmentMap = new SparseArray<Fragment>();

    public Fragment createFragment(int index) {
        Fragment fragment = mFragmentMap.get(index);
        if (fragment == null) {
            switch (index) {
                case FINANCING:
                    fragment = new FinancingFragment();
                    break;
                case DYNAMIC:
                    fragment = new DynamicFragment();
                    break;
            }
            mFragmentMap.put(index, fragment);
        }
        return fragment;
    }
    public Fragment getFragment(int index){
        return mFragmentMap.get(index);
    }
}
