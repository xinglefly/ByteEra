package com.zhicai.byteera.activity.knowwealth.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.umeng.analytics.MobclickAgent;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.bean.Consult;
import com.zhicai.byteera.activity.knowwealth.presenter.KnowWealthPre;
import com.zhicai.byteera.activity.knowwealth.viewinterface.KnowWealthIV;
import com.zhicai.byteera.commonutil.LogUtil;
import com.zhicai.byteera.commonutil.Utils;
import com.zhicai.byteera.widget.LoadMoreListView;
import com.zhicai.byteera.widget.LoadingPage;
import com.zhicai.byteera.widget.MySwipeRefreshLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

@SuppressWarnings("unused")
@SuppressLint("ValidFragment")
public class PageFragment extends BaseFragment implements KnowWealthIV {

    @Bind(R.id.list_view) LoadMoreListView mListView;
    @Bind(R.id.swipe_layout) MySwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.loading_page) LoadingPage loadingPage;
    private KnowWealthAdpater mAdapter;
    KnowWealthPre knowWealthPre = new KnowWealthPre(this);
    private int type = 1;
    private boolean isFirstLoad;

    public PageFragment() {}

    public PageFragment(int type) {
        this.type = type;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFirstLoad){
            LogUtil.d("isvisibletouser: %s, type: %s", isVisibleToUser, type);
            knowWealthPre.getData(type);
            isFirstLoad = true;
        }
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.financial_page_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        knowWealthPre.setContext();
        initListView();
        MobclickAgent.onEventValue(getActivity(), "PageFragment", null, 3);
    }


    private void initListView() {
        mAdapter = new KnowWealthAdpater(getActivity(), knowWealthPre);
        mListView.setAdapter(mAdapter);
        mListView.setLoadMoreDataListener(new LoadMoreListView.LoadMoreDataListener() {
            @Override
            public void loadMore() {
                knowWealthPre.getData(type);
            }
        });

        mSwipeLayout.setOnRefreshListener(new MySwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                knowWealthPre.getData(type);
            }
        });
    }

    @OnItemClick(R.id.list_view)
    public void onItemClick(AdapterView<?> parent,int position){
        Consult item = (Consult) parent.getAdapter().getItem(position);
        if (item!=null) knowWealthPre.intentToKnowWealthDetailActivity(item);
    }


    @Override public Context getContext() {
        return getActivity();
    }

    @Override public void removeAllViews() {
        mAdapter.removeAllViews();
    }

    @Override
    public void addItem(Object item) {
        mAdapter.addItem(item);
    }


    @Override public void loadComplete() {
        mListView.loadComplete();
    }

    @Override public void refreshFinish() {
        mSwipeLayout.setRefreshing(false);
    }


    @Override public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }


    @Override public Object getItem(int position) {
        return mAdapter.getItem(position);
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
    }
}
