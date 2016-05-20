package com.zhicai.byteera.activity.bean;

import java.io.Serializable;

/**
 * Created by zhenxing on 2015/5/6.
 */
@SuppressWarnings("unused")
public class AchievementTask implements Serializable{


    private String achieve_id;
    private int condition_level;
    private String title;
    private int reward_coin;// 达成该成就奖励的财币数
    private String extra_info; // 额外的说明信

    private int jump_point; // APP 内部跳转     jump_point jump_url 最多只能有一项有值，
    private String jump_url;// 跳转到 URL       如果都没有值就是不能跳转的成就

    //boolean
    private boolean time_limited; // 限时成就？ true 是 false 不是，为true时 start_time,end_time就被设置，目前都是false
    private boolean achieve_done; // 成就达成? UI上把该Item设置为不可点击状态，提示用户该成就已达成
    private boolean level_done;   // 成就的该level已达成？如果为true，需要提示用户领取奖励，false 还未达成
    //根据上面判断
    private boolean start_time;
    private boolean end_time;


    public String getAchieve_id() {
        return achieve_id;
    }

    public void setAchieve_id(String achieve_id) {
        this.achieve_id = achieve_id;
    }

    public int getCondition_level() {
        return condition_level;
    }

    public void setCondition_level(int condition_level) {
        this.condition_level = condition_level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReward_coin() {
        return reward_coin;
    }

    public void setReward_coin(int reward_coin) {
        this.reward_coin = reward_coin;
    }

    public String getExtra_info() {
        return extra_info;
    }

    public void setExtra_info(String extra_info) {
        this.extra_info = extra_info;
    }

    public int getjump_point() {
        return jump_point;
    }

    public void setjump_point(int jump_point) {
        this.jump_point = jump_point;
    }

    public String getJump_url() {
        return jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }

    public boolean isTime_limited() {
        return time_limited;
    }

    public void setTime_limited(boolean time_limited) {
        this.time_limited = time_limited;
    }

    public boolean isAchieve_done() {
        return achieve_done;
    }

    public void setAchieve_done(boolean achieve_done) {
        this.achieve_done = achieve_done;
    }

    public boolean isLevel_done() {
        return level_done;
    }

    public void setLevel_done(boolean level_done) {
        this.level_done = level_done;
    }

    public boolean isStart_time() {
        return start_time;
    }

    public void setStart_time(boolean start_time) {
        this.start_time = start_time;
    }

    public boolean isEnd_time() {
        return end_time;
    }

    public void setEnd_time(boolean end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "AchievementTask{" +
                "achieve_id='" + achieve_id + '\'' +
                ", condition_level=" + condition_level +
                ", title='" + title + '\'' +
                ", reward_coin=" + reward_coin +
                ", extra_info='" + extra_info + '\'' +
                ", jump_point=" + jump_point +
                ", jump_url='" + jump_url + '\'' +
                ", time_limited=" + time_limited +
                ", achieve_done=" + achieve_done +
                ", level_done=" + level_done +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                '}';
    }

    public AchievementTask() {
    }


    public AchievementTask(String achieve_id, int condition_level, String title, int reward_coin, String extra_info, boolean time_limited, boolean achieve_done, boolean level_done) {
        this.achieve_id = achieve_id;
        this.condition_level = condition_level;
        this.title = title;
        this.reward_coin = reward_coin;
        this.extra_info = extra_info;
        this.time_limited = time_limited;
        this.achieve_done = achieve_done;
        this.level_done = level_done;
    }
}
