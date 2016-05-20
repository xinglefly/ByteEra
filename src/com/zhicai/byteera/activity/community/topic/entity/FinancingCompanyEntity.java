package com.zhicai.byteera.activity.community.topic.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Table;
import com.zhicai.byteera.commonutil.StringUtil;

@SuppressWarnings("unused")
@Table(name = "FinancingCompany")
public class FinancingCompanyEntity implements Parcelable, Comparable<FinancingCompanyEntity> {

    private int id;
    private String company_id;
    private String company_name;
    private String company_image;         // 头像
    private int company_type;             // 2:p2p, 3:众筹, 4:票据，5:直销银行
    private int company_number;
    private String header;
    private boolean isChecked;


    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

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

    public String getCompany_image() {
        return company_image;
    }

    public void setCompany_image(String company_image) {
        this.company_image = company_image;
    }

    public int getCompany_type() {
        return company_type;
    }

    public void setCompany_type(int company_type) {
        this.company_type = company_type;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getCompany_number() {
        return company_number;
    }

    public void setCompany_number(int company_number) {
        this.company_number = company_number;
    }

    protected FinancingCompanyEntity(Parcel in) {
        company_id = in.readString();
        company_name = in.readString();
        company_image = in.readString();
        company_type = in.readInt();
        company_number = in.readInt();
    }

    public static final Creator<FinancingCompanyEntity> CREATOR = new Creator<FinancingCompanyEntity>() {
        @Override
        public FinancingCompanyEntity createFromParcel(Parcel in) {
            return new FinancingCompanyEntity(in);
        }

        @Override
        public FinancingCompanyEntity[] newArray(int size) {
            return new FinancingCompanyEntity[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(company_id);
        dest.writeString(company_name);
        dest.writeString(company_image);
        dest.writeInt(company_type);
        dest.writeInt(company_number);
    }


    @Override
    public String toString() {
        return "FinancingCompanyEntity{" +
                "id=" + id +
                ", company_id='" + company_id + '\'' +
                ", company_name='" + company_name + '\'' +
                ", company_image='" + company_image + '\'' +
                ", company_type=" + company_type +
                ", header='" + header + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }

    public FinancingCompanyEntity() {
    }

    public FinancingCompanyEntity(int id,String company_id, boolean isChecked) {
        this.id = id;
        this.company_id = company_id;
        this.isChecked = isChecked;
    }

    public FinancingCompanyEntity(String company_id, String company_name, String company_image, int company_type) {
        this.company_id = company_id;
        this.company_name = company_name;
        this.company_image = company_image;
        this.company_type = company_type;
    }

    public FinancingCompanyEntity(String company_id, String company_name, String company_image, int company_type, boolean isChecked) {
        this.company_id = company_id;
        this.company_name = company_name;
        this.company_image = company_image;
        this.company_type = company_type;
        this.isChecked = isChecked;
    }

    public FinancingCompanyEntity(String company_id, String company_name, String company_image, int company_type, int company_number) {
        this.company_id = company_id;
        this.company_name = company_name;
        this.company_image = company_image;
        this.company_type = company_type;
        this.company_number = company_number;
    }

    @Override
    public int compareTo(FinancingCompanyEntity another) {
        return StringUtil.getPinYinHeadChar(this.getCompany_name()).charAt(0) - StringUtil.getPinYinHeadChar(another.getCompany_name()).charAt(0);
    }

}
