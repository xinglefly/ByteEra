package com.zhicai.byteera.activity.community.dynamic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.community.dynamic.adapter.DynamicAdapter;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.DynamicFragmentIV;
import com.zhicai.byteera.activity.community.dynamic.presenter.DynamicFragmentPre;
import com.zhicai.byteera.activity.event.NetWorkEvent;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.Utils;
import com.zhicai.byteera.widget.LoadMoreListView;
import com.zhicai.byteera.widget.LoadingPage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemLongClick;
import de.greenrobot.event.EventBus;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

@SuppressWarnings("unused")
public class DynamicFragment extends BaseFragment implements DynamicFragmentIV {

    @Bind(R.id.list_view) LoadMoreListView mListView;
    @Bind(R.id.swipe_layout) WaveSwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.loading_page) LoadingPage mLoadingPage;
    private DynamicAdapter mAdapter;
    private DynamicFragmentPre dynamicFragmentPre = new DynamicFragmentPre(this);


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dynamic_main, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this,getView());
        dynamicFragmentPre.setContext();
        dynamicFragmentPre.setFragment();
        initListView();
        initLoadingPge();
    }

    private void initListView() {
        mSwipeLayout.setWaveColor(getResources().getColor(R.color.head_view_bg));
        mSwipeLayout.setColorSchemeResources(R.color.white);
        mAdapter = new DynamicAdapter(getActivity(), dynamicFragmentPre);
        mListView.setAdapter(mAdapter);

        dynamicFragmentPre.getDataFromNet(true);

        mListView.setLoadMoreDataListener(new LoadMoreListView.LoadMoreDataListener() {
            @Override
            public void loadMore() {
                dynamicFragmentPre.getDataFromNet(false);
            }
        });

        mSwipeLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dynamicFragmentPre.getDataFromNet(true);
            }
        });
    }

    private void initLoadingPge() {
        mLoadingPage.showPage(Constants.STATE_LOADING);
        mLoadingPage.setOnRetryClickListener(new LoadingPage.RetryClickListener() {
            @Override
            public void retryClick() {
                dynamicFragmentPre.getDataFromNet(true);
            }
        });
    }

    @OnItemLongClick(R.id.list_view)
    public boolean onItemLongClickListener(int position){
        dynamicFragmentPre.showDeleteDialog(position);
        return false;
    }

    @Override public Activity getContext() {
        return getActivity();
    }

    @Override public void addListViewItem(Object o) {
        mAdapter.addItem(o);
    }

    @Override public void addAllItem(List list) {
        mAdapter.addAllItem(list);
    }

    @Override public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override public void hidePage() {
        mLoadingPage.hidePage();
    }


    @Override public void refreshFinish() {
        MyApp.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        }, 1500);
    }

    @Override public void removeAllViews() {
        mAdapter.removeAllView();
    }

    @Override public void addItem(Object o) {
        mAdapter.addItem(o);
    }

    @Override public void loadComplete() {
        mListView.loadComplete();
    }

    @Override public Object getItem(int position) {
        return mAdapter.getItem(position);
    }

    @Override public void removeItemAtPosition(int position) {
        mAdapter.removeItemAtPosition(position);
    }

    @Override public Fragment getFragment() {
        return this;
    }

    @Override public void notifyDataInvidated() {
        mAdapter.notifyDataSetInvalidated();
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_DYNAMIC_FRAGMENT && resultCode == Constants.RESULT_SUCCESS) {
            int position = data.getIntExtra("position", 0);
            DynamicEntity entity = (DynamicEntity) data.getSerializableExtra("dynamic_entity");
            mAdapter.refreshItem(position, entity);
        }
        if (requestCode == Constants.REQUEST_FRESH_DYNAMIC_FRAGMENT) {
            dynamicFragmentPre.getDataFromNet(true);
        }
    }

    public DynamicFragmentPre getPre() {
        return dynamicFragmentPre;
    }


    public void onEventMainThread(NetWorkEvent event) {
        Log.d("DynamicFragment", "接收onEventMainThread信息 NetWorkEvent-->" + event.isNetwork());
        dynamicFragmentPre.getDataFromNet(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.setingNetWork(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
