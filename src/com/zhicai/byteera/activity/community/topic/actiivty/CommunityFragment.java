package com.zhicai.byteera.activity.community.topic.actiivty;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.community.classroom.CommunityFragmentFactory;
import com.zhicai.byteera.activity.community.dynamic.activity.DynamicFragment;
import com.zhicai.byteera.activity.community.dynamic.activity.PublishDynamicActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;

@SuppressWarnings("unused")
public class CommunityFragment extends BaseFragment {
    private static final String TAG = CommunityFragment.class.getSimpleName();

    @Bind(R.id.img_contact) ImageView img_contact;
    @Bind(R.id.line) View line;
    @Bind(R.id.rl_title) RelativeLayout rlTitle;
    @Bind(R.id.top_head) View topHead;
    @Bind(R.id.img_cursor) ImageView img_cursor;
    @Bind(R.id.vp_knowpager) ViewPager vp_knowPager;

    private static final int TAB_COUNT = 2;
    private int currentIndex;
    private CommunityFragmentFactory communityFragmentFactory;


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.community_main, container, false);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topHead.setVisibility(View.VISIBLE);
        }
        communityFragmentFactory = new CommunityFragmentFactory();
        initView();
    }

    private void initView() {
        KnowWealthAdapter adapter = new KnowWealthAdapter(getFragmentManager());
        initImgView();
        vp_knowPager.setAdapter(adapter);
        vp_knowPager.setCurrentItem(0);
    }

    @OnPageChange(value = R.id.vp_knowpager,callback = OnPageChange.Callback.PAGE_SELECTED)
    void pageSelected(int position){
        currentIndex = position;
        img_contact.setVisibility(position==1?View.VISIBLE:View.INVISIBLE);
    }

    @OnPageChange(value = R.id.vp_knowpager,callback = OnPageChange.Callback.PAGE_SCROLLED)
    void pageScrolled(int position, float positionOffset, int positionOffsetPixels){
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) line.getLayoutParams();
        if (currentIndex == position) {
            lp.leftMargin = (int) (positionOffset * (rlTitle.getWidth() / 2.0) + currentIndex * (rlTitle.getWidth() / 2.0)) + UIUtils.dip2px(getActivity(), 5);
        } else if (currentIndex >= position) {
            lp.leftMargin = (int) (-(1 - positionOffset) * (rlTitle.getWidth() / 2.0) + currentIndex * (rlTitle.getWidth() / 2.0)) + UIUtils.dip2px(getActivity(), 5);
        }
        line.setLayoutParams(lp);
    }


    @OnClick({R.id.tv_topic, R.id.tv_dynamic, R.id.img_contact})
    void clickListener(View view) {
        switch (view.getId()) {
            case R.id.tv_topic:
                vp_knowPager.setCurrentItem(CommunityFragmentFactory.FINANCING);
                break;
            case R.id.tv_dynamic:
                vp_knowPager.setCurrentItem(CommunityFragmentFactory.DYNAMIC);
                break;
            case R.id.img_contact:
                ActivityUtil.startActivityForResult(getActivity(), this, new Intent(getActivity(), PublishDynamicActivity.class).putExtra("showJoinLicaiquan",true), Constants.REQUEST_FRESH_DYNAMIC_FRAGMENT);
                break;
        }
    }

    private void initImgView() {
        int imgWidth = BitmapFactory.decodeResource(getResources(), R.drawable.titleiconnorqw).getWidth();
        int screenWidth = UIUtils.getScreenWidth(getActivity());
        Matrix matrix = new Matrix();
        int offset = (int) ((screenWidth / (float) TAB_COUNT - imgWidth) / 2);
        matrix.postTranslate(offset, 0);
        img_cursor.setImageMatrix(matrix);
    }

    class KnowWealthAdapter extends FragmentPagerAdapter {
        public KnowWealthAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override public Fragment getItem(int index) {
            switch (index) {
                case 0:
                    return communityFragmentFactory.createFragment(CommunityFragmentFactory.FINANCING);
                case 1:
                    return  communityFragmentFactory.createFragment(CommunityFragmentFactory.DYNAMIC);
            }
            return null;
        }
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_FRESH_DYNAMIC_FRAGMENT) {
            ((DynamicFragment) communityFragmentFactory.getFragment(1)).getPre().getDataFromNet(true);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
