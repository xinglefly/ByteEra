package com.zhicai.byteera.activity.product.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.product.P2P.P2PPre;
import com.zhicai.byteera.activity.product.ProductDetailActivity;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.LoadMoreListView;

import butterknife.ButterKnife;

/** Created by bing on 2015/4/20. */
public class AllView extends FrameLayout {
    private Context mContext;
    public LoadMoreListView mListView;
    public DrawerLayoutAdapter mAdapter;
    public DrawerRightTitleView titleView;
    private P2PPre p2PPre;
    private boolean[] isReverse = {false, false, false};

    public void setPresenter(P2PPre p2PPre) {
        this.p2PPre = p2PPre;
    }

    public AllView(Context context) {
        this(context, null);
    }

    public AllView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AllView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.drawer_right, this, true);
        titleView = ButterKnife.findById(view,R.id.title_view);
        mListView = ButterKnife.findById(view,R.id.lv_drawer);
        initListView();
    }

    private void initListView() {
        mAdapter = new DrawerLayoutAdapter(mContext, p2PPre);
        mListView.setAdapter(mAdapter);
        setListViewListener();
    }

    private void setListViewListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductEntity productEntity = (ProductEntity) parent.getAdapter().getItem(position);
                if (!TextUtils.isEmpty(productEntity.getProductId()))
                    ActivityUtil.startActivity((Activity) mContext, new Intent(mContext,ProductDetailActivity.class).putExtra("productId",productEntity.getProductId()));
            }
        });
    }


}
