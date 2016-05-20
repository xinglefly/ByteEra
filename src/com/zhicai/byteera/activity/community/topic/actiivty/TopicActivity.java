package com.zhicai.byteera.activity.community.topic.actiivty;

import android.content.Context;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.topic.adapter.TopicAdapter;
import com.zhicai.byteera.activity.community.topic.entity.TopicEntity;
import com.zhicai.byteera.activity.community.topic.presenter.TopicActivityPre;
import com.zhicai.byteera.activity.community.topic.viewinterface.TopicFragmentIV;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.LoadMoreListView;

import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

@SuppressWarnings("unused")
public class TopicActivity extends BaseActivity implements TopicFragmentIV {

    @Bind(R.id.head_title) HeadViewMain headTitle;
    @Bind(R.id.list_view) LoadMoreListView mListVIew;
    @Bind(R.id.swipe_layout) WaveSwipeRefreshLayout mSwipeLayout;

    private TopicAdapter mAdapter;
    private TopicActivityPre topicActivityPre;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.topic_fragment);
        ButterKnife.bind(this);
        topicActivityPre = new TopicActivityPre(this);
    }

    @Override
    protected void initData() {
        initListView();
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        headTitle.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }


    private void initListView() {
        mSwipeLayout.setWaveColor(getResources().getColor(R.color.head_view_bg));
        mSwipeLayout.setColorSchemeResources(R.color.white);
        mAdapter = new TopicAdapter(this);
        mListVIew.setAdapter(mAdapter);
        mListVIew.setLoadMoreDataListener(new LoadMoreListView.LoadMoreDataListener() {
            @Override
            public void loadMore() {
                mListVIew.loadComplete();
            }
        });
        mSwipeLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                topicActivityPre.getDataFromNet(true);
            }
        });
        topicActivityPre.getDataFromNet(true);
    }

    @OnItemClick(R.id.list_view)
    public void onItemClick(int position){
        if (mAdapter.getCount() > position) {
            topicActivityPre.intentToTopicDetailActivity(position);
        }
    }


    @Override public Context getContext() {
        return baseContext;
    }

    @Override public void refreshFinish() {
        MyApp.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        }, 1500);
    }

    @Override public void freshListView(List<TopicEntity> topicItemEntities) {
        mAdapter.setData(topicItemEntities);
        mAdapter.notifyDataSetChanged();
    }

    @Override public void loadComplete() {
        mListVIew.loadComplete();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
