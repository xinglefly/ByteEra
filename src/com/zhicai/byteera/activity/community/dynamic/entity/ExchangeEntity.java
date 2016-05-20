package com.zhicai.byteera.activity.community.dynamic.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;

/**
 * Created by songpengfei on 15/9/22.
 */
public class ExchangeEntity implements Parcelable{

     String item_id;                        // 商品ID
     String item_name;                      // 商品名称
     String item_image;                     // 商品图片
     int display_order;                  // 商品排序
     int item_total_count;                  // 商品总量
     int item_left_count;                   // 商品剩余数量
     int item_coin;                         // 兑换所需财币
     int item_type;                         // 商品类型：1: 实物商品 2: 固定值数字商品 3: 动态值数字商品
     String item_desc;                      // 商品描述
     long create_time;                     // 商品创建时间戳
     private FinancingCompanyEntity companyEntity;        // 商品来源公司

    protected ExchangeEntity(Parcel in) {
        item_id = in.readString();
        item_name = in.readString();
        item_image = in.readString();
        display_order = in.readInt();
        item_total_count = in.readInt();
        item_left_count = in.readInt();
        item_coin = in.readInt();
        item_type = in.readInt();
        item_desc = in.readString();
        create_time = in.readLong();
        companyEntity = in.readParcelable(FinancingCompanyEntity.class.getClassLoader());
    }

    public static final Creator<ExchangeEntity> CREATOR = new Creator<ExchangeEntity>() {
        @Override
        public ExchangeEntity createFromParcel(Parcel in) {
            return new ExchangeEntity(in);
        }

        @Override
        public ExchangeEntity[] newArray(int size) {
            return new ExchangeEntity[size];
        }
    };

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public int getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(int display_order) {
        this.display_order = display_order;
    }

    public int getItem_total_count() {
        return item_total_count;
    }

    public void setItem_total_count(int item_total_count) {
        this.item_total_count = item_total_count;
    }

    public int getItem_left_count() {
        return item_left_count;
    }

    public void setItem_left_count(int item_left_count) {
        this.item_left_count = item_left_count;
    }

    public int getItem_coin() {
        return item_coin;
    }

    public void setItem_coin(int item_coin) {
        this.item_coin = item_coin;
    }

    public int getItem_type() {
        return item_type;
    }

    public void setItem_type(int item_type) {
        this.item_type = item_type;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public FinancingCompanyEntity getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(FinancingCompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item_id);
        dest.writeString(item_name);
        dest.writeString(item_image);
        dest.writeInt(display_order);
        dest.writeInt(item_total_count);
        dest.writeInt(item_left_count);
        dest.writeInt(item_coin);
        dest.writeInt(item_type);
        dest.writeString(item_desc);
        dest.writeLong(create_time);
        dest.writeParcelable(companyEntity, flags);
    }


    @Override
    public String toString() {
        return "ExchangeEntity{" +
                "item_id='" + item_id + '\'' +
                ", item_name='" + item_name + '\'' +
                ", item_image='" + item_image + '\'' +
                ", display_order=" + display_order +
                ", item_total_count=" + item_total_count +
                ", item_left_count=" + item_left_count +
                ", item_coin=" + item_coin +
                ", item_type=" + item_type +
                ", item_desc='" + item_desc + '\'' +
                ", create_time=" + create_time +
                ", companyEntity=" + companyEntity +
                '}';
    }

    public ExchangeEntity() {
    }

    public ExchangeEntity(String item_id, String item_name, String item_image, int display_order, int item_total_count, int item_left_count, int item_coin, int item_type, String item_desc, long create_time) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_image = item_image;
        this.display_order = display_order;
        this.item_total_count = item_total_count;
        this.item_left_count = item_left_count;
        this.item_coin = item_coin;
        this.item_type = item_type;
        this.item_desc = item_desc;
        this.create_time = create_time;
    }
}
