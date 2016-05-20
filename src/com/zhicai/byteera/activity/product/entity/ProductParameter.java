package com.zhicai.byteera.activity.product.entity;


import java.io.Serializable;

/**
 * Created by chenzhenxing on 15/12/17.
 */
@SuppressWarnings("unused")
public class ProductParameter implements Serializable {

    private int type;
    private int income_value;
    private int limit_value;
    private String company_id;
    private boolean isFrist;

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIncome_value() {
        return income_value;
    }

    public void setIncome_value(int income_value) {
        this.income_value = income_value;
    }

    public int getLimit_value() {
        return limit_value;
    }

    public void setLimit_value(int limit_value) {
        this.limit_value = limit_value;
    }

    public boolean isFrist() {
        return isFrist;
    }

    public void setIsFrist(boolean isFrist) {
        this.isFrist = isFrist;
    }

    @Override
    public String toString() {
        return "ProductParameter{" +
                "type=" + type +
                ", income_value=" + income_value +
                ", limit_value=" + limit_value +
                ", company_id=" + company_id +
                ", isFrist=" + isFrist +
                '}';
    }

    public ProductParameter() {
    }

    public ProductParameter(boolean isFrist,String company_id,int type, int income_value, int limit_value) {
        this.type = type;
        this.income_value = income_value;
        this.limit_value = limit_value;
        this.company_id = company_id;
        this.isFrist = isFrist;
    }
}
