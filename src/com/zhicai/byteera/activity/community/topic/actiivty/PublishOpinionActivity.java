package com.zhicai.byteera.activity.community.topic.actiivty;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lidroid.xutils.exception.DbException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.community.topic.entity.InstitutionUserEntity;
import com.zhicai.byteera.activity.community.topic.presenter.PublishOpinionActivityPre;
import com.zhicai.byteera.activity.community.topic.viewinterface.PublishOpinionActivityIV;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.dynamic.InstitutionAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishOpinionActivity extends BaseActivity implements PublishOpinionActivityIV {
    private static final String TAG = PublishOpinionActivity.class.getSimpleName();

    public static final int GET_INSTITUTION = 0x000002;
    @Bind(R.id.et_comment) EditText etComment;
    @Bind(R.id.tv_right) View tvSend;
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.ll_institution) LinearLayout llInstitution;
    @Bind(R.id.iv_institution) ImageView ivInstitution;
    @Bind(R.id.tv_institution) TextView tvInstitution;
    @Bind(R.id.tv_add_institution) TextView tvAddInstitution;

    private PublishOpinionActivityPre publishOpinionActivityPre;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.publish_opinion_activity);
        ButterKnife.bind(this);
        publishOpinionActivityPre = new PublishOpinionActivityPre(this);
        mHeadView.setLeftTextClickListener(new HeadViewMain.LeftTextClickListener() {
            @Override public void onLeftTextClick() {
                UIUtils.hideKeyboard(baseContext);
                ActivityUtil.finishActivity(baseContext);
            }
        });
        mHeadView.setRightTextClickListener(new HeadViewMain.RightTextClickListener() {
            @Override
            public void onRightTextClick() {
                if (!TextUtils.isEmpty(etComment.getText().toString().trim())) {
                    publishOpinionActivityPre.pushOpinion();
                } else {
                    ToastUtil.showToastText("发布动态内容不能为空");
                }
            }
        });
    }

    @Override protected void initData() {
        publishOpinionActivityPre.getIntentData();
        try {
            if (!db.tableIsExist(FinancingCompanyEntity.class)){
                getInstutionList();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override protected void updateUI() {

    }

    @Override protected void processLogic() {

    }

    @OnClick({R.id.rl_add_institution})
    public void clickListener(View view) {
        switch (view.getId()) {
            case R.id.rl_add_institution:   //查看机构列表
                ActivityUtil.startActivityForResult(baseContext, new Intent(baseContext, InstitutionListActivity.class), PublishOpinionActivity.GET_INSTITUTION);
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GET_INSTITUTION:
                if (resultCode == Constants.SELECT_FINISH) {
                    publishOpinionActivityPre.setOnActivityResult(data);
                }
        }
    }

    @Override public Activity getContext() {
        return this;
    }

    @Override public String getContent() {
        return etComment.getText().toString();
    }

    @Override public void setSendEnabled(boolean b) {
        tvSend.setEnabled(b);
    }

    @Override public void showInstitution(InstitutionUserEntity institutionItem) {

    }

    @Override
    public void showInstitutionCompany(FinancingCompanyEntity institutionCompanyItem) {
        tvAddInstitution.setText("");
        llInstitution.setVisibility(View.VISIBLE);
        ivInstitution.setVisibility(View.GONE);
//        ImageLoader.getInstance().displayImage(institutionCompanyItem.getCompany_image(), ivInstitution);//不要显示机构的图像
        tvInstitution.setText(institutionCompanyItem.getCompany_name());
    }


    public void getInstutionList() {
        InstitutionAttribute.FinancingCompanyListReq sec = InstitutionAttribute.FinancingCompanyListReq.newBuilder().build();
        TiangongClient.instance().send("chronos", "get_financing_company_list", sec.toByteArray(), new BaseHandlerClass() {

            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    InstitutionAttribute.FinancingCompanyListResponse response = InstitutionAttribute.FinancingCompanyListResponse.parseFrom(buffer);
                    Log.d(TAG, "company list -->" + response.getErrorno() + "," + response.getErrorDescription() + ",company-->" + response.getFinancingCompanyCount());
                    if (response.getErrorno()==0) {
                        List<Common.FinancingCompany> financingCompanyList = response.getFinancingCompanyList();
                        final List<FinancingCompanyEntity> institutionLists = new ArrayList<FinancingCompanyEntity>();
                        for (Common.FinancingCompany company : financingCompanyList) {
                            institutionLists.add(new FinancingCompanyEntity(company.getCompanyId(), company.getCompanyName(), company.getCompanyImage(), company.getCompanyType()));
                        }
                        MyApp.getInstance().executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    db.saveBindingIdAll(institutionLists);
                                    Log.d(TAG, "db save institution ok.-->" + institutionLists.size());
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
