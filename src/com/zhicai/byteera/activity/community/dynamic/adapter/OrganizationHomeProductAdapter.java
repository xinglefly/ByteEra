package com.zhicai.byteera.activity.community.dynamic.adapter;

import android.content.Context;

import com.zhicai.byteera.activity.community.classroom.view.BaseHolder;
import com.zhicai.byteera.activity.community.classroom.view.LieeberAdapter;
import com.zhicai.byteera.activity.community.dynamic.view.ClickToLoadMoreView;
import com.zhicai.byteera.activity.community.dynamic.view.ClickToLoadMoreView.LoadMoreListener;
import com.zhicai.byteera.activity.community.dynamic.view.ClickToLoadMoreView.State;
import com.zhicai.byteera.activity.community.dynamic.view.OrganizationHomeProductItem;
import com.zhicai.byteera.activity.product.entity.ProductEntity;

/** Created by bing on 2015/5/3. */
public class OrganizationHomeProductAdapter extends LieeberAdapter {
    private static int LIST_TYPE = 0;
    private static int MORE_TYPE = 1;

    public OrganizationHomeProductAdapter(Context context) {
        super(context);
    }


    public interface LoadingMoreListener {
        void loadingMore();
    }

    public void setLoadMoreListener(LoadMoreListener listener) {
        this.moreListener = listener;
    }

    private LoadMoreListener moreListener;

    @Override
    protected BaseHolder checkType(int position) {
        if (getItemType(position) == LIST_TYPE) {
            return new OrganizationHomeProductItem(mContext);
        } else if (getItemType(position) == MORE_TYPE) {
            final ClickToLoadMoreView footView = new ClickToLoadMoreView(mContext);
            footView.setLoadMoreListener(new LoadMoreListener() {
                @Override public void loadingMore() {
                    if (moreListener != null) {
                        moreListener.loadingMore();
                        footView.setLoadState(State.LOAD);
                    }
                }
            });
            return footView;
        }
        return null;
    }

    @Override
    public BaseHolder getHolder(BaseHolder holder, int position) {
        if (holder instanceof ClickToLoadMoreView) {
            ((ClickToLoadMoreView) holder).setLoadState(State.IDLE);
        }
        return holder;
    }



    @Override
    protected int getItemType(int position) {
        if (mList.get(position) instanceof ProductEntity) {
            return LIST_TYPE;
        } else {
            return MORE_TYPE;
        }
    }

    @Override
    public int getItemTypeCount() {
        return 2;
    }
}
