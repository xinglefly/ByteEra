package com.zhicai.byteera.activity.community.classroom.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.classroom.entity.P2pBannerViewEntity;

/**
 * Created by bing on 2015/4/19.
 */
public class P2pBannerView extends BaseHolder {

    private ImageView ivBanner;

    public P2pBannerView(Context context) {
        super(context);
    }

    @Override
    protected void initEvent() {

    }
    @Override
    protected View initView() {
       View view =  LayoutInflater.from(context).inflate(R.layout.p2p_banner_view, null);
        ivBanner = (ImageView) view.findViewById(R.id.iv_banner);
        return view;
    }

    @Override
    public void refreshView() {
        P2pBannerViewEntity entity = (P2pBannerViewEntity) getData();
        ivBanner.setBackgroundResource(entity.getImageIds());
    }
}
