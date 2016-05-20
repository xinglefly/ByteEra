package com.zhicai.byteera.activity.bean;

import java.io.Serializable;

/**
 * Created by zhenxing on 2015/5/6.
 */
public class DayTask implements Serializable{

    private String id;                 // 任务ID
    private String abbr;               // 任务简写
    private String name;               // 任务名称
    private int coin;               // 奖励财币数
    private int status;

    private int user_coin;          // 财币
    private int user_rank;          // 财币排名
    private int con_login;          // 连续登录天数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getUser_coin() {
        return user_coin;
    }

    public void setUser_coin(int user_coin) {
        this.user_coin = user_coin;
    }

    public int getUser_rank() {
        return user_rank;
    }

    public void setUser_rank(int user_rank) {
        this.user_rank = user_rank;
    }

    public int getCon_login() {
        return con_login;
    }

    public void setCon_login(int con_login) {
        this.con_login = con_login;
    }

    @Override
    public String toString() {
        return "DayTask{" +
                "status=" + status +
                ", id='" + id + '\'' +
                ", abbr='" + abbr + '\'' +
                ", name='" + name + '\'' +
                ", coin=" + coin +
                '}';
    }

    public DayTask(String id, String abbr, String name, int coin, int status) {
        this.id = id;
        this.abbr = abbr;
        this.name = name;
        this.coin = coin;
        this.status = status;
    }

    public DayTask(int user_coin, int user_rank, int con_login) {
        this.user_coin = user_coin;
        this.user_rank = user_rank;
        this.con_login = con_login;
    }
}
