/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhicai.byteera.activity.bean;

import com.easemob.chat.EMContact;
import com.lidroid.xutils.db.annotation.Table;
import com.zhicai.byteera.service.register.Register;

import java.io.Serializable;

@Table(name = "ZCUser")
public class ZCUser extends EMContact implements Serializable {
    private int unreadMsgCount;

    private String id;

    // 作为内部嵌套式的方式，获取逻辑服务器上的联系人来插入到原有的数据库中
    private String header;      //用户头像
    private String user_id;     //用户id
    private String mobile_phone;    //手机号码
    private String nickname;        //昵称
    private String sex;             //性别
    private String city;            //城市
    private int fansCount;          //粉丝数
    private int watchUserCnt;       //关注人数
    private int coin;               //金币 数
    private int avaliableCash;      //可用现金
    private String invitationCode;  //本人的邀请码
    private String chatAccount;        //聊天账号
    private int FriendCnt;             //好友数
    private int collectCnt;            //我的收藏数
    private int dongtaiCnt;           //我的动态数
    private String birthday;            //生日    格式：2015-03-05
    private String identifyCard;             //身份证
    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    private String avatar;

    public ZCUser(Register.LoginResponse response) {
        nickname = response.getNickname();
        user_id = response.getUserId();
        mobile_phone = response.getMobilePhone();
        int number = response.getSex().getNumber();
        if (number == 1) {
            sex = "男";
        } else {
            sex = "女";
        }
        city = response.getCity();
        chatAccount = response.getChatAccount();
        fansCount = response.getFansUserCnt();
        watchUserCnt = response.getWatchUserCnt();
        coin = response.getCoin();
        header = response.getHeadPortrait();
        fansCount = response.getFansUserCnt();
        avaliableCash = response.getAvaliableCash();
        invitationCode = response.getInvitationCode();
        FriendCnt = response.getFriendCnt();
        collectCnt = response.getCollectCnt();
        dongtaiCnt = response.getDongtaiCnt();
        birthday = response.getBirthday();
        identifyCard = response.getIdentifyCard();
        code = response.getInvitationCode();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public int getFriendCnt() {
        return FriendCnt;
    }

    public void setFriendCnt(int friendCnt) {
        FriendCnt = friendCnt;
    }

    public int getCollectCnt() {
        return collectCnt;
    }

    public void setCollectCnt(int collectCnt) {
        this.collectCnt = collectCnt;
    }

    public int getDongtaiCnt() {
        return dongtaiCnt;
    }

    public void setDongtaiCnt(int dongtaiCnt) {
        this.dongtaiCnt = dongtaiCnt;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdentifyCard() {
        return identifyCard;
    }

    public void setIdentifyCard(String identifyCard) {
        this.identifyCard = identifyCard;
    }


    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getWatchUserCnt() {
        return watchUserCnt;
    }

    public void setWatchUserCnt(int watchUserCnt) {
        this.watchUserCnt = watchUserCnt;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getChatAccount() {
        return chatAccount;
    }

    public void setChatAccount(String chatAccount) {
        this.chatAccount = chatAccount;
    }

    public int getAvaliableCash() {
        return avaliableCash;
    }

    public void setAvaliableCash(int avaliableCash) {
        this.avaliableCash = avaliableCash;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getUnreadMsgCount() {
        return unreadMsgCount;
    }

    public void setUnreadMsgCount(int unreadMsgCount) {
        this.unreadMsgCount = unreadMsgCount;
    }

    @Override
    public int hashCode() {
        return 17 * getUsername().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || !(o instanceof ZCUser)) && getUsername().equals(((ZCUser) o).getUsername());
    }

    public String getUser_id() {
        if (user_id == null) {
            return "";
        } else {
            return user_id;
        }
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Override
    public String toString() {
        return "User [unreadMsgCount=" + unreadMsgCount + ", header=" + header + ", user_id=" + user_id
                + ", mobile_phone=" + mobile_phone + ", nickname=" + nickname + ", sex=" + sex + ", city=" + city
                + ", chat_account=" + chatAccount + "]";
    }

    public ZCUser() {
        super();
    }

    public ZCUser(String user_id, String mobile_phone, String chat_account) {
        super();
        this.user_id = user_id;
        this.mobile_phone = mobile_phone;
        this.chatAccount = chat_account;
    }

    public ZCUser(String user_id, String chatAccount, String nickname, String avatar) {
        this.user_id = user_id;
        this.chatAccount = chatAccount;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    public void setData() {

    }

    public String getCode() {
        return this.code;
    }
}
