package com.zhicai.byteera.activity.product.recommend;

import android.content.Context;
import android.text.TextUtils;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.product.FinancingProduct;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/6/27. */
public class RecommendPre {
    private RecommendIV mRecommendIV;
    private Context context;
    private ArrayList<ProductEntity> productEntities;

    public RecommendPre(RecommendIV recommendIV) {
        this.mRecommendIV = recommendIV;
    }

    public void getContext() {
        context = mRecommendIV.getContext();
    }
    public void refreshListView() {
        String userId = PreferenceUtils.getInstance(context).getUserId();
        FinancingProduct.RecommendReq sec;
        if (!TextUtils.isEmpty(userId)) {
            sec = FinancingProduct.RecommendReq.newBuilder().setUserId(userId).build();
        } else {
            sec = FinancingProduct.RecommendReq.newBuilder().build();
        }
        TiangongClient.instance().send("chronos", "financing_get_recommend", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final FinancingProduct.RecommendResponse response = FinancingProduct.RecommendResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override
                            public void run() {
                                List<Common.LicaiProduct> productList = response.getProductList();
                                productEntities = new ArrayList<>();
                                for (int i = 0; i < productList.size(); i++) {
                                    Common.LicaiProduct licaiProduct = productList.get(i);
                                    ProductEntity productEntity = new ProductEntity();
                                    productEntity.setSmallImage(licaiProduct.getSmallImage());
                                    productEntity.setProductWatch(licaiProduct.getProductWatch());
                                    productEntity.setProductCoin(licaiProduct.getProductCoin());
                                    productEntity.setProductId(licaiProduct.getProductId());
                                    productEntity.setProductIncome(licaiProduct.getProductIncome());
                                    productEntity.setProductTitle(licaiProduct.getProductTitle());
                                    productEntity.setProductLimit(licaiProduct.getProductLimit());
                                    productEntities.add(productEntity);
                                }
                                mRecommendIV.refreshListView(productEntities);
                            }
                        });
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void changeAttention(ProductEntity entity) {
        boolean productWatch = entity.isProductWatch();
        String userId = PreferenceUtils.getInstance(context).readUserInfo().getUser_id();
        if (productWatch) {
            FinancingProduct.DeWatchProduct sec = FinancingProduct.DeWatchProduct.newBuilder().setUserId(userId).setProductId(entity.getProductId()).build();
            TiangongClient.instance().send("chronos", "financing_dewatch", sec.toByteArray(), new BaseHandlerClass() {
                @Override
                public void onSuccess(byte[] buffer) {
                    try {
                        Common.CommonResponse response = Common.CommonResponse.parseFrom(buffer);
                        if (response.getErrorno() == 0) {
                            MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                                @Override public void run() {
                                    refreshListView();
                                }
                            });
                        }
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            //如果当前用户已经登录  则可以关注, 没登录点击就没反应  做起来太麻烦
            if (TextUtils.isEmpty(userId)) {
//                        ActivityUtil.startActivityForResult((Activity) context, new Intent(context, LoginActivity.class), Constants.RETURN_FROM_LOGIN);
            } else {
                FinancingProduct.WatchProduct sec = FinancingProduct.WatchProduct.newBuilder().setUserId(userId).setProductId(entity.getProductId()).build();
                TiangongClient.instance().send("chronos", "financing_watch", sec.toByteArray(), new BaseHandlerClass() {
                    @Override public void onSuccess(byte[] buffer) {
                        try {
                            Common.CommonResponse response = Common.CommonResponse.parseFrom(buffer);
                            if (response.getErrorno() == 0) {
                                MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                                    @Override public void run() {
                                       refreshListView();
                                    }
                                });
                            }
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
