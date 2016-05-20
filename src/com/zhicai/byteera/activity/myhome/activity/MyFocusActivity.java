package com.zhicai.byteera.activity.myhome.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.HeadViewMain.LeftImgClickListner;
import com.zhicai.byteera.widget.MyViewPager;

import butterknife.ButterKnife;
import butterknife.Bind;

/** Created by bing on 2015/5/16. */
public class MyFocusActivity extends BaseActivity {

    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.id_indicator) TabLayout mIndicator;
    @Bind(R.id.view_pager) MyViewPager mViewPager;
    private static final String[] titles = new String[]{"小组", "机构"};

    @Override protected void loadViewLayout() {
        setContentView(R.layout.my_focus_activity);
        ButterKnife.bind(this);
    }

    @Override protected void initData() {
        mIndicator.setTabMode(TabLayout.MODE_FIXED);
        mIndicator.addTab(mIndicator.newTab().setText("小组"));
        mIndicator.addTab(mIndicator.newTab().setText("机构"));

        FocusFragmentAdapter adapter = new FocusFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mIndicator.setupWithViewPager(mViewPager);
        mIndicator.setTabsFromPagerAdapter(adapter);

    }
    @Override protected void updateUI() {

    }

    @Override protected void processLogic() {
        mHeadView.setLeftImgClickListener(new LeftImgClickListner() {
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }

    class FocusFragmentAdapter extends FragmentPagerAdapter {
        public FocusFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int position) {
            BaseFragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new FocusGroupFragment();
                    break;
                case 1:
                    fragment = new FocusOrganizationFragment();
                    break;
            }
            return fragment;
        }

        @Override public int getCount() {
            return titles.length;
        }

        @Override public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
