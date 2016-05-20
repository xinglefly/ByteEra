package com.zhicai.byteera.activity.knowwealth.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhicai.byteera.R;
import com.zhicai.byteera.widget.MyViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;


@SuppressWarnings("unused")
public class FinancingFragment extends Fragment {

    @Bind(R.id.tab_indicator) TabLayout mIndicator;
    @Bind(R.id.financial_pager) MyViewPager mPager;
    private FinancingFragmentManager productFragmentManager;
    private static final String[] titles = new String[]{ "全部","P2P","消费金融","互联网理财", "直销银行","众筹"};


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.finacingfragment, container, false);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        productFragmentManager = new FinancingFragmentManager();
        initIndicator();
        initView();
    }

    private void initIndicator() {
        mIndicator.setTabMode(TabLayout.MODE_SCROLLABLE);
        mIndicator.addTab(mIndicator.newTab().setText("全部"));
        mIndicator.addTab(mIndicator.newTab().setText("P2P"));
        mIndicator.addTab(mIndicator.newTab().setText("消费金融"));
        mIndicator.addTab(mIndicator.newTab().setText("互联网理财"));
        mIndicator.addTab(mIndicator.newTab().setText("直销银行"));
        mIndicator.addTab(mIndicator.newTab().setText("众筹"));
    }

    private void initView() {
        BankFragmentAdapter adapter = new BankFragmentAdapter(getFragmentManager());
        mPager.setOffscreenPageLimit(6);
        mPager.setAdapter(adapter);
        mIndicator.setupWithViewPager(mPager);
        mIndicator.setTabsFromPagerAdapter(adapter);
        mPager.setCurrentItem(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class BankFragmentAdapter extends FragmentPagerAdapter {
        public BankFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int position) {
            return productFragmentManager.createFragment(position, FinancingFragment.this);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override public int getCount() {
            return titles.length;
        }

        @Override public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
