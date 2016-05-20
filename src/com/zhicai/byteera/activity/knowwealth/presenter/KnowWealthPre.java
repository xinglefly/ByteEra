package com.zhicai.byteera.activity.knowwealth.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.bean.BannerEntity;
import com.zhicai.byteera.activity.bean.Consult;
import com.zhicai.byteera.activity.knowwealth.view.KnowWealthDetailActivity;
import com.zhicai.byteera.activity.knowwealth.view.KnowWealthDetailCommentActivity2;
import com.zhicai.byteera.activity.knowwealth.viewinterface.KnowWealthIV;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.information.Information;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/6/27. */
public class KnowWealthPre {
    private KnowWealthIV knowWealthIV;
    private Context mContext;
    private List<Information.Zixun> zixunList;
    private String pageLastZiXunId;

    public KnowWealthPre(KnowWealthIV knowWealthIV) {
        this.knowWealthIV = knowWealthIV;
        zixunList = new ArrayList<>();
    }

    public void setContext() {
        this.mContext = knowWealthIV.getContext();
    }

    public void intentToKnowWealthDetailActivity(Consult item) {
        Intent intent = new Intent(mContext, KnowWealthDetailActivity.class);
        intent.putExtra("zixun_id", item.getZiXunId());
        intent.putExtra("title", item.getTitle());
        intent.putExtra("imgUrl", item.getAvatarUrl());
        ActivityUtil.startActivity((Activity) mContext, intent);
    }

    public void getData(final int value) {
        Information.MainPageReq page;
        if (pageLastZiXunId != null) {
            page = Information.MainPageReq.newBuilder().setProductType(Common.ProductType.valueOf(value)).setZixunId(pageLastZiXunId).setIsafter(0).build();
        } else {
            page = Information.MainPageReq.newBuilder().setProductType(Common.ProductType.valueOf(value)).setIsafter(0).build();
        }
        TiangongClient.instance().send("chronos", "get_main_page", page.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final Information.MainPage data = Information.MainPage.parseFrom(buffer);
                    Log.d("KnowWealthPre", "res-->" + data.toString() + ",type-->" + value);
                    MyApp.getMainThreadHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            if (data.getErrorno() == 0) {
                                if (pageLastZiXunId == null) knowWealthIV.removeAllViews();
                                if (value == Constants.ZHICAI_ALL) loadZHICAI_ALL(data);
                                if (data.getZixunCount() > 0) loadMore(data);
                                knowWealthIV.notifyDataSetChanged();
                            }
                            knowWealthIV.loadComplete();
                            knowWealthIV.refreshFinish();
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadZHICAI_ALL(Information.MainPage data) {
        List<BannerEntity> bannerList = new ArrayList<>();
        for (int i = 0; i < data.getMainImageList().size(); i++) {
            Information.ADImg adImg = data.getMainImageList().get(i);
            BannerEntity bannerEntity = new BannerEntity(adImg.getImageUrl(), adImg.getJumpPoint().getNumber(), adImg.getJumpUrl());
            bannerList.add(bannerEntity);
        }
        if (pageLastZiXunId == null && data.getZixunCount() > 0) knowWealthIV.addItem(bannerList);
    }

    private void loadMore(Information.MainPage data) {
        List<Information.Zixun> dataZixunList = data.getZixunList();

        if (pageLastZiXunId == null) KnowWealthPre.this.zixunList.clear();

        KnowWealthPre.this.zixunList.addAll(dataZixunList);
        for (int i = 0; i < dataZixunList.size(); i++) {
            Information.Zixun zixun = dataZixunList.get(i);
            Consult consult = new Consult(zixun.getImageUrl(), zixun.getZixunId(), zixun.getTitle(), zixun.getCommentTimes(), zixun.getPublishTime(), zixun.getProductType());
            knowWealthIV.addItem(consult);
            pageLastZiXunId = dataZixunList.get(data.getZixunCount() - 1).getZixunId();
        }
    }


    public void intenttoComment(Consult consult) {
        Intent intent = new Intent(mContext, KnowWealthDetailCommentActivity2.class);
        intent.putExtra("zixun_id", consult.getZiXunId());
        ActivityUtil.startActivity((Activity) mContext, intent);
    }


}
