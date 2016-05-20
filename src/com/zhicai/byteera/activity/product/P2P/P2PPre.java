package com.zhicai.byteera.activity.product.P2P;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.activity.product.entity.ProductParameter;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.product.FinancingProduct;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import java.util.List;

/**
 * Created by bing on 2015/6/27.
 */
@SuppressWarnings("unused")
public class P2PPre {
    private P2PIV p2PIV;
    private Context mContext;
    private DrawerRightIV drawerRightIV;
    public List<ProductEntity> productList;
    private int companyItemPosition;
    private int type;
    private List<FinancingCompanyEntity> FinancingCompanyEntities;
    private String lastMsgId;

    public void setContext() {
        this.mContext = p2PIV.getContext();
    }

    public P2PPre(P2PIV p2PIV) {
        this.p2PIV = p2PIV;
    }

    public void setDrawerRightIV(DrawerRightIV drawerRightIV) {
        this.drawerRightIV = drawerRightIV;
    }

    public void refreshListView(int type) {
        FinancingProduct.GetCompanyListReq sec = FinancingProduct.GetCompanyListReq.newBuilder().setProductType(Common.ProductType.valueOf(type)).build();
        TiangongClient.instance().send("chronos", "get_company_list", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    FinancingProduct.GetCompanyListResponse response = FinancingProduct.GetCompanyListResponse.parseFrom(buffer);
                    Log.d("P2PPre","refreshListView res-->"+response.toString());
                    FinancingCompanyEntities = ModelParseUtil.FinancingCompanyEntityParse(response.getCompaniesList());
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            p2PIV.finishRefresh();
                            p2PIV.loadComplete("mListView");
                            p2PIV.refreshListView(FinancingCompanyEntities);
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void getProductList(final ProductParameter parameter) {
        FinancingProduct.GetCompanyProductListReq.Builder req = FinancingProduct.GetCompanyProductListReq.newBuilder();

        if (!parameter.isFrist()) req.setProductId(lastMsgId);
        if (!TextUtils.isEmpty(parameter.getCompany_id())) req.setCompanyId(parameter.getCompany_id());
        if (parameter.getType()!=0) req.setProductType(Common.ProductType.valueOf(parameter.getType()));
        if (parameter.getIncome_value()!=0) req.setOrderField(FinancingProduct.ProductOrderField.valueOf(parameter.getIncome_value()));
        if (parameter.getLimit_value()==1) req.setOrderType(Common.OrderType.AESC);
        else if (parameter.getLimit_value()==2) req.setOrderType(Common.OrderType.DESC);

        req.setIsafter(false);
        FinancingProduct.GetCompanyProductListReq build = req.build();
        Log.d("P2PPre", "-isFirst->" + parameter.isFrist() + ",builder->" + build.toString());
        TiangongClient.instance().send("chronos", "get_company_product_list", build.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    FinancingProduct.GetCompanyProductListResponse response = FinancingProduct.GetCompanyProductListResponse.parseFrom(buffer);
                    Log.d("P2PPre", "res-->" + response.toString());
                    productList = ModelParseUtil.getPositionProductListParse(response.getProductsList());
                    if (productList.size()>0)
                        lastMsgId = productList.get(productList.size() - 1).getProductId();

                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            ByCompanyIdfillDataDrawerRight(parameter.getCompany_id(),parameter.isFrist());
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void ByCompanyIdfillDataDrawerRight(String company_id, boolean frist) {
        if (!TextUtils.isEmpty(company_id)) {
            if (frist) p2PIV.openDrawerRight(productList, true);
            else p2PIV.openDrawerRight(productList, false);
            p2PIV.loadComplete("mDrawerRight");
        } else {
            if (frist) p2PIV.loadData(productList, true);
            else p2PIV.loadData(productList, false);
            p2PIV.loadComplete("allView");
        }
    }

}
