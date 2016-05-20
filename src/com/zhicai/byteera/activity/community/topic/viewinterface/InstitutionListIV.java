package com.zhicai.byteera.activity.community.topic.viewinterface;

import android.app.Activity;

import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.community.topic.entity.InstitutionUserEntity;

import java.util.List;

/** Created by bing on 2015/7/1. */
public interface InstitutionListIV {
     Activity getContext();
     void setData(List<InstitutionUserEntity> institutionUserEntities);
     void setData1(List<FinancingCompanyEntity> companyEntities);
}
