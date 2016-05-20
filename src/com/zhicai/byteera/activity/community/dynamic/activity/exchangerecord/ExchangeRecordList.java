package com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord;


import android.util.Log;
import android.widget.ListView;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord.adapter.RecordAdapter;
import com.zhicai.byteera.activity.community.dynamic.entity.ExchangeRecordEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.goodsexchange.Exchange;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 兑换记录
 */

public class ExchangeRecordList extends BaseActivity{

    @Bind(R.id.head_title) HeadViewMain mTitle;
    @Bind(R.id.listview) ListView mListView;
    private RecordAdapter mListAdapter;
    private List<ExchangeRecordEntity> exchangeRecordEntityList = null;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.exchange_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        mTitle.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }


    public void getData() {
        if (DetermineIsLogin()) return;
        MyApp.getInstance().executorService.execute(new Runnable() {
            @Override
            public void run() {
                String userId = PreferenceUtils.getInstance(baseContext).getUserId();
                Exchange.GetExchangeRecordListReq sec = Exchange.GetExchangeRecordListReq.newBuilder().setUserId(userId).build();
                TiangongClient.instance().send("chronos", "get_exchange_record_list", sec.toByteArray(), new BaseHandlerClass() {

                    @Override
                    public void onSuccess(byte[] buffer) {
                        try {
                            Exchange.GetExchangeRecordListResponse response = Exchange.GetExchangeRecordListResponse.parseFrom(buffer);
                            Log.d("ExchangeRecordList","res-->"+response.toString());
                            if (response.getErrorno() == 0) {
                                List<Common.ExchangeRecord> exchangeRecordsList = response.getExchangeRecordsList();

                                exchangeRecordEntityList = new ArrayList();
                                for (Common.ExchangeRecord recordsItem : exchangeRecordsList) {
                                    ExchangeRecordEntity recordEntity = new ExchangeRecordEntity(recordsItem.getRecordId(),recordsItem.getItemId(),
                                            recordsItem.getItemName(),recordsItem.getItemImage(),recordsItem.getItemType(),recordsItem.getCreateTime(),recordsItem.getExchangeItemContent()
                                    ,recordsItem.getDeliverCompany(),recordsItem.getDeliverSn());
                                    exchangeRecordEntityList.add(recordEntity);
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mListAdapter = new RecordAdapter(baseContext, R.layout.exchangerecord_item, exchangeRecordEntityList);
                                        mListView.setAdapter(mListAdapter);
                                    }
                                });
                            } else {
                                Log.d("ExchangeRecordList","err-->"+response.getErrorDescription());
                            }
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }




}
