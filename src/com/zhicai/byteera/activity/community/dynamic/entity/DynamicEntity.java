package com.zhicai.byteera.activity.community.dynamic.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bing on 2015/5/19. 动态列表项实体
 */
public class DynamicEntity extends DataSupport implements Serializable {
    private int type;
    private String avatarUrl;
    private String nickName;
    private String content;
    private boolean hasZan;
    private int zanCount;
    private int commentNum;
    private long publishTime;
    private String msgId;
    private String userId;
    private List<ImgEntity> imgList = new ArrayList<>();
    private List<DynamicCommentEntity> commmentItemList = new ArrayList<>();
    private int id;
    private String linkImg;
    private String linkContent;
    private String ziXunId;
    private int userType;

    //新增来源(小组和机构)
    private String group_id;
    private String group_name;


    public String getGruop_id() {
        return group_id;
    }

    public void setGruop_id(String gruop_id) {
        this.group_id = gruop_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getZiXunId() {
        return ziXunId;
    }

    public void setZiXunId(String ziXunId) {
        this.ziXunId = ziXunId;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public String getLinkContent() {
        return linkContent;
    }

    public void setLinkContent(String linkContent) {
        this.linkContent = linkContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public List<DynamicCommentEntity> getCommmentItemList() {
        return commmentItemList;
    }

    public List<DynamicCommentEntity> getComments(int dynamicitementity_id) {
        return DataSupport.where("dynamicitementity_id = ?", String.valueOf(dynamicitementity_id)).find(DynamicCommentEntity.class);
    }

    public void setCommmentItemList(List<DynamicCommentEntity> commmentItemList) {
        this.commmentItemList = commmentItemList;
    }


    public List<ImgEntity> getImgList() {
        if (imgList == null) {
            return new ArrayList<>();
        } else {
            return imgList;
        }
    }

    public List<ImgEntity> getImgListFromDb(int dynamicitementity_id) {
        return DataSupport.where("dynamicitementity_id = ?", String.valueOf(dynamicitementity_id)).find(ImgEntity.class);
    }

    public void setImgList(List<ImgEntity> imgList) {
        this.imgList = imgList;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getZanCount() {
        return zanCount;
    }

    public void setZanCount(int zanCount) {
        this.zanCount = zanCount;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHasZan() {
        return hasZan;
    }

    public void setHasZan(boolean hasZan) {
        this.hasZan = hasZan;
    }
    //添加一条评论
//    public void addComment(DynamicCommentEntity dynamicCommentEntity) {
//        this.commmentItemList.add(dynamicCommentEntity);
//    }
}
