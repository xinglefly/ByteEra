package com.zhicai.byteera.activity.bean;

import com.zhicai.byteera.service.Common;

import java.io.Serializable;


public class Consult implements Serializable {
    private String avatarUrl;
    private String ziXunId;
    private String title;
    private int CommentNum;
    private long publishTime;
    private int produtType;

    public int getProdutType() {
        return produtType;
    }

    public void setProdutType(int produtType) {
        this.produtType = produtType;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getZiXunId() {
        return ziXunId;
    }

    public void setZiXunId(String ziXunId) {
        this.ziXunId = ziXunId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCommentNum() {
        return CommentNum;
    }

    public void setCommentNum(int commentNum) {
        CommentNum = commentNum;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public Consult(String imageUrl, String zixunId, String title, int commentTimes, int publishTime, Common.ProductType productType) {
        this.avatarUrl = imageUrl;
        this.ziXunId = zixunId;
        this.title = title;
        CommentNum = commentTimes;
        this.publishTime = publishTime;
        this.produtType = productType.getNumber();
    }

    public Consult() {
    }
}
