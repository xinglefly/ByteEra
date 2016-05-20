package com.zhicai.byteera.activity.bean;

import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

@Table(name = "GuessPicMission")
public class GuessPicMisson implements Serializable {

    private int id;

    // 上级分类的id
    private int levelid;
    private String missiontitle;
    private String missionimg;
    private int missionstatus;
    private String missionurl;

    private int filesize;

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevelid() {
        return levelid;
    }

    public String getMissionimg() {
        return missionimg;
    }

    public int getMissionstatus() {
        return missionstatus;
    }

    public String getMissiontitle() {
        return missiontitle;
    }

    public String getMissionurl() {
        return missionurl;
    }

    public void setLevelid(int levelid) {
        this.levelid = levelid;
    }

    public void setMissionimg(String missionimg) {
        this.missionimg = missionimg;
    }

    public void setMissionstatus(int missionstatus) {
        this.missionstatus = missionstatus;
    }

    public void setMissiontitle(String missiontitle) {
        this.missiontitle = missiontitle;
    }

    public void setMissionurl(String missionurl) {
        this.missionurl = missionurl;
    }

    @Override
    public String toString() {
        return "GuessPicMisson{" +
                "id=" + id +
                ", levelid=" + levelid +
                ", missiontitle='" + missiontitle + '\'' +
                ", missionimg='" + missionimg + '\'' +
                ", missionstatus=" + missionstatus +
                ", missionurl='" + missionurl + '\'' +
                ", filesize='" + filesize + '\'' +
                '}';
    }

    public GuessPicMisson() {
    }

    public GuessPicMisson(int id,int levelid, String missiontitle, String missionimg, int missionstatus, String missionurl,int filesize) {
        this.id = id;
        this.levelid = levelid;
        this.missiontitle = missiontitle;
        this.missionimg = missionimg;
        this.missionstatus = missionstatus;
        this.missionurl = missionurl;
        this.filesize = filesize;
    }



    //guessgame
    public GuessPicMisson(int levelid, String missiontitle, String missionimg, String missionurl, int missionstatus) {
        this.levelid = levelid;
        this.missiontitle = missiontitle;
        this.missionimg = missionimg;
        this.missionstatus = missionstatus;
        this.missionurl = missionurl;
    }


    public GuessPicMisson(int id, int levelid, int missionstatus) {
        this.id = id;
        this.levelid = levelid;
        this.missionstatus = missionstatus;
    }

}
