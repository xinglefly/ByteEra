package com.zhicai.byteera.activity.community.dynamic.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by songpengfei on 15/10/9.
 */
public class ExchangeRecordEntity implements Parcelable{
     String record_id;                      // 兑换记录的ID
     String item_id;                        // 商品ID
     String item_name;                      // 商品名称
     String item_image;                     // 商品图片
     int item_coin;                      // 兑换消耗的财币
     int item_type;                      // 兑换商品类型：1: 实物商品 2: 固定值数字商品 3: 动态值数字商品
     String item_desc;                      // 商品描述
     long create_time;                     // 兑换时间
     String exchange_item_content;          // 兑换数字商品内容
     String receiver_name;                 // 实物商品的收件人姓名
     String receiver_tel;                  // 实物商品的收件人电话
     String receiver_address;              // 实物商品的收件人地址
     String receiver_zip;                  // 实物商品的收件人邮编
     int deliver_status;                  // 实物商品的邮寄状态
     String deliver_company;               // 实物商品的快递公司
     String deliver_sn;                    // 实物商品的快递单号
     String deliver_time;                  // 实物商品的邮寄时间




    protected ExchangeRecordEntity(Parcel in) {
        record_id = in.readString();
        item_id = in.readString();
        item_name = in.readString();
        item_image = in.readString();
        item_coin = in.readInt();
        item_type = in.readInt();
        item_desc = in.readString();
        create_time = in.readInt();
        exchange_item_content = in.readString();
        receiver_name = in.readString();
        receiver_tel = in.readString();
        receiver_address = in.readString();
        receiver_zip = in.readString();
        deliver_status = in.readInt();
        deliver_company = in.readString();
        deliver_sn = in.readString();
        deliver_time = in.readString();
    }

    public static final Creator<ExchangeRecordEntity> CREATOR = new Creator<ExchangeRecordEntity>() {
        @Override
        public ExchangeRecordEntity createFromParcel(Parcel in) {
            return new ExchangeRecordEntity(in);
        }

        @Override
        public ExchangeRecordEntity[] newArray(int size) {
            return new ExchangeRecordEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(record_id);
        dest.writeString(item_id);
        dest.writeString(item_name);
        dest.writeString(item_image);
        dest.writeInt(item_coin);
        dest.writeInt(item_type);
        dest.writeString(item_desc);
        dest.writeLong(create_time);
        dest.writeString(exchange_item_content);
        dest.writeString(receiver_name);
        dest.writeString(receiver_tel);
        dest.writeString(receiver_address);
        dest.writeString(receiver_zip);
        dest.writeInt(deliver_status);
        dest.writeString(deliver_company);
        dest.writeString(deliver_sn);
        dest.writeString(deliver_time);
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

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

    public String getExchange_item_content() {
        return exchange_item_content;
    }

    public void setExchange_item_content(String exchange_item_content) {
        this.exchange_item_content = exchange_item_content;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_tel() {
        return receiver_tel;
    }

    public void setReceiver_tel(String receiver_tel) {
        this.receiver_tel = receiver_tel;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public String getReceiver_zip() {
        return receiver_zip;
    }

    public void setReceiver_zip(String receiver_zip) {
        this.receiver_zip = receiver_zip;
    }

    public int getDeliver_status() {
        return deliver_status;
    }

    public void setDeliver_status(int deliver_status) {
        this.deliver_status = deliver_status;
    }

    public String getDeliver_company() {
        return deliver_company;
    }

    public void setDeliver_company(String deliver_company) {
        this.deliver_company = deliver_company;
    }

    public String getDeliver_sn() {
        return deliver_sn;
    }

    public void setDeliver_sn(String deliver_sn) {
        this.deliver_sn = deliver_sn;
    }

    public String getDeliver_time() {
        return deliver_time;
    }

    public void setDeliver_time(String deliver_time) {
        this.deliver_time = deliver_time;
    }

    @Override
    public String toString() {
        return "ExchangeRecordEntity{" +
                "record_id='" + record_id + '\'' +
                ", item_id='" + item_id + '\'' +
                ", item_name='" + item_name + '\'' +
                ", item_image='" + item_image + '\'' +
                ", item_coin=" + item_coin +
                ", item_type=" + item_type +
                ", item_desc='" + item_desc + '\'' +
                ", create_time=" + create_time +
                ", exchange_item_content='" + exchange_item_content + '\'' +
                ", receiver_name='" + receiver_name + '\'' +
                ", receiver_tel='" + receiver_tel + '\'' +
                ", receiver_address='" + receiver_address + '\'' +
                ", receiver_zip='" + receiver_zip + '\'' +
                ", deliver_status=" + deliver_status +
                ", deliver_company='" + deliver_company + '\'' +
                ", deliver_sn='" + deliver_sn + '\'' +
                ", deliver_time='" + deliver_time + '\'' +
                '}';
    }


    public ExchangeRecordEntity(String record_id, String item_id, String item_name, String item_image, int item_type, long create_time,String exchange_item_content,String deliver_company,String deliver_sn) {
        this.record_id = record_id;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_image = item_image;
        this.item_type = item_type;
        this.create_time = create_time;
        this.exchange_item_content = exchange_item_content;
        this.deliver_company = deliver_company;
        this.deliver_sn = deliver_sn;
    }

}
