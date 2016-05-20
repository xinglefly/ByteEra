package com.zhicai.byteera.activity.community.topic.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

import java.util.List;

/** Created by bing on 2015/5/27. */
public class TopicEntity extends DataSupport implements Parcelable {
    private String topicId;
    private String topicImgUrl;
    private String topicName;
    private String topicContent;
    private int topicNum;
    private int hotNum;
    private int answerNum;
    private List<String> imgList;


    private  boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    protected TopicEntity(Parcel in) {
        topicId = in.readString();
        topicImgUrl = in.readString();
        topicName = in.readString();
        topicContent = in.readString();
        topicNum = in.readInt();
        hotNum = in.readInt();
        answerNum = in.readInt();
        imgList = in.createStringArrayList();
    }

    public static final Creator<TopicEntity> CREATOR = new Creator<TopicEntity>() {
        @Override
        public TopicEntity createFromParcel(Parcel in) {
            return new TopicEntity(in);
        }

        @Override
        public TopicEntity[] newArray(int size) {
            return new TopicEntity[size];
        }
    };

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public TopicEntity() {}


    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }


    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public int getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(int topicNum) {
        this.topicNum = topicNum;
    }

    public int getHotNum() {
        return hotNum;
    }

    public void setHotNum(int hotNum) {
        this.hotNum = hotNum;
    }

    public int getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(int answerNum) {
        this.answerNum = answerNum;
    }

    public String getTopicImgUrl() {
        return topicImgUrl;
    }

    public void setTopicImgUrl(String topicImgUrl) {
        this.topicImgUrl = topicImgUrl;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(topicId);
        dest.writeString(topicImgUrl);
        dest.writeString(topicName);
        dest.writeString(topicContent);
        dest.writeInt(topicNum);
        dest.writeInt(hotNum);
        dest.writeInt(answerNum);
        dest.writeStringList(imgList);
    }
}
