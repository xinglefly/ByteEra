package com.zhicai.byteera.activity.community.dynamic.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragmentActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Bimp;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.zoomphoto.PhotoView;
import com.zhicai.byteera.widget.zoomphoto.ViewPagerFixed;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Bind;

public class GalleryActivity extends BaseFragmentActivity {
    //当前的位置
    private int location = 0;
    private ArrayList<View> listViews = null;
    private ViewPagerFixed pager;
    private MyPageAdapter adapter;

    @Bind(R.id.head_view)  HeadViewMain mHeadView;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_camera_gallery);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        pager = (ViewPagerFixed) findViewById(R.id.gallery01);
        for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
            initListViews(Bimp.tempSelectBitmap.get(i).getBitmap());
        }

        adapter = new MyPageAdapter(listViews);
        pager.setAdapter(adapter);
       // pager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.ui_10_dip));
        int id = intent.getIntExtra("ID", 0);
        pager.setCurrentItem(id);

        mHeadView.setRightTextClickListener(new HeadViewMain.RightTextClickListener() {
            @Override public void onRightTextClick() {
                if (listViews.size() == 1) {
                    Bimp.tempSelectBitmap.clear();
                    Bimp.max = 0;
                    Intent intent = new Intent("data.broadcast.action");
                    sendBroadcast(intent);
                    ActivityUtil.finishActivity(GalleryActivity.this);
                } else {
                    Bimp.tempSelectBitmap.remove(location);
                    Bimp.max--;
                    pager.removeAllViews();
                    listViews.remove(location);
                    adapter.setListViews(listViews);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        pager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override public void onPageSelected(int position) {
                location = position;
            }

            @Override public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initListViews(Bitmap bm) {
        if (listViews == null)
            listViews = new ArrayList<View>();
        PhotoView img = new PhotoView(this);
        img.setBackgroundColor(0xff000000);
        img.setImageBitmap(bm);
        img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        listViews.add(img);
    }

    class MyPageAdapter extends PagerAdapter {
        private ArrayList<View> listViews;
        private int size;

        public MyPageAdapter(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public int getCount() {
            return size;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
            arg0.removeView(listViews.get(arg1 % size));
        }


        public Object instantiateItem(ViewGroup arg0, int arg1) {
            try {
                arg0.addView(listViews.get(arg1 % size), 0);
            } catch (Exception e) {
            }
            return listViews.get(arg1 % size);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}
