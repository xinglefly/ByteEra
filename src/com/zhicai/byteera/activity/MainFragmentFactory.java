package com.zhicai.byteera.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.topic.actiivty.CommunityFragment;
import com.zhicai.byteera.activity.myhome.activity.MyHomeFragment;
import com.zhicai.byteera.activity.product.ProductFragment;
import com.zhicai.byteera.activity.shouyi.LiCaiShouYiFragment;

public class MainFragmentFactory {
    public static final int TAB_INCOME = 1;
    public static final int TAB_PRODUCT = 2;
    public static final int TAB_FIND = 3;
    public static final int TAB_MINE = 4;

    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;


    public Fragment createFragment(MainActivity mainActivity, int index) {
        FragmentManager fm = mainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case TAB_INCOME:
                if (mTab01 == null) {
                    mTab01 = new LiCaiShouYiFragment();
                    transaction.add(R.id.realcontent, mTab01);
                } else transaction.show(mTab01);
                break;
            case TAB_PRODUCT:
                if (mTab02 == null) {
                    mTab02 = new ProductFragment();
                    transaction.add(R.id.realcontent, mTab02);
                } else transaction.show(mTab02);
                break;
            case TAB_FIND:
                if (mTab03 == null) {
                    mTab03 = new CommunityFragment();
                    transaction.add(R.id.realcontent, mTab03);
                } else transaction.show(mTab03);
                break;
            case TAB_MINE:
                if (mTab04 == null) {
                    mTab04 = new MyHomeFragment();
                    transaction.add(R.id.realcontent, mTab04);
                } else transaction.show(mTab04);
                break;
        }
        transaction.commit();
        return getFragment(index);
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mTab01 != null) transaction.hide(mTab01);
        if (mTab02 != null) transaction.hide(mTab02);
        if (mTab03 != null) transaction.hide(mTab03);
        if (mTab04 != null) transaction.hide(mTab04);
    }

    public void destroyFragment() {
        if (mTab01 != null) mTab01 = null;
        if (mTab02 != null) mTab02 = null;
        if (mTab03 != null) mTab03 = null;
        if (mTab04 != null) mTab04 = null;
    }

    public Fragment getFragment(int index) {
        switch (index) {
            case 0:
                return mTab01;
            case 1:
                return mTab02;
            case 2:
                return mTab03;
            case 3:
                return mTab04;
        }
        return null;
    }

    public void attachFragment(Fragment fragment) {
         if (fragment instanceof LiCaiShouYiFragment) mTab01 = fragment;
         if (fragment instanceof ProductFragment) mTab02 = fragment;
         if (fragment instanceof CommunityFragment) mTab03 = fragment;
         if (fragment instanceof MyHomeFragment) mTab04 = fragment;
    }
}
