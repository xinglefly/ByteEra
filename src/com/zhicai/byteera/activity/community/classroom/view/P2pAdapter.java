package com.zhicai.byteera.activity.community.classroom.view;

import android.content.Context;

import com.zhicai.byteera.activity.community.classroom.entity.P2pBannerViewEntity;
import com.zhicai.byteera.activity.community.classroom.entity.P2pListEntity;

/**
 * Created by bing on 2015/4/19.
 */
public class P2pAdapter extends  LieeberAdapter {
    private static int BANNER_TYPE = 0;
    private static int LIST_TYPE = 1;
    private static int MORE_TYPE = 2;

    public P2pAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected BaseHolder checkType(int position) {
        if (getItemType(position) == BANNER_TYPE) {
            return new P2pBannerView(mContext);
        } else if (getItemType(position) == LIST_TYPE) {
            return new P2plistitem(mContext);
        }
        return null;
    }
    @Override
    protected int getItemType(int position) {
        if (mList.get(position) instanceof P2pBannerViewEntity) {
            return BANNER_TYPE;
        } else if (mList.get(position) instanceof P2pListEntity) {
            return LIST_TYPE;
        }else{
            return MORE_TYPE;
        }
    }

    @Override
    public int getItemTypeCount() {
        return 3;
    }

}
