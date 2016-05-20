package com.zhicai.byteera.activity.myhome.activity;

/**
 * Created by bing on 2015/5/21.
 */
public class TixianEntity {
    private String date;
    private String money;
    private String account;
    private int status;

    public TixianEntity() {
    }

    public TixianEntity(String date, String money, String account, int status) {
        this.date = date;
        this.money = money;
        this.account = account;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
