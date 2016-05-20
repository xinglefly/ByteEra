package com.zhicai.byteera.activity.community.dynamic.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/** Created by bing on 2015/5/21. 动态列表项对应的评论项实体 */
public class DynamicCommentEntity extends DataSupport implements Serializable {
    private DynamicEntity dynamicEntity;
    private String avatarUrl;
    private String content;
    private String nickName;
    private String userId;
    private long publishTime;
    private int dynamicitementity_id;
    private int commentIndex;
    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DynamicEntity getDynamicEntity() {
        return dynamicEntity;
    }

    public void setDynamicEntity(DynamicEntity dynamicEntity) {
        this.dynamicEntity = dynamicEntity;
    }


    public int getCommentIndex() {
        return commentIndex;
    }

    public void setCommentIndex(int commentIndex) {
        this.commentIndex = commentIndex;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getDynamicitementity_id() {
        return dynamicitementity_id;
    }

    public void setDynamicitementity_id(int dynamicitementity_id) {
        this.dynamicitementity_id = dynamicitementity_id;
    }
}
