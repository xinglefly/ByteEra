package com.zhicai.byteera.activity.bean;

/**
 * Created by bing on 2015/4/15.
 */
public class KnowWealthComment  {
    private String commentUserName;
    private String commentUserPicUrl;
    private String time;
    private String content;

    public String getCommentUserPicUrl() {
        return commentUserPicUrl;
    }

    public void setCommentUserPicUrl(String commentUserPicUrl) {
        this.commentUserPicUrl = commentUserPicUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentUserName() {

        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }
}
