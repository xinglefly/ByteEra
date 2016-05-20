package com.zhicai.byteera.activity.product.MyAttention;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.activity.product.ProductDetailActivity;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.LoadMoreListView;
import com.zhicai.byteera.widget.MySwipeRefreshLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import me.drakeet.materialdialog.MaterialDialog;

/** 我的关注 @author xinglefly */
public class MyAttentionFragment extends BaseFragment implements MyAttentionFragmentIV {
    @Bind(R.id.list_view) LoadMoreListView mListView;
    @Bind(R.id.swipe_layout) MySwipeRefreshLayout mSwipeLayout;
    private MyAttentionAdapter mAdapter;
    private MyAttentionFragmentPre myAttentionFragmentPre = new MyAttentionFragmentPre(this);

    @Override public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            myAttentionFragmentPre.refreshListView();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_attention_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this,getView());
        myAttentionFragmentPre.getContext();
        initListView();
    }

    private void initListView() {
        mAdapter = new MyAttentionAdapter(getActivity(), myAttentionFragmentPre);
        mListView.setAdapter(mAdapter);
        mSwipeLayout.setOnRefreshListener(new MySwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myAttentionFragmentPre.refreshListView();
            }
        });
    }

    @OnItemClick(R.id.list_view)
    public void onItemClickListener(AdapterView<?> parent,int position){
        ProductEntity productEntity = (ProductEntity) parent.getAdapter().getItem(position);
        if (!TextUtils.isEmpty(productEntity.getProductId()))
            ActivityUtil.startActivity(getActivity(), new Intent(getActivity(),ProductDetailActivity.class).putExtra("productId",productEntity.getProductId()));
    }

    @Override public Context getContext() {
        return getActivity();
    }

    @Override public void finishRefresh() {
        MyApp.getMainThreadHandler().postDelayed(new Runnable() {
            @Override public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        }, 1500);
    }

    @Override public void listViewRefreshView(List<ProductEntity> mList) {
        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override public void showCheckLoginDialog() {
        final MaterialDialog materialDialog = new MaterialDialog(getActivity());
        materialDialog.setTitle("登录").setMessage("是否现在进行登录?").setPositiveButton("确定", new View.OnClickListener() {
            @Override public void onClick(View v) {
                ActivityUtil.startActivity(getActivity(), new Intent(getActivity(), LoginActivity.class));
                mSwipeLayout.setRefreshing(false);
                materialDialog.dismiss();
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override public void onClick(View v) {
                materialDialog.dismiss();
                mSwipeLayout.setRefreshing(false);
            }
        }).show();
    }
}
