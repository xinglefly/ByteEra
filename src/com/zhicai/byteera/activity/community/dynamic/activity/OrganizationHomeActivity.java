package com.zhicai.byteera.activity.community.dynamic.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.adapter.OrganizationHomeAdapter;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.OrganizationHomeIV;
import com.zhicai.byteera.activity.community.dynamic.presenter.OrganizationHomePre;
import com.zhicai.byteera.activity.community.dynamic.view.OrganizationHomeHeadView;
import com.zhicai.byteera.activity.community.eventbus.OrganizationEvent;
import com.zhicai.byteera.activity.community.eventbus.PublishGroupDynamicEvent;
import com.zhicai.byteera.activity.product.ProductDetailActivity;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.widget.HasHeadLoadMoreListView;
import com.zhicai.byteera.widget.HasHeadLoadMoreListViewNoMeasure;
import com.zhicai.byteera.widget.HeadViewMain;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;

@SuppressWarnings("unused")
public class OrganizationHomeActivity extends BaseActivity implements  OrganizationHomeIV, OnClickListener {
    private static final String TAG = OrganizationHomeActivity.class.getSimpleName();

    @Bind(R.id.title_view) HeadViewMain mTitle;
    @Bind(R.id.head_view) OrganizationHomeHeadView mHeadView;
    @Bind(R.id.list_view) HasHeadLoadMoreListViewNoMeasure mListView;
    @Bind(R.id.iv_organization_right) ImageView iv_organization_right;
    @Bind(R.id.tv_moneynum) TextView tvMoneyNum;
    @Bind(R.id.tv_inverstornum) TextView tvInverstorNum;
    @Bind(R.id.rl_dianping)RelativeLayout rlDianPin;
    private TextView tvDynamic;
    private TextView tvProduct;
    private ImageView ivIndicator;
    private OrganizationHomeAdapter mAdapter;
    private OrganizationHomePre organizationHomePre;
    private boolean showBottom = true;
    private boolean isAttention;


    @Override protected void loadViewLayout() {
        setContentView(R.layout.activity_organization_home);
        ButterKnife.bind(this);
        organizationHomePre = new OrganizationHomePre(this);
        addHeadViewClickListener();
        initListView();
        EventBus.getDefault().register(this);
    }

    private void addHeadViewClickListener() {
        mHeadView.getDoFocusText().setOnClickListener(this);
        iv_organization_right.setOnClickListener(this);
        rlDianPin.setOnClickListener(this);
    }

    private void initListView() {
        View view = LayoutInflater.from(baseContext).inflate(R.layout.organization_home_header_layout, null);
        mListView.setHeaderView(view);
        iniCategoryView(view);
        mAdapter = new OrganizationHomeAdapter(baseContext, organizationHomePre);
        mListView.setAdapter(mAdapter);
        mListView.setLoadMoreDataListener(new HasHeadLoadMoreListViewNoMeasure.LoadMoreDataListener() {
            @Override
            public void loadMore() {
                organizationHomePre.loadMore();
            }
        });
    }

    private void iniCategoryView(View view) {
        tvDynamic = ButterKnife.findById(view, R.id.tv_dynamic);
        tvProduct = ButterKnife.findById(view,R.id.tv_product);
        ivIndicator = ButterKnife.findById(view,R.id.iv_indicator);
        RelativeLayout rlDynamic = ButterKnife.findById(view,R.id.rl_dynamic);
        RelativeLayout rlProduct = ButterKnife.findById(view,R.id.rl_product);
        rlDynamic.setOnClickListener(this);
        rlProduct.setOnClickListener(this);
    }

    @OnItemClick(R.id.list_view)
    public void onItemClickListener(AdapterView<?> parent,int position){
        ProductEntity productEntity = (ProductEntity) parent.getAdapter().getItem(position);
        if (!TextUtils.isEmpty(productEntity.getProductId()))
            ActivityUtil.startActivity(this, new Intent(this,ProductDetailActivity.class).putExtra("productId",productEntity.getProductId()));
    }


    @Override
    protected void initData() {
        organizationHomePre.getDataFromBefore();
        organizationHomePre.getAttributeFromNet();
        organizationHomePre.setSelect(0);
    }

    @Override
    protected void updateUI() {

    }


    @Override
    protected void processLogic() {
        mListView.setOnScrollListener(new HasHeadLoadMoreListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    showBottom = true;
                    organizationHomePre.showBottom(rlDianPin);
                } else {
                    if (showBottom) {
                        showBottom = false;
                        organizationHomePre.hideBottom(rlDianPin);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        mTitle.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_dynamic:
                organizationHomePre.setSelect(0);
                tvDynamic.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                tvProduct.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                rlDianPin.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_product:
                organizationHomePre.setSelect(1);
                tvProduct.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                tvDynamic.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                rlDianPin.setVisibility(View.GONE);
                break;
            case R.id.tv_focus:
                organizationHomePre.doFocus();
                break;
            case R.id.tv_fans:
                organizationHomePre.getFans();
                break;
            case R.id.iv_organization_right:
                organizationHomePre.intentToOrganizationInf(isAttention);
                break;
            case R.id.rl_dianping:
                organizationHomePre.intentToPublish();
                break;
        }
    }



    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_PRODUCT_HOME && resultCode == Constants.RESULT_SUCCESS) {
            organizationHomePre.setSelect(1);
        }
        if (requestCode == Constants.REQUEST_ORGANIZATION_HOME && resultCode == Constants.RESULT_SUCCESS) {
            organizationHomePre.setSelect(0);
        }
    }

    @Override public Activity getContext() {
        return this;
    }

    @Override public void setHead(String headPortrait) {
        mHeadView.setHead(headPortrait);
    }

    @Override public void setFansCount(String fansCount) {
        mHeadView.setFansNum(fansCount);
    }

    @Override
    public void setMoneyNum(String num) {
        tvMoneyNum.setText("昨日成交金额 " + num + "万元");
    }

    @Override
    public void setPeopleNum(String num) {
        tvInverstorNum.setText("昨日投资人数 " + num + "人");
    }


    @Override public void setHeadUnFocus() {
        mHeadView.getDoFocusText().setVisibility(View.VISIBLE);
        mHeadView.getDoFocusText().setText("关注");
        isAttention = false;
    }

    @Override public void setHeadIsFocus() {
        mHeadView.getDoFocusText().setVisibility(View.GONE);
        isAttention = true;
    }

    @Override public void setDynamicButtomChecked() {
        ivIndicator.animate().x(UIUtils.getScreenWidth(this) / 4.0f - UIUtils.dip2px(this, 10))
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator()).start();
    }

    @Override public void setProductButtomChecked() {
        ivIndicator.animate().x(UIUtils.getScreenWidth(this) * 3 / 4.0f - UIUtils.dip2px(this, 10))
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator()).start();
    }

    @Override public void loadComplete() {
        mListView.loadComplete();
    }

    @Override public void removeAllViews() {
        mAdapter.removeAllViews();
    }

    @Override public void setTitleName(String institutionName) {
        mTitle.setTitleName(institutionName);
    }

    @Override public String getFansCount() {
        return mHeadView.getFansNum();
    }


    @Override public void setData(List list) {
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override public void addAllItem(List list) {
        mAdapter.addAllItem(list);
        mAdapter.notifyDataSetChanged();
    }


    public void onEventMainThread(OrganizationEvent event) {
        Log.d(TAG,"接收onEventMainThread信息-->"+event.getPosition());
        boolean attention = event.isAttention();
        int position = event.getPosition();
        sendBroadCast(position, attention);
        setHeadUnFocus();
    }


    public void onEventMainThread(PublishGroupDynamicEvent event){
        Log.d(TAG, "接收onEventMainThread信息 PublishDynamicEvent-->" + event.isRefresh());
        if (event.isRefresh()){
            organizationHomePre.getDynamicData(true);
        }
    }


    public void sendBroadCast(int position,boolean isFocus) {
        sendBroadcast(new Intent(Constants.ORGANIZATIONACTION).putExtra("position", position).putExtra("isFocus", isFocus));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
