package com.zhicai.byteera.activity.product.P2P;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.event.NetWorkEvent;
import com.zhicai.byteera.activity.product.ProductFragment;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.activity.product.entity.ProductParameter;
import com.zhicai.byteera.activity.product.view.AllView;
import com.zhicai.byteera.activity.product.view.DrawerRight;
import com.zhicai.byteera.activity.product.view.DrawerRightTitleView;
import com.zhicai.byteera.activity.product.view.ProductP2pAdapter;
import com.zhicai.byteera.widget.LoadMoreListView;
import com.zhicai.byteera.widget.MySwipeRefreshLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;

@SuppressLint("ValidFragment")
@SuppressWarnings("unused")
public class P2pFragment extends BaseFragment implements P2PIV {

    @Bind(R.id.list_view) LoadMoreListView mListView;
    @Bind(R.id.swipe_layout) MySwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.drawer_right) DrawerRight mDrawerRight;
    @Bind(R.id.all_view) AllView allView;
    @Bind(R.id.company_view) RelativeLayout companyView;
    private ProductP2pAdapter mAdapter;
    private P2PPre p2PPre = new P2PPre(this);
    private boolean showAllView = false;
    private boolean isChanged;
    private boolean incomeSort = true;
    private boolean limitSort = true;
    private boolean isFirstload;
    private ProductParameter parameter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }


    public P2pFragment() {
    }

    public P2pFragment(ProductFragment productFragment,final int position,final int type) {
        super();
        if (parameter==null) parameter = new ProductParameter();
        productFragment.setQieHuanClickListener(new ProductFragment.QieHuanClickListener() {
            @Override
            public void qiehuan() {
                changeView();
            }
        }, position);
        parameter.setType(type);
    }

    private void changeView() {
        if (showAllView) {
            allView.setVisibility(View.GONE);
            companyView.setVisibility(View.VISIBLE);
            showAllView = false;
        } else {
            showAllView = true;
            allView.setVisibility(View.VISIBLE);
            companyView.setVisibility(View.GONE);
            if (!isChanged)
                isChanged = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFirstload){
            Log.d("TestFragment", "type-->" + parameter.getType());
            p2PPre.refreshListView(parameter.getType());
            p2PPre.getProductList(new ProductParameter(true, "", parameter.getType(), 0, 0));
            isFirstload = true;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_p2p, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        p2PPre.setContext();
        initListView();
        initAllViewListView();
        initDrawerRightList();
        MobclickAgent.onEventValue(getActivity(),"p2pFragment",null,2);
    }



    private void initListView() {
        mAdapter = new ProductP2pAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState != SCROLL_STATE_IDLE) {
                    mDrawerRight.closeMenu();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        mSwipeLayout.setOnRefreshListener(new MySwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                p2PPre.refreshListView(parameter.getType());
            }
        });
    }



    private void initAllViewListView() {
        allView.setPresenter(p2PPre);
        allView.mListView.setLoadMoreDataListener(new LoadMoreListView.LoadMoreDataListener() {
            @Override
            public void loadMore() {
                p2PPre.getProductList(new ProductParameter(false, "", parameter.getType(), parameter.getIncome_value(), parameter.getLimit_value()));
            }
        });
        allView.titleView.setDrawerTitleListener(new DrawerRightTitleView.DrawerTitleListener() {
            @Override
            public void clickLeft() {
                clearAllViewAdapter();
                inComeSort("");
            }

            @Override
            public void clickMiddle() {
                clearAllViewAdapter();
                limitSort("");
            }

            @Override
            public void clickRight() {

            }
        });
    }



    private void initDrawerRightList() {
        mDrawerRight.setPresenter(p2PPre);
        mDrawerRight.lvDrawer.setLoadMoreDataListener(new LoadMoreListView.LoadMoreDataListener() {
            @Override
            public void loadMore() {
                p2PPre.getProductList(new ProductParameter(false, parameter.getCompany_id(), parameter.getType(), parameter.getIncome_value(), parameter.getLimit_value()));
            }
        });

        mDrawerRight.titleView.setDrawerTitleListener(new DrawerRightTitleView.DrawerTitleListener() {
            @Override
            public void clickLeft() {
                clearDrawerAdapter();
                inComeSort(parameter.getCompany_id());
            }

            @Override
            public void clickMiddle() {
                clearDrawerAdapter();
                limitSort(parameter.getCompany_id());
            }

            @Override
            public void clickRight() {

            }
        });
    }

    private void limitSort(String company_id) {
        parameter.setIncome_value(3);
        if (limitSort) {
            parameter.setLimit_value(1);
            p2PPre.getProductList(new ProductParameter(true, company_id, parameter.getType(), parameter.getIncome_value(), parameter.getLimit_value()));
            limitSort = false;
        } else {
            parameter.setLimit_value(2);
            p2PPre.getProductList(new ProductParameter(true, company_id, parameter.getType(), parameter.getIncome_value(), parameter.getLimit_value()));
            limitSort = true;
        }
    }

    private void inComeSort(String company_id) {
        parameter.setIncome_value(2);
        if (incomeSort) {
            parameter.setLimit_value(1);
            p2PPre.getProductList(new ProductParameter(true, company_id, parameter.getType(), parameter.getIncome_value(), parameter.getLimit_value()));
            incomeSort = false;
        } else {
            parameter.setLimit_value(2);
            p2PPre.getProductList(new ProductParameter(true, company_id, parameter.getType(), parameter.getIncome_value(), parameter.getLimit_value()));
            incomeSort = true;
        }
    }

    private void clearDrawerAdapter() {
        mDrawerRight.mAdapter.clearData();
    }

    private void clearAllViewAdapter(){
        allView.mAdapter.clearData();
    }

    @OnItemClick(R.id.list_view)
    public void onItemClick(AdapterView<?> parent,int position){
        if (position<mAdapter.getCount()){
            clearDrawerAdapter();
            FinancingCompanyEntity item = (FinancingCompanyEntity) parent.getAdapter().getItem(position);
            mAdapter.setSelectItem(position);
            parameter.setCompany_id(item.getCompany_id());
            p2PPre.getProductList(new ProductParameter(true, parameter.getCompany_id(), parameter.getType(), 0, 0));
        }
    }


    @Override public Context getContext() {
        return getActivity();
    }

    @Override public void openDrawerRight(List<ProductEntity> productList,boolean isFirst) {
        if (isFirst) mDrawerRight.mAdapter.setData(productList);
        else mDrawerRight.mAdapter.addAllItem(productList);
        mDrawerRight.openMenu();
    }

    @Override
    public void loadData(List<ProductEntity> productList,boolean isFirst) {
        if (isFirst) allView.mAdapter.setData(productList);
        else allView.mAdapter.addAllItem(productList);
    }


    @Override public void finishRefresh() {
        mSwipeLayout.setRefreshing(false);
    }


    @Override public void loadComplete(String listViewName) {
        switch (listViewName){
            case "mDrawerRight":
                mDrawerRight.lvDrawer.loadComplete();
                break;
            case "allView":
                allView.mListView.loadComplete();
                break;
            case "mListView":
                mListView.loadComplete();
                break;
        }
    }

    @Override public void refreshListView(List<FinancingCompanyEntity> companyEntities) {
        mAdapter.setData(companyEntities);
        mAdapter.notifyDataSetChanged();
    }

    public void onEventMainThread(NetWorkEvent event) {
        Log.d("p2pFragment", "接收onEventMainThread信息 NetWorkEvent-->" + event.isNetwork());
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

}
