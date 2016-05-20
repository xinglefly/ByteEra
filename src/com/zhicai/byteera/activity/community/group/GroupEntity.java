package com.zhicai.byteera.activity.community.group;

import com.lidroid.xutils.db.annotation.Table;
import com.zhicai.byteera.commonutil.StringUtil;

import java.io.Serializable;

/** Created by bing on 2015/6/1. */
@Table(name = "GroupEntity")
public class GroupEntity  implements Serializable,Comparable<GroupEntity>{
    private int id;
    private String groupId;
    private String name;
    private String avatarUrl;
    private int peopleCnt;
//    private boolean watched;
    private String description;
    private int dogntaiToadyCnt;
    private int commentCnt;
    private String chatGroupId;

    private boolean joined;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDogntaiToadyCnt() {
        return dogntaiToadyCnt;
    }

    public void setDogntaiToadyCnt(int dogntaiToadyCnt) {
        this.dogntaiToadyCnt = dogntaiToadyCnt;
    }

    public int getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(int commentCnt) {
        this.commentCnt = commentCnt;
    }

    public String getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(String chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getPeopleCnt() {
        return peopleCnt;
    }

    public void setPeopleCnt(int peopleCnt) {
        this.peopleCnt = peopleCnt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isJoined() {
        return joined;
    }

    public void setJoined(boolean joined) {
        this.joined = joined;
    }


    @Override
    public String toString() {
        return "GroupEntity{" +
                "groupId='" + groupId + '\'' +
                ", name='" + name + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", peopleCnt=" + peopleCnt +
                ", description='" + description + '\'' +
                ", dogntaiToadyCnt=" + dogntaiToadyCnt +
                ", commentCnt=" + commentCnt +
                ", chatGroupId='" + chatGroupId + '\'' +
                ", joined=" + joined +
                '}';
    }

    public GroupEntity() {
    }

    public GroupEntity(String groupId, String name, String avatarUrl, String description,int peopleCnt,boolean joined) {
        this.groupId = groupId;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.description = description;
        this.peopleCnt = peopleCnt;
        this.joined = joined;
    }

    public GroupEntity(String groupId, String name, String avatarUrl, String description,int peopleCnt) {
        this.groupId = groupId;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.description = description;
        this.peopleCnt = peopleCnt;
    }

    public GroupEntity(String groupId, String name, String avatarUrl, String description,boolean joined) {
        this.groupId = groupId;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.description = description;
        this.joined = joined;
    }

    @Override
    public int compareTo(GroupEntity another) {
        return StringUtil.getPinYinHeadChar(this.getName()).charAt(0) - StringUtil.getPinYinHeadChar(another.getName()).charAt(0);
    }
}
