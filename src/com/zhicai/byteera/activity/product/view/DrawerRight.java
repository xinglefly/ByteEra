package com.zhicai.byteera.activity.product.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.product.P2P.DrawerRightIV;
import com.zhicai.byteera.activity.product.P2P.P2PPre;
import com.zhicai.byteera.activity.product.ProductDetailActivity;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.LoadMoreListView;

import java.util.List;
import butterknife.ButterKnife;

/** Created by bing on 2015/4/20. */
public class DrawerRight extends RelativeLayout implements DrawerRightIV {
    private Context mContext;
    public LoadMoreListView lvDrawer;
    public DrawerLayoutAdapter mAdapter;
    public DrawerRightTitleView titleView;
    private P2PPre p2PPre;
    private boolean isOpen = false;
    private float downX;
    private float downY;
    private boolean[] isReverse = {false, false, false};

    public DrawerRight(Context context) {
        this(context, null);
    }

    public DrawerRight(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerRight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.drawer_right, this, true);
        inflateLayout(view);
    }

    private void inflateLayout(View view) {
        view.setBackgroundColor(Color.parseColor("#aaaaaa"));
        LayoutParams params = new LayoutParams(1, LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.bringToFront();
        lvDrawer = ButterKnife.findById(view,R.id.lv_drawer);
        titleView = ButterKnife.findById(view, R.id.title_view);
        setListViewListener();
    }

    private void setListViewListener() {
        lvDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductEntity productEntity = (ProductEntity) parent.getAdapter().getItem(position);
                if (!TextUtils.isEmpty(productEntity.getProductId()))
                    ActivityUtil.startActivity((Activity) mContext, new Intent(mContext,ProductDetailActivity.class).putExtra("productId",productEntity.getProductId()));

            }
        });

    }


    public boolean openMenu() {
        //使用属性动画来执行
        if (!isOpen) {
            this.animate().translationX(0).setDuration(200).setInterpolator(new AccelerateDecelerateInterpolator());
            isOpen = true;
        }
        return true;
    }

    public boolean closeMenu() {
        if (isOpen) {
            this.animate().translationX(this.getMeasuredWidth()).setDuration(200).setInterpolator(new AccelerateDecelerateInterpolator());
            isOpen = false;
        }
        return false;
    }

    public boolean isOpen() {
        return isOpen;
    }

    float dx = 0;
    float dy = 0;

    @Override public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getRawX();
        float currentY = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                dx = currentX - downX;
                dy = currentY - downY;
                if (dx > 0) {       //只能够向右边滑动
                    DrawerRight.this.setTranslationX(dx);
                }
                break;
            case MotionEvent.ACTION_UP:
                dx = currentX - downX;
                if (dx >= this.getMeasuredWidth() / 2) {
                    this.animate().translationX(this.getMeasuredWidth()).setDuration(100);  //从当前位置滑动到指定位置
                    isOpen = false;
                } else {
                    this.animate().translationX(0).setDuration(100);
                    isOpen = true;
                }
                break;
        }
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getRawX();
                downY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX = Math.abs(ev.getRawX() - downX);
                float distanceY = (int) Math.abs(ev.getRawY() - downY);
                if (distanceX > distanceY && distanceX > 5) {
                    result = true;
                } else {
                    result = false;
                }
                break;
        }
        return result;
    }

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    public void setPresenter(P2PPre p2PPre) {
        this.p2PPre = p2PPre;
        this.p2PPre.setDrawerRightIV(this);
        mAdapter = new DrawerLayoutAdapter(mContext, p2PPre);
        lvDrawer.setAdapter(mAdapter);
    }

    @Override
    public void refreshListView(List<ProductEntity> productList,boolean isFirst) {
        if (isFirst)
            mAdapter.setData(productList);
        else
            mAdapter.addAllItem(productList);
    }
}
