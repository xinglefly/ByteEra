package com.zhicai.byteera.activity.product.entity;

import android.os.Parcel;
import android.os.Parcelable;

/** Created by bing on 2015/6/2. */
@SuppressWarnings("unused")
public class ProductEntity implements Parcelable {
    private String productId;
    private String smallImage;      //头像
    private String productTitle;    //标题
    private float productIncome;    //收益，值为 0.05 就是 5%
    private float product_income_in_limit; //投资期限内的收益(收益*期限/365)
    private int productCoin;        //融资金额
    private int productLimit;       //期限, 值为 5 就是 5个月
    private boolean productWatch;   //是否关注，是0就是没关注，其他的都是关注
    private int serializedSize;
    private float progress;         //投标进度, 1为满标, 小于1时为未满标
    private String productUrl;      //产品详情url
    private String companyName;     //平台名称

    public ProductEntity() {
    }


    protected ProductEntity(Parcel in) {
        productId = in.readString();
        smallImage = in.readString();
        productTitle = in.readString();
        productIncome = in.readFloat();
        product_income_in_limit = in.readFloat();
        productCoin = in.readInt();
        productLimit = in.readInt();
        productWatch = in.readByte() != 0;
        serializedSize = in.readInt();
        progress = in.readInt();
        productUrl = in.readString();
        companyName = in.readString();
    }

    public static final Creator<ProductEntity> CREATOR = new Creator<ProductEntity>() {
        @Override
        public ProductEntity createFromParcel(Parcel in) {
            return new ProductEntity(in);
        }

        @Override
        public ProductEntity[] newArray(int size) {
            return new ProductEntity[size];
        }
    };


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public float getProduct_income_in_limit() {
        return product_income_in_limit;
    }

    public void setProduct_income_in_limit(float product_income_in_limit) {
        this.product_income_in_limit = product_income_in_limit;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public int getProductCoin() {
        return productCoin;
    }

    public void setProductCoin(int productCoin) {
        this.productCoin = productCoin;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public float getProductIncome() {
        return productIncome;
    }

    public void setProductIncome(float productIncome) {
        this.productIncome = productIncome;
    }

    public int getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(int productLimit) {
        this.productLimit = productLimit;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public boolean isProductWatch() {
        return productWatch;
    }

    public void setProductWatch(boolean productWatch) {
        this.productWatch = productWatch;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public int getSerializedSize() {
        return serializedSize;
    }

    public void setSerializedSize(int serializedSize) {
        this.serializedSize = serializedSize;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(productId);
        dest.writeString(smallImage);
        dest.writeString(productTitle);
        dest.writeFloat(productIncome);
        dest.writeFloat(product_income_in_limit);
        dest.writeInt(productCoin);
        dest.writeInt(productLimit);
        dest.writeByte((byte) (productWatch ? 1 : 0));
        dest.writeInt(serializedSize);
        dest.writeFloat(progress);
        dest.writeString(productUrl);
        dest.writeString(companyName);
    }


    @Override
    public String toString() {
        return "ProductEntity{" +
                "productId='" + productId + '\'' +
                ", smallImage='" + smallImage + '\'' +
                ", productTitle='" + productTitle + '\'' +
                ", productIncome=" + productIncome +
                ", product_income_in_limit=" + product_income_in_limit +
                ", productCoin=" + productCoin +
                ", productLimit=" + productLimit +
                ", productWatch=" + productWatch +
                ", serializedSize=" + serializedSize +
                ", progress=" + progress +
                ", productUrl=" + productUrl+
                ", company_name=" + companyName+
                '}';
    }



    public ProductEntity(String productId, String smallImage, String productTitle, float productIncome, float product_income_in_limit,int productCoin, int productLimit, boolean productWatch,float progress,String productUrl,String companyName) {
        this.productId = productId;
        this.smallImage = smallImage;
        this.productTitle = productTitle;
        this.productIncome = productIncome;
        this.product_income_in_limit = product_income_in_limit;
        this.productCoin = productCoin;
        this.productLimit = productLimit;
        this.productWatch = productWatch;
        this.progress = progress;
        this.productUrl = productUrl;
        this.companyName = companyName;
    }
}
