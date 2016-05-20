package com.zhicai.byteera.activity.knowwealth.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.bean.BannerEntity;
import com.zhicai.byteera.activity.community.dynamic.activity.AdditionActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord.CoinStoreActivity;
import com.zhicai.byteera.activity.knowwealth.presenter.KnowWealthPre;
import com.zhicai.byteera.activity.traincamp.DailyTaskActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;

/** Created by bing on 2015/4/16. */
@SuppressWarnings("unused")
public class BannerView extends FrameLayout {

    private  KnowWealthPre knowWealthPre;
    private ScheduledExecutorService scheduledExecutorService;
    private ViewPager advPager;
    private VPAdapter vpAdapter;
    private List<View> advPics;
    private List<View> dotViewsList;
    private int currentPosition = 0;


    private Handler mHadler = new Handler() {
        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);
            advPager.setCurrentItem(currentPosition);
            if (vpAdapter == null) {
                vpAdapter = new VPAdapter(advPics);
                advPager.setAdapter(vpAdapter);
            } else vpAdapter.notifyDataSetChanged();
        }
    };


    public BannerView(Context context,KnowWealthPre knowWealthPre) {
        super(context);
        this.knowWealthPre = knowWealthPre;
        initView();
        startPlay();
    }

    protected void initView() {
        advPics = new ArrayList();
        dotViewsList = new ArrayList();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.banner_view, this);
        advPager = ButterKnife.findById(view,R.id.adv_pager);
        dotViewsList.add(findViewById(R.id.iv_1));
        dotViewsList.add(findViewById(R.id.iv_2));
        dotViewsList.add(findViewById(R.id.iv_3));
        setPageListener();
    }

    private void setPageListener() {
        advPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotViewsList.size(); i++)
                    dotViewsList.get(i).setBackgroundResource(i == position ? R.drawable.point_selected : R.drawable.point_unselected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void refreshView(List<BannerEntity> bannerList) {
        advPics.clear();
        for (int i = 0; i < bannerList.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.getInstance().displayImage(bannerList.get(i).getImg_url(), imageView);
            advPics.add(imageView);
        }
    }

    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                synchronized (advPager) {
                    currentPosition = (currentPosition + 1) % advPics.size();
                    mHadler.obtainMessage().sendToTarget();
                }
            }
        }, 1, 5, TimeUnit.SECONDS);
    }


    private class VPAdapter extends PagerAdapter {
        private List<View> views;

        public VPAdapter(List<View> views) {
            this.views = views;
        }

        @Override public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(views.get(position));
            views.get(position).setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    jumpToStartActivity(position);
                }
            });
            return views.get(position);
        }

        @Override public int getCount() {
            return views.size();
        }

        @Override public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    private void jumpToStartActivity(int position) {
        switch (position){
            case 0:
                ActivityUtil.startActivity((Activity)getContext(),new Intent(getContext(), CoinStoreActivity.class));
                break;
            case 1:
                ActivityUtil.startActivity((Activity)getContext(),new Intent(getContext(), AdditionActivity.class));
                break;
            case 2:
                ActivityUtil.startActivity((Activity)getContext(),new Intent(getContext(), DailyTaskActivity.class));
                break;
        }
    }

    private void jumpToUrl(final int jump_point,final String jump_url) {
        if (!TextUtils.isEmpty(jump_url)) {
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            intent.putExtra("url", jump_url);
            ActivityUtil.startActivity((Activity) getContext(), intent);
        }
    }
}
