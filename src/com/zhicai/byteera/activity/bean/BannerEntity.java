package com.zhicai.byteera.activity.bean;

import java.io.Serializable;

/**
 * Created by bing on 2015/4/17.
 */
public class BannerEntity implements Serializable {
    private String img_url;
    private int jump_point;
    private String jump_url;

    public BannerEntity(String img_url, int jump_point, String jump_url) {
        this.img_url = img_url;
        this.jump_point = jump_point;
        this.jump_url = jump_url;
    }

    public String getImg_url() {

        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getJump_point() {
        return jump_point;
    }

    public void setJump_point(int jump_point) {
        this.jump_point = jump_point;
    }

    public String getJump_url() {
        return jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }
}
