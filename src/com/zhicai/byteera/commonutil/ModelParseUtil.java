package com.zhicai.byteera.commonutil;

import android.util.Log;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.bean.Consult;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicCommentEntity;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.entity.ImgEntity;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.activity.community.group.GroupEntity;
import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.community.topic.entity.InstitutionUserEntity;
import com.zhicai.byteera.activity.community.topic.entity.NormalUserEntity;
import com.zhicai.byteera.activity.community.topic.entity.OpinionCommentEntity;
import com.zhicai.byteera.activity.community.topic.entity.OpinionEntity;
import com.zhicai.byteera.activity.community.topic.entity.TopicEntity;
import com.zhicai.byteera.activity.myhome.User;
import com.zhicai.byteera.activity.product.entity.CompanyEntity;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.activity.product.entity.ProductInfo;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.Common.LicaiProduct;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.dynamic.Dynamic;
import com.zhicai.byteera.service.dynamic.DynamicGroupV2;
import com.zhicai.byteera.service.information.InformationSecondary;
import com.zhicai.byteera.service.product.FinancingProduct;
import com.zhicai.byteera.service.topic.Topic;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/6/30. */
public class ModelParseUtil {
    private static final String TAG = ModelParseUtil.class.getSimpleName();

    public static List<ProductEntity> ProductEntityParse(List<LicaiProduct> productList) {
        List<ProductEntity> entities = new ArrayList<>();
        for (Common.LicaiProduct productItem : productList) {
            entities.add(new ProductEntity(productItem.getProductId(), productItem.getSmallImage(), productItem.getProductTitle(), productItem.getProductIncome(), productItem.getProductIncomeInLimit(), productItem.getProductCoin(),
                    productItem.getProductLimit(), productItem.getProductWatch(), productItem.getProgress(), productItem.getProductUrl(), productItem.getCompanyName()));
        }
        return entities;
    }

    public static List<Consult> ZixunParse(List<InformationSecondary.TZixun> list) {
        List<Consult> ziXunlist = new ArrayList<>();
        for (InformationSecondary.TZixun item : list) {
            Consult consult = new Consult();
            consult.setAvatarUrl(item.getImageUrl());
            consult.setCommentNum(item.getCommentTimes());
            consult.setPublishTime(item.getPublishTime());
            consult.setTitle(item.getTitle());
            consult.setZiXunId(item.getZixunId());
            ziXunlist.add(consult);

        }
        return ziXunlist;
    }


    public static List<DynamicEntity> DynamicEntityParse(Dynamic.GetUserMsgResponse response, String userId) {
        ArrayList<DynamicEntity> dynamicItemList = new ArrayList<>();
        ArrayList<DynamicCommentEntity> commentItemEntities = null;
        for (int i = 0; i < response.getItemCount(); i++) {
            Dynamic.MsgCommonField msgCommonField = checkMsgType(response.getItem(i));
            DynamicEntity entity = new DynamicEntity();
            int number = response.getItem(i).getType().getNumber();
            switch (number) {
                case 1:
                    entity.setType(1);
                    entity.setContent(response.getItem(i).getM1().getContent());
                    entity.setAvatarUrl(msgCommonField.getHeadPortrait());
                    entity.setUserId(msgCommonField.getUserId());
                    entity.setZanCount(msgCommonField.getZanCount());
                    entity.setMsgId(msgCommonField.getMsgId());
                    entity.setCommentNum(msgCommonField.getCommentCount());
                    entity.setPublishTime(msgCommonField.getPublishTime());
                    entity.setUserType(msgCommonField.getUserType().getNumber());
                    ArrayList<ImgEntity> imgEntities = new ArrayList<ImgEntity>();
                    //动态中的图片
                    for (int a = 0; a < response.getItem(i).getM1().getImageCount(); a++) {
                        ImgEntity entity1 = new ImgEntity();
                        entity1.setImgUrl(response.getItem(i).getM1().getImage(a));
                        entity1.save();
                        imgEntities.add(entity1);
                    }
                    entity.setImgList(imgEntities);
                    //循环一遍 只是为了判断是否点过赞
                    entity.setHasZan(false);
                    for (int j = 0; j < msgCommonField.getZanList().size(); j++) {
                        if (msgCommonField.getZan(j).getUserId().equals(userId)) {
                            entity.setHasZan(true);
                            break;
                        }
                    }
                    entity.setNickName(msgCommonField.getNickname());
                    //评论  需要遍历所有的评论 添加到自己的实体  这个有点坑
                    commentItemEntities = new ArrayList<>();
                    for (int k = 0; k < msgCommonField.getCommentCount(); k++) {
                        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
                        dynamicCommentEntity.setNickName(msgCommonField.getComment(k).getNickname());
                        dynamicCommentEntity.setContent(msgCommonField.getComment(k).getContent());
                        dynamicCommentEntity.setAvatarUrl(msgCommonField.getComment(k).getHeadPortrait());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setUserId(msgCommonField.getComment(k).getUserId());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setCommentIndex(msgCommonField.getComment(k).getCommentIndex());
                        commentItemEntities.add(dynamicCommentEntity);
                    }
                    break;
                case 2:
                    entity.setType(2);
                    entity.setContent(response.getItem(i).getM2().getContent());
                    entity.setAvatarUrl(msgCommonField.getHeadPortrait());
                    entity.setUserId(msgCommonField.getUserId());
                    entity.setMsgId(msgCommonField.getMsgId());
                    entity.setZanCount(msgCommonField.getZanCount());
                    entity.setPublishTime(msgCommonField.getPublishTime());
                    entity.setUserType(msgCommonField.getUserType().getNumber());
                    entity.setZiXunId(response.getItem(i).getM2().getZixunId());
                    entity.setLinkContent(response.getItem(i).getM2().getTitle());
                    entity.setLinkImg(response.getItem(i).getM2().getSmallImage());
                    entity.setHasZan(false);
                    for (int j = 0; j < msgCommonField.getZanList().size(); j++) {
                        if (msgCommonField.getZan(j).getUserId().equals(userId)) {
                            entity.setHasZan(true);
                            break;
                        }
                    }
                    entity.setNickName(msgCommonField.getNickname());
                    commentItemEntities = new ArrayList<>();
                    for (int k = 0; k < msgCommonField.getCommentCount(); k++) {
                        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
                        dynamicCommentEntity.setNickName(msgCommonField.getComment(k).getNickname());
                        dynamicCommentEntity.setContent(msgCommonField.getComment(k).getContent());
                        dynamicCommentEntity.setAvatarUrl(msgCommonField.getComment(k).getHeadPortrait());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setUserId(msgCommonField.getComment(k).getUserId());
                        dynamicCommentEntity.setCommentIndex(msgCommonField.getComment(k).getCommentIndex());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        commentItemEntities.add(dynamicCommentEntity);
                    }
                    break;
                case 3:
                    entity.setType(3);
                    entity.setContent(response.getItem(i).getM3().getContent());
                    entity.setAvatarUrl(msgCommonField.getHeadPortrait());
                    entity.setUserId(msgCommonField.getUserId());
                    entity.setMsgId(msgCommonField.getMsgId());
                    entity.setZanCount(msgCommonField.getZanCount());
                    entity.setPublishTime(msgCommonField.getPublishTime());
                    entity.setUserType(msgCommonField.getUserType().getNumber());
                    entity.setHasZan(false);
                    for (int j = 0; j < msgCommonField.getZanList().size(); j++) {
                        if (msgCommonField.getZan(j).getUserId().equals(userId)) {
                            entity.setHasZan(true);
                            break;
                        }
                    }
                    entity.setNickName(msgCommonField.getNickname());
                    commentItemEntities = new ArrayList<>();
                    for (int k = 0; k < msgCommonField.getCommentCount(); k++) {
                        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
                        dynamicCommentEntity.setNickName(msgCommonField.getComment(k).getNickname());
                        dynamicCommentEntity.setContent(msgCommonField.getComment(k).getContent());
                        dynamicCommentEntity.setAvatarUrl(msgCommonField.getComment(k).getHeadPortrait());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setUserId(msgCommonField.getComment(k).getUserId());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setCommentIndex(msgCommonField.getComment(k).getCommentIndex());
                        commentItemEntities.add(dynamicCommentEntity);
                    }
                    break;
            }
//            DataSupport.saveAll(commentItemEntities);
            entity.setCommmentItemList(commentItemEntities);
            dynamicItemList.add(entity);
        }
//        DataSupport.saveAll(dynamicItemList);
        return dynamicItemList;
    }


    /**获取机构主页**/
    public static List<DynamicEntity> OrganizationDynamicEntityParse(Dynamic.GetInstituteResponse response, String userId) {
        ArrayList<DynamicEntity> dynamicItemList = new ArrayList<>();
        ArrayList<DynamicCommentEntity> commentItemEntities = null;
        for (int i = 0; i < response.getItemCount(); i++) {
            Dynamic.MsgCommonField msgCommonField = checkMsgType(response.getItem(i));
            DynamicEntity entity = new DynamicEntity();
            int number = response.getItem(i).getType().getNumber();
            switch (number) {
                case 1:
                    entity.setType(1);
                    entity.setContent(response.getItem(i).getM1().getContent());
                    entity.setAvatarUrl(msgCommonField.getHeadPortrait());
                    entity.setUserId(msgCommonField.getUserId());
                    entity.setZanCount(msgCommonField.getZanCount());
                    entity.setMsgId(msgCommonField.getMsgId());
                    entity.setCommentNum(msgCommonField.getCommentCount());
                    entity.setPublishTime(msgCommonField.getPublishTime());
                    entity.setUserType(msgCommonField.getUserType().getNumber());
                    ArrayList<ImgEntity> imgEntities = new ArrayList<ImgEntity>();
                    //动态中的图片
                    for (int a = 0; a < response.getItem(i).getM1().getImageCount(); a++) {
                        ImgEntity entity1 = new ImgEntity();
                        entity1.setImgUrl(response.getItem(i).getM1().getImage(a));
                        entity1.save();
                        imgEntities.add(entity1);
                    }
                    entity.setImgList(imgEntities);
                    //循环一遍 只是为了判断是否点过赞
                    entity.setHasZan(false);
                    for (int j = 0; j < msgCommonField.getZanList().size(); j++) {
                        if (msgCommonField.getZan(j).getUserId().equals(userId)) {
                            entity.setHasZan(true);
                            break;
                        }
                    }
                    entity.setNickName(msgCommonField.getNickname());
                    //评论  需要遍历所有的评论 添加到自己的实体  这个有点坑
                    commentItemEntities = new ArrayList<>();
                    for (int k = 0; k < msgCommonField.getCommentCount(); k++) {
                        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
                        dynamicCommentEntity.setNickName(msgCommonField.getComment(k).getNickname());
                        dynamicCommentEntity.setContent(msgCommonField.getComment(k).getContent());
                        dynamicCommentEntity.setAvatarUrl(msgCommonField.getComment(k).getHeadPortrait());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setUserId(msgCommonField.getComment(k).getUserId());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setCommentIndex(msgCommonField.getComment(k).getCommentIndex());
                        commentItemEntities.add(dynamicCommentEntity);
                    }
                    break;
                case 2:
                    entity.setType(2);
                    entity.setContent(response.getItem(i).getM2().getContent());
                    entity.setAvatarUrl(msgCommonField.getHeadPortrait());
                    entity.setUserId(msgCommonField.getUserId());
                    entity.setMsgId(msgCommonField.getMsgId());
                    entity.setZanCount(msgCommonField.getZanCount());
                    entity.setPublishTime(msgCommonField.getPublishTime());
                    entity.setUserType(msgCommonField.getUserType().getNumber());
                    entity.setZiXunId(response.getItem(i).getM2().getZixunId());
                    entity.setLinkContent(response.getItem(i).getM2().getTitle());
                    entity.setLinkImg(response.getItem(i).getM2().getSmallImage());
                    entity.setHasZan(false);
                    for (int j = 0; j < msgCommonField.getZanList().size(); j++) {
                        if (msgCommonField.getZan(j).getUserId().equals(userId)) {
                            entity.setHasZan(true);
                            break;
                        }
                    }
                    entity.setNickName(msgCommonField.getNickname());
                    commentItemEntities = new ArrayList<>();
                    for (int k = 0; k < msgCommonField.getCommentCount(); k++) {
                        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
                        dynamicCommentEntity.setNickName(msgCommonField.getComment(k).getNickname());
                        dynamicCommentEntity.setContent(msgCommonField.getComment(k).getContent());
                        dynamicCommentEntity.setAvatarUrl(msgCommonField.getComment(k).getHeadPortrait());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setUserId(msgCommonField.getComment(k).getUserId());
                        dynamicCommentEntity.setCommentIndex(msgCommonField.getComment(k).getCommentIndex());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        commentItemEntities.add(dynamicCommentEntity);
                    }
                    break;
                case 3:
                    entity.setType(3);
                    entity.setContent(response.getItem(i).getM3().getContent());
                    entity.setAvatarUrl(msgCommonField.getHeadPortrait());
                    entity.setUserId(msgCommonField.getUserId());
                    entity.setMsgId(msgCommonField.getMsgId());
                    entity.setZanCount(msgCommonField.getZanCount());
                    entity.setPublishTime(msgCommonField.getPublishTime());
                    entity.setUserType(msgCommonField.getUserType().getNumber());
                    entity.setHasZan(false);
                    for (int j = 0; j < msgCommonField.getZanList().size(); j++) {
                        if (msgCommonField.getZan(j).getUserId().equals(userId)) {
                            entity.setHasZan(true);
                            break;
                        }
                    }
                    entity.setNickName(msgCommonField.getNickname());
                    commentItemEntities = new ArrayList<>();
                    for (int k = 0; k < msgCommonField.getCommentCount(); k++) {
                        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
                        dynamicCommentEntity.setNickName(msgCommonField.getComment(k).getNickname());
                        dynamicCommentEntity.setContent(msgCommonField.getComment(k).getContent());
                        dynamicCommentEntity.setAvatarUrl(msgCommonField.getComment(k).getHeadPortrait());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setUserId(msgCommonField.getComment(k).getUserId());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setCommentIndex(msgCommonField.getComment(k).getCommentIndex());
                        commentItemEntities.add(dynamicCommentEntity);
                    }
                    break;
            }
//            DataSupport.saveAll(commentItemEntities);
            entity.setCommmentItemList(commentItemEntities);
            dynamicItemList.add(entity);
        }
//        DataSupport.saveAll(dynamicItemList);
        return dynamicItemList;
    }


    /**获取所有动态**/
    public static List<DynamicEntity> DynamicFragmentItemEntityParse(Dynamic.GetMsgResponse response, String userId) {
        ArrayList<DynamicEntity> dynamicItemList = new ArrayList<>();
        ArrayList<DynamicCommentEntity> commentItemEntities = null;
        for (int i = 0; i < response.getItemCount(); i++) {
            String groupName = response.getItem(i).getGroupName();//来源小组或机构
            String groupId = response.getItem(i).getGroupId();
            Dynamic.MsgCommonField msgCommonField = checkMsgType(response.getItem(i));

            DynamicEntity entity = new DynamicEntity();
            int number = response.getItem(i).getType().getNumber();
            switch (number) {
                case 1:
                    entity.setType(1);
                    entity.setContent(response.getItem(i).getM1().getContent());
                    entity.setAvatarUrl(msgCommonField.getHeadPortrait());
                    entity.setUserId(msgCommonField.getUserId());
                    entity.setZanCount(msgCommonField.getZanCount());
                    entity.setMsgId(msgCommonField.getMsgId());
                    entity.setCommentNum(msgCommonField.getCommentCount());
                    entity.setPublishTime(msgCommonField.getPublishTime());
                    entity.setGruop_id(groupId);
                    entity.setGroup_name(groupName);
                    entity.setUserType(msgCommonField.getUserType().getNumber());
                    ArrayList<ImgEntity> imgEntities = new ArrayList<ImgEntity>();
                    //动态中的图片
                    for (int a = 0; a < response.getItem(i).getM1().getImageCount(); a++) {
                        ImgEntity entity1 = new ImgEntity();
                        entity1.setImgUrl(response.getItem(i).getM1().getImage(a));
                        entity1.save();
                        imgEntities.add(entity1);
                    }
                    entity.setImgList(imgEntities);
                    //循环一遍 只是为了判断是否点过赞
                    entity.setHasZan(false);
                    for (int j = 0; j < msgCommonField.getZanList().size(); j++) {
                        if (msgCommonField.getZan(j).getUserId().equals(userId)) {
                            entity.setHasZan(true);
                            break;
                        }
                    }
                    entity.setNickName(msgCommonField.getNickname());
                    //评论  需要遍历所有的评论 添加到自己的实体  这个有点坑
                    commentItemEntities = new ArrayList<>();
                    for (int k = 0; k < msgCommonField.getCommentCount(); k++) {
                        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
                        dynamicCommentEntity.setNickName(msgCommonField.getComment(k).getNickname());
                        dynamicCommentEntity.setContent(msgCommonField.getComment(k).getContent());
                        dynamicCommentEntity.setAvatarUrl(msgCommonField.getComment(k).getHeadPortrait());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setUserId(msgCommonField.getComment(k).getUserId());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setCommentIndex(msgCommonField.getComment(k).getCommentIndex());
                        commentItemEntities.add(dynamicCommentEntity);
                    }
                    break;
                case 2:
                    entity.setType(2);
                    entity.setContent(response.getItem(i).getM2().getContent());
                    entity.setAvatarUrl(msgCommonField.getHeadPortrait());
                    entity.setUserId(msgCommonField.getUserId());
                    entity.setMsgId(msgCommonField.getMsgId());
                    entity.setZanCount(msgCommonField.getZanCount());
                    entity.setPublishTime(msgCommonField.getPublishTime());
                    entity.setGruop_id(groupId);
                    entity.setGroup_name(groupName);
                    entity.setUserType(msgCommonField.getUserType().getNumber());
                    entity.setZiXunId(response.getItem(i).getM2().getZixunId());
                    entity.setLinkContent(response.getItem(i).getM2().getTitle());
                    entity.setLinkImg(response.getItem(i).getM2().getSmallImage());
                    entity.setHasZan(false);
                    for (int j = 0; j < msgCommonField.getZanList().size(); j++) {
                        if (msgCommonField.getZan(j).getUserId().equals(userId)) {
                            entity.setHasZan(true);
                            break;
                        }
                    }
                    entity.setNickName(msgCommonField.getNickname());
                    commentItemEntities = new ArrayList<>();
                    for (int k = 0; k < msgCommonField.getCommentCount(); k++) {
                        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
                        dynamicCommentEntity.setNickName(msgCommonField.getComment(k).getNickname());
                        dynamicCommentEntity.setContent(msgCommonField.getComment(k).getContent());
                        dynamicCommentEntity.setAvatarUrl(msgCommonField.getComment(k).getHeadPortrait());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setUserId(msgCommonField.getComment(k).getUserId());
                        dynamicCommentEntity.setCommentIndex(msgCommonField.getComment(k).getCommentIndex());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        commentItemEntities.add(dynamicCommentEntity);
                    }
                    break;
                case 3:
                    entity.setType(3);
                    entity.setContent(response.getItem(i).getM3().getContent());
                    entity.setAvatarUrl(msgCommonField.getHeadPortrait());
                    entity.setUserId(msgCommonField.getUserId());
                    entity.setMsgId(msgCommonField.getMsgId());
                    entity.setZanCount(msgCommonField.getZanCount());
                    entity.setPublishTime(msgCommonField.getPublishTime());
                    entity.setGruop_id(groupId);
                    entity.setGroup_name(groupName);
                    entity.setUserType(msgCommonField.getUserType().getNumber());
                    entity.setHasZan(false);
                    for (int j = 0; j < msgCommonField.getZanList().size(); j++) {
                        if (msgCommonField.getZan(j).getUserId().equals(userId)) {
                            entity.setHasZan(true);
                            break;
                        }
                    }
                    entity.setNickName(msgCommonField.getNickname());
                    commentItemEntities = new ArrayList<>();
                    for (int k = 0; k < msgCommonField.getCommentCount(); k++) {
                        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
                        dynamicCommentEntity.setNickName(msgCommonField.getComment(k).getNickname());
                        dynamicCommentEntity.setContent(msgCommonField.getComment(k).getContent());
                        dynamicCommentEntity.setAvatarUrl(msgCommonField.getComment(k).getHeadPortrait());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setUserId(msgCommonField.getComment(k).getUserId());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setCommentIndex(msgCommonField.getComment(k).getCommentIndex());
                        commentItemEntities.add(dynamicCommentEntity);
                    }
                    break;
            }
//            DataSupport.saveAll(commentItemEntities);
            entity.setCommmentItemList(commentItemEntities);
            dynamicItemList.add(entity);
        }
//        DataSupport.saveAll(dynamicItemList);
        return dynamicItemList;
    }

    private static Dynamic.MsgCommonField checkMsgType(Dynamic.LicaiquanItem item) {
        int number = item.getType().getNumber();
        Dynamic.MsgCommonField commonField = null;
        switch (number) {
            case 1:
                commonField = item.getM1().getCommonField();
                break;
            case 2:
                commonField = item.getM2().getCommonField();
                break;
            case 3:
                commonField = item.getM3().getCommonField();
                break;
        }
        return commonField;
    }

    public static List<TopicEntity> TopicEntityParse(List<Topic.TopicItem> itemList) {
        List<TopicEntity> topicItemEntities = new ArrayList<>();
        for (Topic.TopicItem item : itemList) {
            TopicEntity entity = new TopicEntity();
            entity.setAnswerNum(item.getAnswerNum());
            entity.setTopicName(item.getTopicName());
            entity.setHotNum(item.getHotNum());
            entity.setTopicContent(item.getTopicContent());
            entity.setTopicId(item.getTopicId());
            entity.setTopicImgUrl(item.getTopicImgUrl());
            entity.setTopicNum(item.getTopicNum());
            entity.setImgList(item.getImageList());
            topicItemEntities.add(entity);
        }
        return topicItemEntities;
    }

    public static List<OpinionEntity> OpioionEntityParse(List<Topic.OpinionItem> itemList, int type) {
        List<OpinionEntity> opinionItemEntities = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            Topic.OpinionItem opinionItem = itemList.get(i);
            OpinionEntity entity1 = new OpinionEntity();
            if (i == 0 && type == 0) {        //设置为热度类型的第一条
                entity1.setHotFirst(true);
            }
            if (i == 0 && type == 1) {      //设置为最新类型的第一条
                entity1.setNewFirst(true);
            }
            if (type == 0) {
                entity1.setType(0);             //设置为热度类型
            } else {
                entity1.setType(1);             //设置为最新类型
            }
            entity1.setCommentNum(opinionItem.getCommentNum());
            entity1.setHotHum(opinionItem.getHotNum());
            entity1.setOpinionId(opinionItem.getOpinionId());
            entity1.setOpinionAvatar(opinionItem.getOpinionAvatar());
            entity1.setOpinionName(opinionItem.getOpinionName());
            entity1.setPublish_time(opinionItem.getPublishTime());
            entity1.setUserId(opinionItem.getUserId());
            entity1.setContent(opinionItem.getContent());
            entity1.setIsZaning(opinionItem.getZaning());
            //机构信息
            if (opinionItem.hasInstituteUser()){
                Common.InstituteUser instituteUser = opinionItem.getInstituteUser();
                if (instituteUser != null) {
                    InstitutionUserEntity institutionUserEntity = new InstitutionUserEntity();
                    institutionUserEntity.setUserId(instituteUser.getUserId());
                    institutionUserEntity.setHeadPortrait(instituteUser.getHeadPortrait());
                    institutionUserEntity.setNickName(instituteUser.getNickname());
                    entity1.setInstitutionUserEntity(institutionUserEntity);
                }
            }

            //机构的父类,判断是否能点击进去
            if (opinionItem.hasFinancingCompany()){
                Common.FinancingCompany financingCompany = opinionItem.getFinancingCompany();
                if (financingCompany!=null){
                    entity1.setFinancingCompanyEntity(new FinancingCompanyEntity(financingCompany.getCompanyId(),financingCompany.getCompanyName(),financingCompany.getCompanyImage(),financingCompany.getCompanyType(),false));
                }
            }else{
                entity1.setFinancingCompanyEntity(new FinancingCompanyEntity());
            }


            //设置同样点赞的人的信息
            List<Common.NormalUser> recommendUserList = opinionItem.getSameRecommendUserList();
            List<NormalUserEntity> normalUserEntities = new ArrayList<NormalUserEntity>();
            for (int k = 0; k < recommendUserList.size(); k++) {
                NormalUserEntity normalUserEntity = new NormalUserEntity();
                Common.NormalUser normalUser = recommendUserList.get(k);
                normalUserEntity.setNickName(normalUser.getNickname());
                normalUserEntity.setUserId(normalUser.getUserId());
                normalUserEntity.setHeadPorait(normalUser.getHeadPortrait());
                normalUserEntities.add(normalUserEntity);
            }
            entity1.setNormalUserEntities(normalUserEntities);
            //设置评论人的信息
            List<Common.Comment> commentList = opinionItem.getCommentList();
            List<OpinionCommentEntity> opinionCommentEntities = new ArrayList<>();
            for (int n = 0; n < commentList.size(); n++) {
                Common.Comment comment = commentList.get(n);
                OpinionCommentEntity opinionCommentEntity = new OpinionCommentEntity();
                opinionCommentEntity.setCommentIndex(comment.getCommentIndex());
                opinionCommentEntity.setContent(comment.getContent());
                opinionCommentEntity.setHeadPortrait(comment.getHeadPortrait());
                opinionCommentEntity.setNickName(comment.getNickname());
                opinionCommentEntity.setUserId(comment.getUserId());

                /*******************这一段是设置回复人的信息***************/
                Common.NormalUser toUser = comment.getToUser();
                NormalUserEntity normalUserEntity = new NormalUserEntity();
                normalUserEntity.setHeadPorait(toUser.getHeadPortrait());
                normalUserEntity.setNickName(toUser.getNickname());
                normalUserEntity.setUserId(toUser.getUserId());
                opinionCommentEntity.setToUser(normalUserEntity);
                /*******************完成***************/

                opinionCommentEntities.add(opinionCommentEntity);
            }
            entity1.setOpinionCommentEntities(opinionCommentEntities);
            opinionItemEntities.add(entity1);
        }
        return opinionItemEntities;
    }

    public static OpinionCommentEntity OpinionCommentEntityParse(Common.Comment comment) {
        OpinionCommentEntity opinionCommentEntity = new OpinionCommentEntity();
        opinionCommentEntity.setCommentIndex(comment.getCommentIndex());
        opinionCommentEntity.setContent(comment.getContent());
        opinionCommentEntity.setHeadPortrait(comment.getHeadPortrait());
        opinionCommentEntity.setNickName(comment.getNickname());
        opinionCommentEntity.setUserId(comment.getUserId());

        /*******************这一段是设置回复人的信息***************/
        Common.NormalUser toUser = comment.getToUser();
        NormalUserEntity normalUserEntity = new NormalUserEntity();
        normalUserEntity.setHeadPorait(toUser.getHeadPortrait());
        normalUserEntity.setNickName(toUser.getNickname());
        normalUserEntity.setUserId(toUser.getUserId());
        opinionCommentEntity.setToUser(normalUserEntity);
        /*******************完成***************/
        return opinionCommentEntity;
    }

    public static List<DynamicCommentEntity> DynamicCommentEntityParse(List<Common.Comment> commentList) {
        List<DynamicCommentEntity> commentItemEntities = new ArrayList<>();
        for (int i = 0; i < commentList.size(); i++) {
            Common.Comment comment = commentList.get(i);
            DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
            dynamicCommentEntity.setUserId(comment.getUserId());
            dynamicCommentEntity.setNickName(comment.getNickname());
            dynamicCommentEntity.setAvatarUrl(comment.getHeadPortrait());
            dynamicCommentEntity.setContent(comment.getContent());
            dynamicCommentEntity.setCommentIndex(comment.getCommentIndex());
            dynamicCommentEntity.setPublishTime(comment.getPublishTime());
            commentItemEntities.add(dynamicCommentEntity);
        }
        return commentItemEntities;
    }

    public static List<UserInfoEntity> UserInfoEntityParse(List<UserAttribute.RelationUser> userList) {
        List<UserInfoEntity> userInfoEntities = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            UserAttribute.RelationUser relationUser = userList.get(i);
            UserInfoEntity item = new UserInfoEntity();
            item.setUserType(relationUser.getType().getNumber());
            item.setNickName(relationUser.getNickname());
            item.setAvatarUrl(relationUser.getHeadPortrait());
            item.setmStatus(Math.random() < 0.5 ? UserInfoEntity.Status.ARROW : UserInfoEntity.Status.ADD_ATTENTION);
            item.setUserId(relationUser.getUserId());
            userInfoEntities.add(item);
        }
        return userInfoEntities;
    }

    /**机构列表**/
    public static List<UserInfoEntity> organizationParse(List<Common.InstituteUserWithRelation> organizationList) {
        List<UserInfoEntity> organizationEntities = new ArrayList<>();
        for (Common.InstituteUserWithRelation item:organizationList) {
            UserInfoEntity userInfoEntity = new UserInfoEntity(item.getUser().getUserId(), item.getUser().getHeadPortrait(), item.getUser().getNickname(), item.getWatched());
            organizationEntities.add(userInfoEntity);
        }
        return organizationEntities;
    }


    public static List<UserInfoEntity> UserInfoEntityParse2(List<UserAttribute.FriendUser> userList) {
        List<UserInfoEntity> userInfoEntities = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            UserAttribute.FriendUser relationUser = userList.get(i);
            UserInfoEntity item = new UserInfoEntity();
            item.setNickName(relationUser.getNickname());
            item.setAvatarUrl(relationUser.getHeadPortrait());
            item.setmStatus(Math.random() < 0.5 ? UserInfoEntity.Status.ARROW : UserInfoEntity.Status.ADD_ATTENTION);
            item.setUserId(relationUser.getUserId());
            userInfoEntities.add(item);
        }
        return userInfoEntities;
    }

    /**join organization list**/
    public static List<InstitutionUserEntity> InstitutionUserEntityParse(List<Common.InstituteUser> instituteUserList) {
        ArrayList<InstitutionUserEntity> institutionUserEntities = new ArrayList<>();
        for (Common.InstituteUser instituteUser: instituteUserList) {
            institutionUserEntities.add(new InstitutionUserEntity(instituteUser.getUserId(),instituteUser.getNickname(),instituteUser.getHeadPortrait()));
        }
        return institutionUserEntities;
    }

    //获取所有机构列表
    public static List<FinancingCompanyEntity> CompanyEntityParse(List<Common.FinancingCompany> companyList) {
        final ArrayList<FinancingCompanyEntity> companyEntities = new ArrayList<>();
        for (Common.FinancingCompany company : companyList){
            FinancingCompanyEntity  companyEntity = new FinancingCompanyEntity(company.getCompanyId(),company.getCompanyName(),company.getCompanyImage(),company.getCompanyType(),false);
            companyEntities.add(companyEntity);
        }

        MyApp.getInstance().executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    MyApp.getInstance().db.saveBindingIdAll(companyEntities);
                    Log.d(TAG, "db save institution  ok.-->" + companyEntities.size());
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
        return companyEntities;
    }


    public static List<GroupEntity> GroupEntityParse(List<DynamicGroupV2.LicaiquanGroupWithMembership> groupList) {
        List<GroupEntity> groupEntities = new ArrayList<>();
        for (DynamicGroupV2.LicaiquanGroupWithMembership groupItem:groupList) {
            groupEntities.add(new GroupEntity(groupItem.getGroup().getLicaiquanGroupId(),groupItem.getGroup().getName(),groupItem.getGroup().getImage(),groupItem.getGroup().getDescription(),groupItem.getGroup().getMemberCount(),groupItem.getJoined()));
        }
        //save db
        DbUtils db = DbUtils.create(MyApp.getInstance(), Constants.BYTEERA_DB);
        try {
            if (db.tableIsExist(GroupEntity.class)){
                db.deleteAll(GroupEntity.class);
                Log.d(TAG,"delete groupEntity is succ!");
            }
            db.saveBindingIdAll(groupEntities);
            Log.d(TAG, "save groupEntity db!"+groupEntities.size());
        } catch (DbException e) {
            e.printStackTrace();
        }

        return groupEntities;
    }


    public static List<GroupEntity> JoinGroupEntityParse(List<DynamicGroupV2.LicaiquanGroupEx> groupList) {
        List<GroupEntity> groupEntities = new ArrayList<>();
        for (DynamicGroupV2.LicaiquanGroupEx groupItem:groupList) {
            groupEntities.add(new GroupEntity(groupItem.getLicaiquanGroupId(),groupItem.getName(),groupItem.getImage(),groupItem.getDescription(),groupItem.getMemberCount()));
        }
        return groupEntities;
    }


    /**join group list**/
    public static List<GroupEntity> JoinGroupListParse(List<UserAttribute.WatchGroup> groupList) {
        List<GroupEntity> groupEntities = new ArrayList<>();
        for (UserAttribute.WatchGroup groupItem:groupList) {
            groupEntities.add(new GroupEntity(groupItem.getGroupId(),groupItem.getName(),groupItem.getImage(),groupItem.getDescription(),true));
        }
        return groupEntities;
    }



    /***获取小组动态**/
    public static List<DynamicEntity> GroupDynamicEntityParse(Dynamic.GetGroupMsgResponse response, String userId) {
        ArrayList<DynamicEntity> dynamicItemList = new ArrayList<>();
        ArrayList<DynamicCommentEntity> commentItemEntities = null;
        for (int i = 0; i < response.getItemCount(); i++) {
            String groupName = response.getItem(i).getGroupName();//来源小组或机构
            String groupId = response.getItem(i).getGroupId();
            Dynamic.MsgCommonField msgCommonField = checkMsgType(response.getItem(i));
            DynamicEntity entity = new DynamicEntity();
            int number = response.getItem(i).getType().getNumber();
            switch (number) {
                case 1:
                    entity.setType(1);
                    entity.setContent(response.getItem(i).getM1().getContent());
                    entity.setAvatarUrl(msgCommonField.getHeadPortrait());
                    entity.setUserId(msgCommonField.getUserId());
                    entity.setZanCount(msgCommonField.getZanCount());
                    entity.setMsgId(msgCommonField.getMsgId());
                    entity.setCommentNum(msgCommonField.getCommentCount());
                    entity.setPublishTime(msgCommonField.getPublishTime());

                    entity.setUserType(msgCommonField.getUserType().getNumber());
                    ArrayList<ImgEntity> imgEntities = new ArrayList<ImgEntity>();
                    //动态中的图片
                    for (int a = 0; a < response.getItem(i).getM1().getImageCount(); a++) {
                        ImgEntity entity1 = new ImgEntity();
                        entity1.setImgUrl(response.getItem(i).getM1().getImage(a));
                        entity1.save();
                        imgEntities.add(entity1);
                    }
                    entity.setImgList(imgEntities);
                    //循环一遍 只是为了判断是否点过赞
                    entity.setHasZan(false);
                    for (int j = 0; j < msgCommonField.getZanList().size(); j++) {
                        if (msgCommonField.getZan(j).getUserId().equals(userId)) {
                            entity.setHasZan(true);
                            break;
                        }
                    }
                    entity.setNickName(msgCommonField.getNickname());
                    //评论  需要遍历所有的评论 添加到自己的实体  这个有点坑
                    commentItemEntities = new ArrayList<>();
                    for (int k = 0; k < msgCommonField.getCommentCount(); k++) {
                        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
                        dynamicCommentEntity.setNickName(msgCommonField.getComment(k).getNickname());
                        dynamicCommentEntity.setContent(msgCommonField.getComment(k).getContent());
                        dynamicCommentEntity.setAvatarUrl(msgCommonField.getComment(k).getHeadPortrait());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setUserId(msgCommonField.getComment(k).getUserId());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setCommentIndex(msgCommonField.getComment(k).getCommentIndex());
                        commentItemEntities.add(dynamicCommentEntity);
                    }
                    break;
                case 2:
                    entity.setType(2);
                    entity.setContent(response.getItem(i).getM2().getContent());
                    entity.setAvatarUrl(msgCommonField.getHeadPortrait());
                    entity.setUserId(msgCommonField.getUserId());
                    entity.setMsgId(msgCommonField.getMsgId());
                    entity.setZanCount(msgCommonField.getZanCount());
                    entity.setPublishTime(msgCommonField.getPublishTime());
                    entity.setUserType(msgCommonField.getUserType().getNumber());
                    entity.setZiXunId(response.getItem(i).getM2().getZixunId());
                    entity.setLinkContent(response.getItem(i).getM2().getTitle());
                    entity.setLinkImg(response.getItem(i).getM2().getSmallImage());
                    entity.setHasZan(false);
                    for (int j = 0; j < msgCommonField.getZanList().size(); j++) {
                        if (msgCommonField.getZan(j).getUserId().equals(userId)) {
                            entity.setHasZan(true);
                            break;
                        }
                    }
                    entity.setNickName(msgCommonField.getNickname());
                    commentItemEntities = new ArrayList<>();
                    for (int k = 0; k < msgCommonField.getCommentCount(); k++) {
                        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
                        dynamicCommentEntity.setNickName(msgCommonField.getComment(k).getNickname());
                        dynamicCommentEntity.setContent(msgCommonField.getComment(k).getContent());
                        dynamicCommentEntity.setAvatarUrl(msgCommonField.getComment(k).getHeadPortrait());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setUserId(msgCommonField.getComment(k).getUserId());
                        dynamicCommentEntity.setCommentIndex(msgCommonField.getComment(k).getCommentIndex());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        commentItemEntities.add(dynamicCommentEntity);
                    }
                    break;
                case 3:
                    entity.setType(3);
                    entity.setContent(response.getItem(i).getM3().getContent());
                    entity.setAvatarUrl(msgCommonField.getHeadPortrait());
                    entity.setUserId(msgCommonField.getUserId());
                    entity.setMsgId(msgCommonField.getMsgId());
                    entity.setZanCount(msgCommonField.getZanCount());
                    entity.setPublishTime(msgCommonField.getPublishTime());
                    entity.setUserType(msgCommonField.getUserType().getNumber());
                    entity.setHasZan(false);
                    for (int j = 0; j < msgCommonField.getZanList().size(); j++) {
                        if (msgCommonField.getZan(j).getUserId().equals(userId)) {
                            entity.setHasZan(true);
                            break;
                        }
                    }
                    entity.setNickName(msgCommonField.getNickname());
                    commentItemEntities = new ArrayList<>();
                    for (int k = 0; k < msgCommonField.getCommentCount(); k++) {
                        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
                        dynamicCommentEntity.setNickName(msgCommonField.getComment(k).getNickname());
                        dynamicCommentEntity.setContent(msgCommonField.getComment(k).getContent());
                        dynamicCommentEntity.setAvatarUrl(msgCommonField.getComment(k).getHeadPortrait());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setUserId(msgCommonField.getComment(k).getUserId());
                        dynamicCommentEntity.setPublishTime(msgCommonField.getComment(k).getPublishTime());
                        dynamicCommentEntity.setCommentIndex(msgCommonField.getComment(k).getCommentIndex());
                        commentItemEntities.add(dynamicCommentEntity);
                    }
                    break;
            }
            //同组保存一次
            entity.setGruop_id(groupId);
            entity.setGroup_name(groupName);
//            DataSupport.saveAll(commentItemEntities);
            entity.setCommmentItemList(commentItemEntities);
            dynamicItemList.add(entity);
        }
//        DataSupport.saveAll(dynamicItemList);
        return dynamicItemList;
    }

    public static List<User> UserParse(List<UserAttribute.FriendUser> userList) {
        List<User> mList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            User user = new User();
            user.setAvatar(userList.get(i).getHeadPortrait());
            user.setUsername(userList.get(i).getNickname());
            user.setUserId(userList.get(i).getUserId());
            mList.add(user);
        }
        return mList;
    }

    /**获取产品详情**/
    public static List<CompanyEntity> CompanyParse(List<FinancingProduct.Company> companyList) {
        List<CompanyEntity> companyEntities = new ArrayList<>();
        for (int i = 0; i < companyList.size(); i++) {
            CompanyEntity entity = new CompanyEntity();
            entity.setSmallImage(companyList.get(i).getSmallImage());
            entity.setName(companyList.get(i).getName());
            entity.setSellableProducts(companyList.get(i).getSellableProducts());
            List<ProductEntity> productEntities = new ArrayList<>();
            List<LicaiProduct> productList = companyList.get(i).getProductList();

            for (Common.LicaiProduct productItem : productList) {
                productEntities.add(new ProductEntity(productItem.getProductId(), productItem.getSmallImage(), productItem.getProductTitle(), productItem.getProductIncome(), productItem.getProductIncomeInLimit(),productItem.getProductCoin(),
                        productItem.getProductLimit(), productItem.getProductWatch(),productItem.getProgress(),productItem.getProductUrl(),productItem.getCompanyName()));
            }
            entity.setProductEntities(productEntities);
            companyEntities.add(entity);
        }
        return companyEntities;
    }

    public static List<FinancingCompanyEntity> FinancingCompanyEntityParse(List<Common.FinancingCompany> companyList) {
        final ArrayList<FinancingCompanyEntity> companyEntities = new ArrayList<>();
        for (Common.FinancingCompany company : companyList){
            FinancingCompanyEntity  companyEntity = new FinancingCompanyEntity(company.getCompanyId(),company.getCompanyName(),company.getCompanyImage(),company.getCompanyType(),company.getProductsNumber());
            companyEntities.add(companyEntity);
        }
        return companyEntities;
    }


    public static List<ProductEntity> getPositionProductListParse(List<LicaiProduct> productsList) {
        List<ProductEntity> productEntities = new ArrayList<>();
        for (LicaiProduct productItem : productsList) {
            ProductEntity productEntity = new ProductEntity(productItem.getProductId(), productItem.getSmallImage(), productItem.getProductTitle(), productItem.getProductIncome(), productItem.getProductIncomeInLimit(), productItem.getProductCoin(),
                    productItem.getProductLimit(), productItem.getProductWatch(), productItem.getProgress(), productItem.getProductUrl(), productItem.getCompanyName());
            productEntities.add(productEntity);
//            Log.d(TAG, "-productEntity->" + productEntity.toString() + productEntities.size());
        }
        return productEntities;
    }

    public static ProductInfo getProductDetailParse(Common.ProductInfo productInfo) {
        return new ProductInfo(productInfo.getTitle(),productInfo.getProgress(),productInfo.getIncome(),productInfo.getLimit(),
                productInfo.getAmount(),productInfo.getRepaymentType(),productInfo.getFee(),productInfo.getPublishDate(),
                productInfo.getEndDate(),productInfo.getDetailUrl(),productInfo.getCompanyUrl(),productInfo.getRiskInfoUrl(),
                productInfo.getOriginUrl(),productInfo.getCompanyName(),
                productInfo.getStandardProductInfo().getCurrentDeposit(),productInfo.getStandardProductInfo().getYuebao(),
                productInfo.getStandardProductInfo().getFixedDeposit());

    }

}
