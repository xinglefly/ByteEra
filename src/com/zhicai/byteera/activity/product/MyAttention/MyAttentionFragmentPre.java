package com.zhicai.byteera.activity.product.MyAttention;

import android.content.Context;
import android.text.TextUtils;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.product.FinancingProduct;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.List;

/** Created by bing on 2015/6/27. */
public class MyAttentionFragmentPre {
    private MyAttentionFragmentIV myAttentionFragmentIV;
    private Context context;
    private List<ProductEntity> productEntities;

    public MyAttentionFragmentPre(MyAttentionFragmentIV myAttentionFragmentIV) {
        this.myAttentionFragmentIV = myAttentionFragmentIV;
    }

    public void getContext() {
        this.context = myAttentionFragmentIV.getContext();
    }

    /** 刷新listView */
    public void refreshListView() {
        String userId = PreferenceUtils.getInstance(context).getUserId();
        if (!TextUtils.isEmpty(userId)) {
            FinancingProduct.GetUserWatchReq sec = FinancingProduct.GetUserWatchReq.newBuilder().setUserId(userId).build();
            TiangongClient.instance().send("chronos", "financing_get_watch", sec.toByteArray(), new BaseHandlerClass() {
                @Override public void onSuccess(byte[] buffer) {
                    try {
                        final FinancingProduct.GetUserWatchResponse response = FinancingProduct.GetUserWatchResponse.parseFrom(buffer);
                        if (response.getErrorno() == 0) {
                            MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                                @Override public void run() {
                                    myAttentionFragmentIV.finishRefresh();
                                    productEntities = ModelParseUtil.ProductEntityParse(response.getProductList());
                                    myAttentionFragmentIV.listViewRefreshView(productEntities);
                                }
                            });
                        }
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            myAttentionFragmentIV.showCheckLoginDialog();
        }
    }

    /** 取消关注 */
    public void undoAttention(ProductEntity entity) {
        String userId = PreferenceUtils.getInstance(context).readUserInfo().getUser_id();
        FinancingProduct.DeWatchProduct sec = FinancingProduct.DeWatchProduct.newBuilder().
                setUserId(userId).setProductId(entity.getProductId()).build();
        TiangongClient.instance().send("chronos", "financing_dewatch", sec.toByteArray(), new BaseHandlerClass() {
            @Override public void onSuccess(byte[] buffer) {
                try {
                    final Common.CommonResponse response = Common.CommonResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override public void run() {
                            if (response.getErrorno() == 0) {
                                //刷新listView
                                refreshListView();
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
