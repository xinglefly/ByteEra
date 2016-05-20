/**
 *
 */
package com.zhicai.byteera.activity.bean;

import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;


@Table(name = "GuessPicLevle")
public class GuessPicLevel implements Serializable {

    private int id;
    private String levelname;
    private String levelimg;
    private int levelcount;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public String getLevelimg() {
        return levelimg;
    }

    public void setLevelimg(String levelimg) {
        this.levelimg = levelimg;
    }

    public int getLevelcount() {
        return levelcount;
    }

    public void setLevelcount(int levelcount) {
        this.levelcount = levelcount;
    }

    @Override
    public String toString() {
        return "GuessPicLevel{" +
                "id=" + id +
                ", levelname='" + levelname + '\'' +
                ", levelimg='" + levelimg + '\'' +
                ", levelcount=" + levelcount +
                '}';
    }

    public GuessPicLevel() {
    }


    public GuessPicLevel(int id,String levelname, String levelimg, int levelcount) {
        this.id = id;
        this.levelname = levelname;
        this.levelimg = levelimg;
        this.levelcount = levelcount;
    }

}
