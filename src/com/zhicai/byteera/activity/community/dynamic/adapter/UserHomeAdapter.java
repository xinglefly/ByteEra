package com.zhicai.byteera.activity.community.dynamic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhicai.byteera.activity.bean.Consult;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.presenter.UserHomeActivityPre;
import com.zhicai.byteera.activity.community.dynamic.view.UserHomeCollectItem;
import com.zhicai.byteera.activity.community.dynamic.view.UserHomeDynamicItem;
import com.zhicai.byteera.activity.community.dynamic.view.UserHomeProductItem;
import com.zhicai.byteera.activity.product.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/5/3. */
public class UserHomeAdapter extends BaseAdapter {
    private static final int DYNAMIC_TYPE = 0;
    private static final int COLLECT_TYPE = 2;
    private static final int PRODUCT_TYPE = 1;
    private Context mContext;
    private List mList;
    private UserHomeActivityPre userHomeActivityPre;

    public UserHomeAdapter(Context mContext, UserHomeActivityPre userHomeActivityPre) {
        this.mContext = mContext;
        this.mList = new ArrayList();
        this.userHomeActivityPre = userHomeActivityPre;
    }
    public void setData(List mList) {
        this.mList = mList;
    }
    @Override public int getCount() {
        return mList.size();
    }

    @Override public Object getItem(int position) {
        return mList.get(position);
    }

    @Override public long getItemId(int position) {
        return 0;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            switch (getItemViewType(position)) {
                case DYNAMIC_TYPE:
                    convertView = new UserHomeDynamicItem(mContext);
                    break;
                case COLLECT_TYPE:
                    convertView = new UserHomeCollectItem(mContext);
                    break;
                case PRODUCT_TYPE:
                    convertView = new UserHomeProductItem(mContext);
                    break;
            }
        }

        switch (getItemViewType(position)) {
            case DYNAMIC_TYPE:
                DynamicEntity dynamicEntity = (DynamicEntity) mList.get(position);
                if (convertView != null) {
                    ((UserHomeDynamicItem) convertView).refreshView(position, dynamicEntity, userHomeActivityPre);
                }
                break;
            case COLLECT_TYPE:
                Consult consult = (Consult) mList.get(position);
                if (convertView != null) {
                    ((UserHomeCollectItem) convertView).refreshView(position, consult, userHomeActivityPre);
                }
                break;
            case PRODUCT_TYPE:
                ProductEntity productEntity = (ProductEntity) mList.get(position);
                if (convertView != null) {
                    ((UserHomeProductItem) convertView).refreshView(position, productEntity, userHomeActivityPre);
                }
                break;

        }
        return convertView;
    }

    @Override public int getItemViewType(int position) {
        if (mList.get(position) instanceof DynamicEntity) {
            return DYNAMIC_TYPE;
        } else if (mList.get(position) instanceof ProductEntity) {
            return PRODUCT_TYPE;
        } else {
            return COLLECT_TYPE;
        }
    }

    @Override public int getViewTypeCount() {
        return 3;
    }

    public void addAllItem(List list) {
        mList.addAll(list);
    }

    public void removeAllView() {
        mList.clear();
    }
}
