package com.zhicai.byteera.activity.community.topic.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.community.topic.entity.InstitutionUserEntity;
import com.zhicai.byteera.activity.community.topic.viewinterface.InstitutionListIV;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.LoadingDialogShow;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.dynamic.InstitutionAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Created by bing on 2015/7/1. */
public class InstitutionListPre {
    private static final String TAG = InstitutionListPre.class.getSimpleName();

    private Activity mContext;
    private InstitutionListIV institutionListIV;
    private List<InstitutionUserEntity> institutionUserEntities;
    private List<FinancingCompanyEntity> companyEntityLists;
    private LoadingDialogShow dialog = null;

    public InstitutionListPre(InstitutionListIV institutionListIV) {
        this.institutionListIV = institutionListIV;
        this.mContext = institutionListIV.getContext();
        dialog = new LoadingDialogShow(mContext);
    }


    public void getData1(final int company_type){
            //TODO Fragment会自动懒加载，导致第一次会请求两次接口
//            if (!MyApp.getInstance().db.tableIsExist(FinancingCompanyEntity.class)){
//                getInstutionList();
//            }
            try {
                List<FinancingCompanyEntity> companyLists = MyApp.getInstance().db.findAll(Selector.from(FinancingCompanyEntity.class).where("company_type","=",company_type));
                companyEntityLists = new ArrayList<>();
                if (companyLists!=null && companyLists.size()>0){
                    for (FinancingCompanyEntity entity: companyLists) {
                        companyEntityLists.add(new FinancingCompanyEntity(entity.getCompany_id(),entity.getCompany_name(),entity.getCompany_image(),entity.getCompany_type()));
                    }
                    Log.d(TAG, "company_type-->" + company_type + ",size-->" + companyEntityLists.size());
                    institutionListIV.setData1(companyEntityLists);
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
    }


    /***获取全部机构列表***/
    public void getInstutionList(){
        InstitutionAttribute.FinancingCompanyListReq sec = InstitutionAttribute.FinancingCompanyListReq.newBuilder().build();
        TiangongClient.instance().send("chronos", "get_financing_company_list", sec.toByteArray(), new BaseHandlerClass() {

            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final InstitutionAttribute.FinancingCompanyListResponse response = InstitutionAttribute.FinancingCompanyListResponse.parseFrom(buffer);
                    Log.d(TAG, "institution -->" + response.getErrorno() + "," + response.getErrorDescription() + ",company-->" + response.getFinancingCompanyCount());
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getErrorno() == 0) {
                                List<Common.FinancingCompany> financingCompanyList = response.getFinancingCompanyList();
                                companyEntityLists = ModelParseUtil.CompanyEntityParse(financingCompanyList);
                                Collections.sort(companyEntityLists);
                                institutionListIV.setData1(companyEntityLists);
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getData() {
        int institutionVersion = PreferenceUtils.getInstance(mContext).getInstitutionVersion();
        InstitutionAttribute.InstitutionListReq req = InstitutionAttribute.InstitutionListReq.newBuilder().setClientVersion(Math.round(100)).build();
        TiangongClient.instance().send("chronos", "get_institution_list", req.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final InstitutionAttribute.InstitutionListResponse response = InstitutionAttribute.InstitutionListResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override public void run() {
                            if (response.getErrorno() == 0) {
                                int serverVersion = response.getServerVersion();
                                PreferenceUtils.getInstance(mContext).setInstitutionVersion(serverVersion);
                                List<Common.InstituteUser> instituteUserList = response.getInstituteUserList();
                                institutionUserEntities = ModelParseUtil.InstitutionUserEntityParse(instituteUserList);
                                institutionListIV.setData(institutionUserEntities);
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void finish(int position) {
        Intent intent = new Intent();
        intent.putExtra("institutionCompanyItem", companyEntityLists.get(position));
        mContext.setResult(Constants.SELECT_FINISH, intent);
        ActivityUtil.finishActivity(mContext);
    }
}
