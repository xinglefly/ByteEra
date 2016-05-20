package com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord.adapter.GridAdapter;
import com.zhicai.byteera.activity.community.dynamic.entity.ExchangeEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.goodsexchange.Exchange;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.ExpandGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by xingle on 15/9/21.
 */
@SuppressWarnings("unused")
public class ShoppingFragment extends BaseFragment{

    @Bind(R.id.gridview) ExpandGridView gridview;
    private List<ExchangeEntity> exchangeEntityLists;
    private GridAdapter GVadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shopping_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this,getView());
        getData(getArguments().getInt("product_type"));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Constants.isUpdateShopping){
            getData(getArguments().getInt("product_type"));
            Constants.isUpdateShopping = false;
        }
    }


    @OnItemClick(R.id.gridview)
    public void onItemClck(int position){
        ExchangeEntity exchangeEntity = exchangeEntityLists.get(position);
        Intent intent = new Intent(getActivity(),ExchangeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("exchange",exchangeEntity);
        intent.putExtras(bundle);
        ActivityUtil.startActivity(getActivity(), intent);
    }


    public void getData(final int product_type) {
        MyApp.getInstance().executorService.execute(new Runnable() {
            @Override
            public void run() {
                Exchange.GetExchangeItemListReq sec;
                if (!TextUtils.isEmpty(userId)) sec = Exchange.GetExchangeItemListReq.newBuilder().setItemType(product_type).setUserId(userId).build();
                else sec = Exchange.GetExchangeItemListReq.newBuilder().setItemType(product_type).build();

                TiangongClient.instance().send("chronos", "get_exchange_item_list", sec.toByteArray(), new BaseHandlerClass() {

                    @Override
                    public void onSuccess(byte[] buffer) {
                        try {
                            Exchange.GetExchangeItemListResponse response = Exchange.GetExchangeItemListResponse.parseFrom(buffer);
                            if (response.getErrorno() == 0) {
                                List<Common.ExchangeItem> exchangeItemsList = response.getExchangeItemsList();

                                exchangeEntityLists = new ArrayList();
                                for (Common.ExchangeItem exchangeItem : exchangeItemsList) {
                                    ExchangeEntity exchangeEntity = new ExchangeEntity(exchangeItem.getItemId(), exchangeItem.getItemName(), exchangeItem.getItemImage(),
                                            exchangeItem.getDisplayOrder(), exchangeItem.getItemTotalCount(), exchangeItem.getItemLeftCount(), exchangeItem.getItemCoin(),
                                            exchangeItem.getItemType(), exchangeItem.getItemDesc(), exchangeItem.getCreateTime());
                                    exchangeEntityLists.add(exchangeEntity);
                                }

                                refreshAdpater();

                            } else {
                                Log.d("ShoppingFragment", "err-->" + response.getErrorDescription());
                            }
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        });

    }

    private void refreshAdpater() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GVadapter = new GridAdapter(getActivity(), R.layout.shopping_item, exchangeEntityLists);
                gridview.setAdapter(GVadapter);
            }
        });
    }


}
