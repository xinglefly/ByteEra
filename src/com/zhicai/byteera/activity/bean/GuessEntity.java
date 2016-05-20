package com.zhicai.byteera.activity.bean;

import java.io.Serializable;

/**
 * guess entity
 */
@SuppressWarnings("unused")
public class GuessEntity implements Serializable {

    private static final long serialVersionUID = -4870463110310204638L;

    public int levelid;
    public int missionid;
    public String backgroundimg;
    public String content;
    public String tv_explain;

    public String downloadurl;

    // public int resid;

//    public int guesstype; // 0-综合；1-初级；2-中级；3-高级；


    public GuessEntity() {
    }


    public int getLevelid() {
        return levelid;
    }

    public void setLevelid(int levelid) {
        this.levelid = levelid;
    }

    public int getMissionid() {
        return missionid;
    }

    public void setMissionid(int missionid) {
        this.missionid = missionid;
    }

    public String getBackgroundimg() {
        return backgroundimg;
    }

    public void setBackgroundimg(String backgroundimg) {
        this.backgroundimg = backgroundimg;
    }

    public String getcontent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public String gettv_explain() {
        return tv_explain;
    }

    public void settv_explain(String tv_explain) {
        this.tv_explain = tv_explain;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }


    @Override
    public String toString() {
        return "GuessEntity{" +
                "levelid='" + levelid + '\'' +
                ", missionid='" + missionid + '\'' +
                ", backgroundimg='" + backgroundimg + '\'' +
                ", content='" + content + '\'' +
                ", tv_explain='" + tv_explain + '\'' +
                ", downloadurl='" + downloadurl + '\'' +
                '}';
    }

    public GuessEntity(int levelid, int missionid, String backgroundimg, String content, String tv_explain, String downloadurl) {
        this.levelid = levelid;
        this.missionid = missionid;
        this.backgroundimg = backgroundimg;
        this.content = content;
        this.tv_explain = tv_explain;
        this.downloadurl = downloadurl;
//        this.guesstype = guesstype;
    }
}
