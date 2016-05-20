package com.zhicai.byteera.activity.community.topic.entity;

import java.util.List;

/** Created by bing on 2015/6/16. */
public class OpinionEntity {
    private String opinionId;
    private String userId;
    private String opinionAvatar;
    private String opinionName;
    private int publish_time;
    private int hotHum;
    private int commentNum;
    private InstitutionUserEntity institutionUserEntity;
    private List<NormalUserEntity> normalUserEntities;  //与您同样推荐的人信息
    private List<OpinionCommentEntity> opinionCommentEntities ;
    private String content;
    private boolean hotFirst =false;
    private boolean newFirst  = false;
    private boolean showMoreComment = false;
    private int type ;  //类型  热度推荐类型或者最新推荐类型

    private  boolean isZaning;

    //判断是否能进入机构主页
    private FinancingCompanyEntity financingCompanyEntity;

    public boolean isZaning() {
        return isZaning;
    }

    public void setIsZaning(boolean isZaning) {
        this.isZaning = isZaning;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isShowMoreComment() {
        return showMoreComment;
    }

    public void setShowMoreComment(boolean showMoreComment) {
        this.showMoreComment = showMoreComment;
    }

    public String getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(String opinionId) {
        this.opinionId = opinionId;
    }

    public List<OpinionCommentEntity> getOpinionCommentEntities() {
        return opinionCommentEntities;
    }

    public void setOpinionCommentEntities(List<OpinionCommentEntity> opinionCommentEntities) {
        this.opinionCommentEntities = opinionCommentEntities;
    }

    public boolean isHotFirst() {
        return hotFirst;
    }

    public void setHotFirst(boolean hotFirst) {
        this.hotFirst = hotFirst;
    }

    public boolean isNewFirst() {
        return newFirst;
    }

    public void setNewFirst(boolean newFirst) {
        this.newFirst = newFirst;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<NormalUserEntity> getNormalUserEntities() {
        return normalUserEntities;
    }

    public void setNormalUserEntities(List<NormalUserEntity> normalUserEntities) {
        this.normalUserEntities = normalUserEntities;
    }

    public InstitutionUserEntity getInstitutionUserEntity() {
        return institutionUserEntity;
    }

    public void setInstitutionUserEntity(InstitutionUserEntity institutionUserEntity) {
        this.institutionUserEntity = institutionUserEntity;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpinionAvatar() {
        return opinionAvatar;
    }

    public void setOpinionAvatar(String opinionAvatar) {
        this.opinionAvatar = opinionAvatar;
    }

    public String getOpinionName() {
        return opinionName;
    }

    public void setOpinionName(String opinionName) {
        this.opinionName = opinionName;
    }

    public int getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(int publish_time) {
        this.publish_time = publish_time;
    }

    public int getHotHum() {
        return hotHum;
    }

    public void setHotHum(int hotHum) {
        this.hotHum = hotHum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public FinancingCompanyEntity getFinancingCompanyEntity() {
        return financingCompanyEntity;
    }

    public void setFinancingCompanyEntity(FinancingCompanyEntity financingCompanyEntity) {
        this.financingCompanyEntity = financingCompanyEntity;
    }
}
