package com.zhicai.byteera.activity.myhome.activity;

/**
 * Created by bing on 2015/5/21.
 */
public class ZhanjiEntity {
    private String part;
    private String time;
    private int status;
    private String caibi;

    public ZhanjiEntity() {
    }

    public ZhanjiEntity(String part, String time, int status, String caibi) {
        this.part = part;
        this.time = time;
        this.status = status;
        this.caibi = caibi;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCaibi() {
        return caibi;
    }

    public void setCaibi(String caibi) {
        this.caibi = caibi;
    }
}
