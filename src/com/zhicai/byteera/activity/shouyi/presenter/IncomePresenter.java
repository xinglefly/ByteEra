//package com.zhicai.byteera.activity.shouyi.presenter;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.google.protobuf.InvalidProtocolBufferException;
//import com.zhicai.byteera.MyApp;
//import com.zhicai.byteera.activity.shouyi.view.IncomeView;
//import com.zhicai.byteera.commonutil.ModelParseUtil;
//import com.zhicai.byteera.service.Common;
//import com.zhicai.byteera.service.product.FinancingProduct;
//import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
//import com.zhicai.byteera.service.serversdk.TiangongClient;
//
///**
// * Created by chenzhenxing on 15/12/26.
// */
//public class IncomePresenter {
//
//    IncomeView incomeView;
//    private String lastMsgId;
//
//    public IncomePresenter(IncomeView view) {
//        this.incomeView = view;
//    }
//
//
//    public void getDynamicFromNet(final boolean isFirst) {
//        FinancingProduct.IncomeComparisionGetReq.Builder req = FinancingProduct.IncomeComparisionGetReq.newBuilder();
//        StringBuilder sb = new StringBuilder();
//        int p2p = Common.ProductType.zhiCai_p2p_VALUE;
//        int bank = Common.ProductType.zhiCai_zxyh_VALUE;
//        if (isFirst) {
//            if (conditionTime != 0)
//                req.setCondLimit(FinancingProduct.IncomeComparisionGetCondLimit.newBuilder().setMax(conditionTime));
//            else
//                req.setCondLimit(FinancingProduct.IncomeComparisionGetCondLimit.newBuilder().setMax(90));
//            if (conditionRate != 0)
//                req.setCondIncome(FinancingProduct.IncomeComparisionGetCondIncome.newBuilder().setMax(((float) conditionRate) / 100));
//            else
//                req.setCondIncome(FinancingProduct.IncomeComparisionGetCondIncome.newBuilder().setMax(0.18f));
//            if (conditionBank) sb.append(bank).append(",");
//            if (conditionP2p) sb.append(p2p).append(",");
//            if (!TextUtils.isEmpty(sb.toString())) {
//                String types = sb.toString().substring(0, sb.toString().lastIndexOf(","));
//                req.setTypes(types);
//            }
//            req.setIsafter(false);
//        } else {
//            if (!TextUtils.isEmpty(lastMsgId))
//                req.setProductId(lastMsgId);
//            req.setIsafter(false);
//        }
//        final FinancingProduct.IncomeComparisionGetReq builder = req.build();
//        Log.d("isfirst", "-isFirst->" + isFirst + ",builder->" + builder.toString());
//        TiangongClient.instance().send("chronos", "income_comparision_get", builder.toByteArray(), new BaseHandlerClass() {
//            public void onSuccess(byte[] buffer) {
//                try {
//                    final FinancingProduct.IncomeComparisionGetResponse response = FinancingProduct.IncomeComparisionGetResponse.parseFrom(buffer);
//                    Log.d("IncomeFagment", "groupHome-->" + response.toString());
//                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
//                        public void run() {
//                            if (response.getErrorno() == 0 && response.getProductsCount() > 0) {
//                                productEntities = ModelParseUtil.ProductEntityParse(response.getProductsList());
//                                if (productEntities.size()>0){
//                                    lastMsgId = productEntities.get(productEntities.size() - 1).getProductId();
//                                    if (isFirst) {
//                                        adapter.setData(productEntities);
//                                    } else {
//                                        adapter.addAllItem(productEntities);
//                                    }
//                                    mListView.loadComplete();
//                                }
//                            }
//                        }
//                    });
//                } catch (InvalidProtocolBufferException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//}
