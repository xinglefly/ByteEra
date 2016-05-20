package com.zhicai.byteera.activity.bean;

import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by zhenxing on 2015/6/24.
 * 此数据是用来保存每个大关卡里面的小关卡，然后通过记录关卡的值计算加锁解锁
 * 解决后期数据动态修改后对其数据进行覆盖
 */
@SuppressWarnings("unused")
@Table(name="GuessDb")
public class GuessDb implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int level_id;
    private int mission_id;
    private int game_id;

    public int getLevel_id() {
        return level_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    public int getMission_id() {
        return mission_id;
    }

    public void setMission_id(int mission_id) {
        this.mission_id = mission_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    @Override
    public String toString() {
        return "GuessDb{" +
                "id=" + id +
                ",level_id=" + level_id +
                ", mission_id=" + mission_id +
                ", game_id=" + game_id +
                '}';
    }

    public GuessDb() {
    }

    public GuessDb(int level_id, int mission_id, int game_id) {
        this.level_id = level_id;
        this.mission_id = mission_id;
        this.game_id = game_id;
    }

    public GuessDb(int id,int level_id, int mission_id, int game_id) {
        this.id = id;
        this.level_id = level_id;
        this.mission_id = mission_id;
        this.game_id = game_id;
    }

}
