package com.zhicai.byteera.jpush;

import com.lidroid.xutils.db.annotation.Table;
import java.io.Serializable;

@SuppressWarnings("unused")
@Table(name = "JpushProductEntity")
public class JpushProductEntity implements Serializable{

    private int id;
    private int notifaction_id;
    private String product_id;
    private String title;
    private String small_image;
    private long product_time;
    private String product_url;
    private int time_limit;
    private float income;

    public int getNotifaction_id() {
        return notifaction_id;
    }

    public void setNotifaction_id(int notifaction_id) {
        this.notifaction_id = notifaction_id;
    }

    public int getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(int time_limit) {
        this.time_limit = time_limit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public long getProduct_time() {
        return product_time;
    }

    public void setProduct_time(long product_time) {
        this.product_time = product_time;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }



    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "JpushProductEntity{" +
                "id=" + id +
                ", product_id='" + product_id + '\'' +
                ", title='" + title + '\'' +
                ", small_image='" + small_image + '\'' +
                ", product_time='" + product_time + '\'' +
                ", product_url='" + product_url + '\'' +
                ", time_limit=" + time_limit +
                ", income=" + income +
                '}';
    }

    public JpushProductEntity() {
    }

    public JpushProductEntity(String product_id, String title) {
        this.product_id = product_id;
        this.title = title;
    }

    public JpushProductEntity(int notifaction_id,String product_id, String title, String small_image, long product_time, String product_url, int time_limit, float income) {
        this.notifaction_id = notifaction_id;
        this.product_id = product_id;
        this.title = title;
        this.small_image = small_image;
        this.product_time = product_time;
        this.product_url = product_url;
        this.time_limit = time_limit;
        this.income = income;
    }
}
