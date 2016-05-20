package com.zhicai.byteera.activity.community.dynamic.entity;

import com.zhicai.byteera.commonutil.StringUtil;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/** Created by bing on 2015/5/22. 用户基本信息实体 */
public class UserInfoEntity extends DataSupport implements Serializable, Comparable<UserInfoEntity> {
    private String userId;
    private int id;
    private int coinNUm;
    private int FansNum;
    private int FocusNum;
    private String avatarUrl;
    private String nickName;
    private boolean hasWatched;
    private int friendsNum;
    private Status mStatus;

    private int userType;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public enum Status {
        ATTENTION, ADD_ATTENTION, ARROW
    }

    public boolean isHasWatched() {
        return hasWatched;
    }

    public void setHasWatched(boolean hasWatched) {
        this.hasWatched = hasWatched;
    }

    private int collectNum;

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }

    public int getFriendsNum() {
        return friendsNum;
    }

    public void setFriendsNum(int friendsNum) {
        this.friendsNum = friendsNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoinNUm() {
        return coinNUm;
    }

    public void setCoinNUm(int coinNUm) {
        this.coinNUm = coinNUm;
    }

    public int getFansNum() {
        return FansNum;
    }

    public void setFansNum(int fansNum) {
        FansNum = fansNum;
    }

    public int getFocusNum() {
        return FocusNum;
    }

    public void setFocusNum(int focusNum) {
        FocusNum = focusNum;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setmStatus(Status mStatus) {
        this.mStatus = mStatus;
    }

    public Status getmStatus() {
        return mStatus;
    }

    @Override
    public int compareTo(UserInfoEntity another) {
        return StringUtil.getPinYinHeadChar(this.getNickName()).charAt(0) - StringUtil.getPinYinHeadChar(another.getNickName()).charAt(0);
    }

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "userId='" + userId + '\'' +
                ", id=" + id +
                ", coinNUm=" + coinNUm +
                ", FansNum=" + FansNum +
                ", FocusNum=" + FocusNum +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", nickName='" + nickName + '\'' +
                ", hasWatched=" + hasWatched +
                ", friendsNum=" + friendsNum +
                ", mStatus=" + mStatus +
                ", userType=" + userType +
                ", collectNum=" + collectNum +
                '}';
    }

    public UserInfoEntity() {
    }

    public UserInfoEntity(String userId, String avatarUrl, String nickName, boolean hasWatched) {
        this.userId = userId;
        this.avatarUrl = avatarUrl;
        this.nickName = nickName;
        this.hasWatched = hasWatched;
    }
}
