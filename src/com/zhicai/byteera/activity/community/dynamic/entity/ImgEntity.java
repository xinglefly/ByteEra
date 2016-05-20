package com.zhicai.byteera.activity.community.dynamic.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;


/** Created by bing on 2015/5/29. */
public class ImgEntity extends DataSupport implements Serializable {
    private String imgUrl;
    private int dynamicitementity_id;

    public ImgEntity() {
    }

    public int getDynamicitementity_id() {
        return dynamicitementity_id;
    }

    public void setDynamicitementity_id(int dynamicitementity_id) {
        this.dynamicitementity_id = dynamicitementity_id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
