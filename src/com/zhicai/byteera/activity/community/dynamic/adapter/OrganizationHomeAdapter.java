package com.zhicai.byteera.activity.community.dynamic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.presenter.OrganizationHomePre;
import com.zhicai.byteera.activity.community.dynamic.view.OrganizationDynamicItem;
import com.zhicai.byteera.activity.community.dynamic.view.OrganizationProductListItem;
import com.zhicai.byteera.activity.product.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/5/3. */
public class OrganizationHomeAdapter extends BaseAdapter {
    private static final int DYNAMIC_TYPE = 0;
    private static final int PRODUCT_TYPE = 1;
    private List mList;
    private Context mContext;
    private OrganizationHomePre organizationHomePre;

    public OrganizationHomeAdapter(Context mContext, OrganizationHomePre organizationHomePre) {
        this.mContext = mContext;
        this.organizationHomePre = organizationHomePre;
        this.mList = new ArrayList();
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
                    convertView = new OrganizationDynamicItem(mContext);
                    break;
                case PRODUCT_TYPE:
                    convertView = new OrganizationProductListItem(mContext);
                    break;
            }
        }
        switch (getItemViewType(position)) {
            case DYNAMIC_TYPE:
                DynamicEntity entity = (DynamicEntity) mList.get(position);
                if (convertView != null) {
                    ((OrganizationDynamicItem) convertView).refreshView(entity, position, organizationHomePre);
                }
                break;
            case PRODUCT_TYPE:
                ProductEntity productEntity = (ProductEntity) mList.get(position);
                if (convertView != null) {
                    ((OrganizationProductListItem) convertView).refreshView(productEntity, position, organizationHomePre);
                }
                break;
        }
        return convertView;
    }

    @Override public int getViewTypeCount() {
        return 2;
    }

    @Override public int getItemViewType(int position) {

        if (mList.get(position) instanceof DynamicEntity) {
            return DYNAMIC_TYPE;
        } else {
            return PRODUCT_TYPE;
        }
    }

    public void addAllItem(List list) {
        this.mList.addAll(list);
    }

    public void removeAllViews() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }
}
