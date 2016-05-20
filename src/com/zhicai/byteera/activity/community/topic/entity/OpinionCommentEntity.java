package com.zhicai.byteera.activity.community.topic.entity;

/** Created by bing on 2015/6/24. */
public class OpinionCommentEntity {
    private int commentIndex;
    private int publishTime;
    private String userId;
    private String headPortrait;
    private String nickName;
    private String content;
    private NormalUserEntity toUser;

    public NormalUserEntity getToUser() {
        return toUser;
    }

    public void setToUser(NormalUserEntity toUser) {
        this.toUser = toUser;
    }

    public int getCommentIndex() {
        return commentIndex;
    }

    public void setCommentIndex(int commentIndex) {
        this.commentIndex = commentIndex;
    }

    public int getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(int publishTime) {
        this.publishTime = publishTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
