package com.zhicai.byteera.activity.product.entity;

import java.io.Serializable;

/**
 * Created by chenzhenxing on 15/12/22.
 */
@SuppressWarnings("unused")
public class ProductInfo implements Serializable {

    private String title;                                  //产品标题
    private float progress;                                //进度
    private float income;                                  //收益
    private int limit;                                     //投资期限(天)
    private int amount;                                    //金额(元)
    private String repayment_type;                         //还款方式
    private String fee;                                    //费用
    private String publish_date;                           //发布日期
    private String end_date;                               //结息日期
    private String detail_url;                             //产品详情链接
    private String company_url;                            //公司信息链接
    private String risk_info_url;                          //风险控制链接
    private String origin_url;                             //原始链接
    private String company_name;                           //平台名称
    private float current_deposit;                         //活期存款
    private float yuebao;                                  //余额宝
    private float fixed_deposit;                           //六个月定期存款


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRepayment_type() {
        return repayment_type;
    }

    public void setRepayment_type(String repayment_type) {
        this.repayment_type = repayment_type;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    public String getCompany_url() {
        return company_url;
    }

    public void setCompany_url(String company_url) {
        this.company_url = company_url;
    }

    public String getRisk_info_url() {
        return risk_info_url;
    }

    public void setRisk_info_url(String risk_info_url) {
        this.risk_info_url = risk_info_url;
    }

    public String getOrigin_url() {
        return origin_url;
    }

    public void setOrigin_url(String origin_url) {
        this.origin_url = origin_url;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public float getCurrent_deposit() {
        return current_deposit;
    }

    public void setCurrent_deposit(float current_deposit) {
        this.current_deposit = current_deposit;
    }

    public float getYuebao() {
        return yuebao;
    }

    public void setYuebao(float yuebao) {
        this.yuebao = yuebao;
    }

    public float getFixed_deposit() {
        return fixed_deposit;
    }

    public void setFixed_deposit(float fixed_deposit) {
        this.fixed_deposit = fixed_deposit;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "title='" + title + '\'' +
                ", progress=" + progress +
                ", income=" + income +
                ", limit=" + limit +
                ", amount=" + amount +
                ", repayment_type='" + repayment_type + '\'' +
                ", fee='" + fee + '\'' +
                ", publish_date='" + publish_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", detail_url='" + detail_url + '\'' +
                ", company_url='" + company_url + '\'' +
                ", risk_info_url='" + risk_info_url + '\'' +
                ", origin_url='" + origin_url + '\'' +
                ", company_name='" + company_name + '\'' +
                ", current_deposit=" + current_deposit +
                ", yuebao=" + yuebao +
                ", fixed_deposit=" + fixed_deposit +
                '}';
    }

    public ProductInfo() {
    }


    public ProductInfo(String title, float progress, float income, int limit, int amount, String repayment_type, String fee, String publish_date, String end_date,
                       String detail_url, String company_url, String risk_info_url, String origin_url, String company_name,
                       float current_deposit,float yuebao,float fixed_deposit) {
        this.title = title;
        this.progress = progress;
        this.income = income;
        this.limit = limit;
        this.amount = amount;
        this.repayment_type = repayment_type;
        this.fee = fee;
        this.publish_date = publish_date;
        this.end_date = end_date;
        this.detail_url = detail_url;
        this.company_url = company_url;
        this.risk_info_url = risk_info_url;
        this.origin_url = origin_url;
        this.company_name = company_name;
        this.current_deposit = current_deposit;
        this.yuebao = yuebao;
        this.fixed_deposit = fixed_deposit;
    }
}
