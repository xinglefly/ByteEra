package com.zhicai.byteera.activity.community.dynamic.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;

/** Created by lieeber on 15/8/26. */
public class RecommendActivity extends BaseActivity {
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.load_more_list_view) LoadMoreListView moreListView;
    @Bind(R.id.swipe_layout) SwipeRefreshLayout mSwipeLayout;
    private RecommendActivityAdapter mAdapter;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.recommend_activity);
        ButterKnife.bind(this);
        mAdapter = new RecommendActivityAdapter(this);
        moreListView.setAdapter(mAdapter);
    }

    @Override protected void initData() {
        getDataFromNet(true);
    }

    private void getDataFromNet(boolean isFirst) {
        List mList = new ArrayList();
        for (int i = 0; i < 20; i++) {
            mList.add("1");
        }
        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override protected void updateUI() {

    }

    @Override protected void processLogic() {
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
        moreListView.setLoadMoreDataListener(new LoadMoreListView.LoadMoreDataListener() {
            @Override public void loadMore() {

            }
        });

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {

                MyApp.getMainThreadHandler().postDelayed(new Runnable() {
                    @Override public void run() {
                        mSwipeLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }
}
