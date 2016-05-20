package com.zhicai.byteera.activity.community.topic.actiivty;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bing on 2015/6/23.
 * 发表意见的时候 选择机构出现的机构列表
 */
public class InstitutionListActivity extends BaseActivity {
    private static final String TAG = InstitutionListActivity.class.getSimpleName();

    @Bind(R.id.view_pager) ViewPager mViewPager;
    @Bind(R.id.id_indicator) TabLayout mIndicator;
    @Bind(R.id.head_view) HeadViewMain mHeadView;

    private static final String[] titles = new String[]{"P2P", "直销银行"};

    @Override protected void loadViewLayout() {
        setContentView(R.layout.institution_list_activity);
        ButterKnife.bind(this);
    }

    @Override protected void initData() {
        mIndicator.setTabMode(TabLayout.MODE_FIXED);
        mIndicator.addTab(mIndicator.newTab().setText("P2P"));
        mIndicator.addTab(mIndicator.newTab().setText("直销银行"));
    }

    @Override protected void updateUI() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myPagerAdapter);
        mIndicator.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mIndicator.setTabsFromPagerAdapter(myPagerAdapter);//给Tabs设置适配器
    }

    @Override protected void processLogic() {
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override public void onLeftImgClick() {
                UIUtils.hideKeyboard(baseContext);
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int position) {
            return InstitutionFragmentFactory.createFragment(position);
        }

        @Override public int getCount() {
            return titles.length;
        }

        @Override public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }



}
