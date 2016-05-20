package com.zhicai.byteera.activity.community.dynamic.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.view.ShowGroupWindow;
import com.zhicai.byteera.activity.community.eventbus.OrganizationEvent;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.service.dynamic.InstitutionAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.MyRatingBar;
import com.zhicai.byteera.widget.OrganizationLinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class OrganizationInfoActivity extends BaseActivity {
    private static final String TAG = OrganizationInfoActivity.class.getSimpleName();

    @Bind(R.id.rating_bar) MyRatingBar myRatingBar;
    @Bind(R.id.tv_rating) TextView tvRating;
    @Bind(R.id.iv_raring_right)ImageView ivRatingRight;
    @Bind(R.id.tv_fans) TextView tvFansCnt;
    @Bind(R.id.iv_fans_right)ImageView ivFansRight;
    @Bind(R.id.tv_title_1) TextView tvTitle1;
    @Bind(R.id.tv_title_2) TextView tvTitle2;
    @Bind(R.id.tv_title_3) TextView tvTitle3;
    @Bind(R.id.iv_1) ImageView iv1;
    @Bind(R.id.iv_2) ImageView iv2;
    @Bind(R.id.iv_3) ImageView iv3;
    @Bind(R.id.ol_1) OrganizationLinearLayout ol1;
    @Bind(R.id.ol_2) OrganizationLinearLayout ol2;
    @Bind(R.id.ol_3) OrganizationLinearLayout ol3;
    @Bind(R.id.rl_container) RelativeLayout rlContainer;
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.line) View line;
    @Bind(R.id.rl_organization) RelativeLayout rlOrganization;

    private ShowGroupWindow pop;
    private boolean isAttention;
    private int position;
    private String institutionId;
    private int fansCnt;
    private int score;
    private int riskScore;
    private int expScore;
    private int incomeScore;


    @Override protected void loadViewLayout() {
        setContentView(R.layout.organization_info);
        ButterKnife.bind(this);
        getIntentData();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getIntentData() {
        institutionId = getIntent().getStringExtra("user_id");
        position = getIntent().getIntExtra("position", 0);
        fansCnt = getIntent().getIntExtra("fansCnt", 0);
        score = getIntent().getIntExtra("score", 0);
        riskScore = getIntent().getIntExtra("riskScore", 0);
        expScore = getIntent().getIntExtra("expScore", 0);
        incomeScore = getIntent().getIntExtra("incomeScore", 0);
        myRatingBar.setRating(score);
        tvRating.setText(score + "分");
        tvFansCnt.setText(fansCnt + "人");

        isAttention = getIntent().getBooleanExtra("isAttention", false);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHeadView.isSHowRightImg(isAttention);
            }
        });
    }

    @OnClick({R.id.iv_raring_right,R.id.iv_fans_right})
    public void clickListener(View v){
        switch(v.getId()){
            case R.id.iv_raring_right:
                Intent intent = new Intent(baseContext, RatingActivity.class);
                intent.putExtra("riskScore", riskScore);
                intent.putExtra("expScore", expScore);
                intent.putExtra("incomeScore", incomeScore);
                intent.putExtra("institutionid",institutionId);
                ActivityUtil.startActivityForResult(baseContext, intent, Constants.RATING);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RATING && resultCode == Constants.RATING) {    //更新评分信息
            riskScore = getIntent().getIntExtra("riskScore", 0);
            expScore = getIntent().getIntExtra("expScore", 0);
            incomeScore = getIntent().getIntExtra("incomeScore", 0);
            int pingjun = (riskScore + expScore + incomeScore) / 3;
            myRatingBar.setRating(pingjun);
        }
    }

    @Override protected void initData() {
        InstitutionAttribute.GetInstitutionCompanyAttrReq sec = InstitutionAttribute.GetInstitutionCompanyAttrReq.newBuilder().
                setInstUserId(institutionId).build();
        TiangongClient.instance().send("chronos", "get_institution_company_attr", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final InstitutionAttribute.GetInstitutionCompanyAttrResponse response = InstitutionAttribute.GetInstitutionCompanyAttrResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().post(new Runnable() {
                        @Override public void run() {
                            if (response.getErrorno() == 0) {
                                InstitutionAttribute.P2PCompanyInfo p2PCompanyInfo = response.getP2PCompanyInfo();
                                InstitutionAttribute.ZhongchouCompanyInfo zhongchouCompanyInfo = response.getZhongchouCompanyInfo();
                                if (!TextUtils.isEmpty(p2PCompanyInfo + "")) {
                                    setP2pInfo(p2PCompanyInfo);
                                } else if (!TextUtils.isEmpty(zhongchouCompanyInfo + "")) {
                                    setZhongChouInfo(zhongchouCompanyInfo);
                                }
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setZhongChouInfo(InstitutionAttribute.ZhongchouCompanyInfo zhongchouCompanyInfo) {
        tvTitle1.setText("基本信息");

        tvTitle2.setText("费用");

        tvTitle3.setText("企业信息");
        ol3.addItem("隶属公司:", zhongchouCompanyInfo.getFullName());
        ol3.addItem("业务模式:", zhongchouCompanyInfo.getModel());
        ol3.addItem("主要产品:", zhongchouCompanyInfo.getProduct());
        ol3.addItem("网址:", zhongchouCompanyInfo.getWebsite());
        ol3.addItem("ICP备案号:", zhongchouCompanyInfo.getIcp());
        ol3.addItem("充值提现限制:", zhongchouCompanyInfo.getRechargeLimit());
        ol3.addItem("起诉金额:", zhongchouCompanyInfo.getLimitAmount());
        updateUI();
    }

    private void setP2pInfo(InstitutionAttribute.P2PCompanyInfo p2PCompanyInfo) {
        tvTitle1.setText("基本信息");
        ol1.addItem("注册资本:", p2PCompanyInfo.getRegisteredCapital());
        ol1.addItem("资金托管:", p2PCompanyInfo.getTrusteeship());
        ol1.addItem("自动投标:", p2PCompanyInfo.getAutoBid() ? "支持" : "不支持");
        ol1.addItem("债权转让:", p2PCompanyInfo.getDebtTransfer() ? "随时" : "固定");
        ol1.addItem("投标保障:", p2PCompanyInfo.getBidEnsure());//"100%本息"
        ol1.addItem("保障模式:", p2PCompanyInfo.getEnsureModel());
        ol1.addItem("融资情况:", p2PCompanyInfo.getFinancingHistory());


        tvTitle2.setText("费用");
        ol2.addItem("管理费:", p2PCompanyInfo.getManageCost());
        ol2.addItem("充值费:", p2PCompanyInfo.getRechargeCost());
        ol2.addItem("提现费:", p2PCompanyInfo.getWithdrawCost());
        ol2.addItem("VIP费用:", p2PCompanyInfo.getVipYearlyPayment());

        tvTitle3.setText("企业信息");
        ol3.addItem("隶属公司:", p2PCompanyInfo.getFullName());
        ol3.addItem("所在地:", p2PCompanyInfo.getArea());
        ol3.addItem("上线时间:", p2PCompanyInfo.getOnlineTime());
        ol3.addItem("法人:", p2PCompanyInfo.getCorporateRepresentative());
        ol3.addItem("ICP备案号:", p2PCompanyInfo.getIcp());
        ol3.addItem("客服电话:", p2PCompanyInfo.getCustomerService());
        updateUI();
    }

    @Override protected void updateUI() {
        tvTitle1.post(new Runnable() {
            @Override public void run() {
                iv1.setY(tvTitle1.getY() + UIUtils.dip2px(baseContext, 3));
            }
        });

        tvTitle2.post(new Runnable() {
            @Override public void run() {
                iv2.setY(tvTitle2.getY() + UIUtils.dip2px(baseContext, 3));
            }
        });

        tvTitle3.post(new Runnable() {
            @Override public void run() {
                iv3.setY(tvTitle3.getY() + UIUtils.dip2px(baseContext, 3));
            }
        });

        rlContainer.post(new Runnable() {
            @Override public void run() {
                line.setMinimumHeight(rlContainer.getHeight());
            }
        });

    }


    @Override protected void processLogic() {
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });

        mHeadView.isSHowRightImg(true);
        mHeadView.setRightImgClickListener(new HeadViewMain.RightImgClickListner() {
            @Override
            public void onRightImgClick() {
                View rootView = getWindow().getDecorView();
                rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                pop = new ShowGroupWindow(baseContext, itemsOnclick);
                pop.showAtLocation(rlOrganization, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    //为弹出窗口实现监听类
    public View.OnClickListener itemsOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pop.dismiss();
            switch (v.getId()){
                case R.id.tv_cancle_joined:
                    cancleAttention();
                    break;
            }
        }
    };

    public void cancleAttention(){
        if(TextUtils.isEmpty(MyApp.getInstance().getUserId())){
            ActivityUtil.startActivity(baseContext, new Intent(baseContext, LoginActivity.class));
            return;
        }
        final InstitutionAttribute.WatchInstitutionReq req = InstitutionAttribute.WatchInstitutionReq.newBuilder().setUserId(institutionId).setOtherUserId(userId).build();
        TiangongClient.instance().send("chronos", "watch_institution", req.toByteArray(), new BaseHandlerClass() {
            @Override public void onSuccess(byte[] buffer) {
                try {
                    final InstitutionAttribute.WatchInstitutionResponse response = InstitutionAttribute.WatchInstitutionResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        final boolean isFocus = response.getWatched();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mHeadView.isSHowRightImg(false);
                                ToastUtil.showToastText("取消关注");
                                EventBus.getDefault().post(new OrganizationEvent(position, isFocus));
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
