package com.zhicai.byteera.activity.shouyi.entity;

import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by chenzhenxing on 15/11/23.
 */
@SuppressWarnings("unused")
@Table(name="CompaniesIdList")
public class CompaniesIdList implements Serializable{
    private int id;
    private String company_id;
    private String company_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    @Override
    public String toString() {
        return "CompaniesIdList{" +
                "id=" + id +
                ", company_id='" + company_id + '\'' +
                ", company_name='" + company_name + '\'' +
                '}';
    }

    public CompaniesIdList() {
    }

    public CompaniesIdList(String company_id, String company_name) {
        this.company_id = company_id;
        this.company_name = company_name;
    }
}
