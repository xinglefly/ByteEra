package com.zhicai.byteera.activity.message.entity;

import com.zhicai.byteera.commonutil.StringUtil;

import java.io.Serializable;

public class ContactInfo implements Serializable, Comparable<ContactInfo> {
    private String name;
    private String id;
    private String phone;
    private String email;
    private String qq;

    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public String toString() {
        return "ContactInfo [name=" + name + ", id=" + id + ", phone=" + phone
                + ", email=" + email + ", qq=" + qq + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public int compareTo(ContactInfo another) {
        return StringUtil.getPinYinHeadChar(this.getName()).charAt(0) - StringUtil.getPinYinHeadChar(another.getName()).charAt(0);

    }

}
