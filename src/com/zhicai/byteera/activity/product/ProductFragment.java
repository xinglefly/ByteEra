package com.zhicai.byteera.activity.product;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zhicai.byteera.R;
import com.zhicai.byteera.widget.MyViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;

@SuppressWarnings("unused")
public class ProductFragment extends Fragment {

    @Bind(R.id.bank_pager) MyViewPager mPager;
    @Bind(R.id.qiehuan) TextView qiehuan;
    @Bind(R.id.top_head) View topHead;
    @Bind(R.id.id_indicator) TabLayout mIndicator;
    private boolean isClick = false;
    private ProductFragmentManager productFragmentManager;

    public interface QieHuanClickListener {
        void qiehuan();
    }

    private SparseArray<QieHuanClickListener> listeners = new SparseArray<>();

    public void setQieHuanClickListener(QieHuanClickListener listener, int position) {
        if (this.listeners.get(position) == null) {
            this.listeners.append(position, listener);
        }
    }

    private static final String[] titles = new String[]{ "P2P", "直销银行"};


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_main, container, false);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        productFragmentManager = new ProductFragmentManager();
        initIndicator();
        initView();
    }

    private void initIndicator() {
        mIndicator.setTabMode(TabLayout.MODE_SCROLLABLE);
        mIndicator.addTab(mIndicator.newTab().setText("P2P"));
        mIndicator.addTab(mIndicator.newTab().setText("直销银行"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topHead.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        BankFragmentAdapter adapter = new BankFragmentAdapter(getFragmentManager());
        mPager.setOffscreenPageLimit(1);
        mPager.setAdapter(adapter);
        mIndicator.setupWithViewPager(mPager);
        mIndicator.setTabsFromPagerAdapter(adapter);
        mPager.setCurrentItem(0);
    }


    @OnClick({R.id.qiehuan})
    public void clickListener() {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).qiehuan();
        }
        if(isClick){
            isClick = false;
            qiehuan.setText("排名");
        }else{
            isClick = true;
            qiehuan.setText("返回");
        }
    }

    @OnPageChange(value = R.id.bank_pager,callback = OnPageChange.Callback.PAGE_SELECTED)
    public void onPageSelected(int position){
            qiehuan.setEnabled(true);
            qiehuan.setTextColor(getResources().getColor(R.color.white));
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
            return productFragmentManager.createFragment(position, ProductFragment.this);
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
