package com.zhicai.byteera.activity.product.entity;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/6/12. */
public class CompanyEntity  {
    private String name;
    private String smallImage;
    private List<ProductEntity> productEntities = new ArrayList<>();
//    optional uint32 sellable_products = 5;  // 可售产品数
    private int sellableProducts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public List<ProductEntity> getProductEntities() {
        return productEntities;
    }

    public void setProductEntities(List<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }

    public int getSellableProducts() {
        return sellableProducts;
    }

    public void setSellableProducts(int sellableProducts) {
        this.sellableProducts = sellableProducts;
    }
}
