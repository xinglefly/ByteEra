package com.zhicai.byteera.activity.product.recommend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.product.ProductDetailActivity;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.ZhiCaiLRefreshListView;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Bind;

public class RecommendFragment extends BaseFragment implements RecommendIV {
    @Bind(R.id.list_view) ZhiCaiLRefreshListView mListView;
    private RecommendAdapter mAdapter;
    private RecommendPre recommendPre = new RecommendPre(this);

    @Override public Context getContext() {
        return getActivity();
    }
    @Override public void refreshListView(ArrayList<ProductEntity> productEntities) {
        mAdapter.setData(productEntities);
        mAdapter.notifyDataSetChanged();
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_attention_fragment, container, false);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this,getView());
        recommendPre.getContext();
        initListView();
    }

    private void initListView() {
        mAdapter = new RecommendAdapter(getActivity(), recommendPre);
        mListView.getListView().setAdapter(mAdapter);
        mListView.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductEntity productEntity = (ProductEntity) parent.getAdapter().getItem(position);
                if (!TextUtils.isEmpty(productEntity.getProductId()))
                    ActivityUtil.startActivity(getActivity(), new Intent(getActivity(), ProductDetailActivity.class).putExtra("productId", productEntity.getProductId()));
            }
        });
        mListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                recommendPre.refreshListView();
            }
        });
    }
    @Override public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            recommendPre.refreshListView();
        }
    }
}
