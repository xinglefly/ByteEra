package com.zhicai.byteera.activity.community.topic.viewinterface;

import android.app.Activity;

import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.community.topic.entity.InstitutionUserEntity;

/** Created by bing on 2015/6/30. */
public interface PublishOpinionActivityIV {
    Activity getContext();

    String getContent();

    boolean isStartLoginActivity();

    void setSendEnabled(boolean b);

    void showInstitution(InstitutionUserEntity institutionItem);

    void showInstitutionCompany(FinancingCompanyEntity institutionCompanyItem);
}
