package com.zhicai.byteera.activity.product.entity;

import java.util.Comparator;

/**
 * Created by bing on 2015/6/13.
 */
public class ShouYiComparator implements Comparator<ProductEntity> {
    @Override public int compare(ProductEntity lhs, ProductEntity rhs) {
         if(lhs.getProductIncome()>rhs.getProductIncome()){
             return -1;
         }else {
             return 1;
         }
    }
}
