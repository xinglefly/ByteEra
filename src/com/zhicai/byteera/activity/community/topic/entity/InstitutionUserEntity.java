package com.zhicai.byteera.activity.community.topic.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhicai.byteera.commonutil.StringUtil;

/** Created by bing on 2015/6/17. */
public class InstitutionUserEntity implements Parcelable,Comparable<InstitutionUserEntity> {
    private String userId;
    private String nickName;
    private String headPortrait;

    protected InstitutionUserEntity(Parcel in) {
        userId = in.readString();
        nickName = in.readString();
        headPortrait = in.readString();
    }

    public InstitutionUserEntity() {
    }

    public static final Creator<InstitutionUserEntity> CREATOR = new Creator<InstitutionUserEntity>() {
        @Override
        public InstitutionUserEntity createFromParcel(Parcel in) {
            return new InstitutionUserEntity(in);
        }

        @Override
        public InstitutionUserEntity[] newArray(int size) {
            return new InstitutionUserEntity[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    @Override public int compareTo(InstitutionUserEntity another) {
        return StringUtil.getPinYinHeadChar(this.getNickName()).charAt(0) - StringUtil.getPinYinHeadChar(another.getNickName()).charAt(0);
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(nickName);
        dest.writeString(headPortrait);
    }


    public InstitutionUserEntity(String userId, String nickName, String headPortrait) {
        this.userId = userId;
        this.nickName = nickName;
        this.headPortrait = headPortrait;
    }
}
