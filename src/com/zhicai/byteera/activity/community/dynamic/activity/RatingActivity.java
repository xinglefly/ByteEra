package com.zhicai.byteera.activity.community.dynamic.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.dynamic.InstitutionAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.MyRatingBar;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/** Created by lieeber on 15/8/11. */
public class RatingActivity extends BaseActivity {
    @Bind(R.id.rating_bar_1) MyRatingBar mRatingBar1;
    @Bind(R.id.rating_bar_2) MyRatingBar mRatingBar2;
    @Bind(R.id.rating_bar_3) MyRatingBar mRatingBar3;
    @Bind(R.id.head_view) HeadViewMain mheadView;
    private int riskScore;
    private int expScore;
    private int incomeScore;
    private String institutionid;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.rating_activity);
        ButterKnife.bind(this);
    }

    @Override protected void initData() {
        riskScore = getIntent().getIntExtra("riskScore", 0);
        expScore = getIntent().getIntExtra("expScore", 0);
        incomeScore = getIntent().getIntExtra("incomeScore", 0);
        institutionid = getIntent().getStringExtra("institutionid");
        if (riskScore != 0 || expScore != 0 || incomeScore != 0) {
            mRatingBar1.setEnabled(false);
            mRatingBar2.setEnabled(false);
            mRatingBar3.setEnabled(false);
        }
        mRatingBar1.setRating(riskScore);
        mRatingBar2.setRating(expScore);
        mRatingBar3.setRating(incomeScore);
    }

    @Override protected void updateUI() {

    }

    @Override protected void processLogic() {
        mheadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }

    @OnClick({R.id.tv_commit})
    public void click(View view) {
        final int num1 = mRatingBar1.getNum();
        final int num2 = mRatingBar2.getNum();
        final int num3 = mRatingBar3.getNum();
        final String userId = PreferenceUtils.getInstance(baseContext).readUserInfo().getUser_id();
        if(TextUtils.isEmpty(userId)){
            ActivityUtil.startActivity(baseContext,new Intent(baseContext,LoginActivity.class));
            return;
        }

        if (riskScore != 0 || expScore != 0 || incomeScore != 0) {
            ToastUtil.showToastText("对不起,您已经参与过评分");
        } else {

            if (TextUtils.isEmpty(userId)) {
                ActivityUtil.startActivity(baseContext, new Intent(this, LoginActivity.class));
                return;
            }
            InstitutionAttribute.DoEvaluateReq req = InstitutionAttribute.DoEvaluateReq.newBuilder().
                    setExpScore(num2).setIncomeScore(num3).setRiskScore(num1).setInstUserId(institutionid).setMyUserId(userId).build();
            TiangongClient.instance().send("chronos", "do_evaluate", req.toByteArray(), new BaseHandlerClass() {
                public void onSuccess(byte[] buffer) {
                    try {
                        final InstitutionAttribute.DoEvaluateResponse response = InstitutionAttribute.DoEvaluateResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            public void run() {
                                if (response.getErrorno() == 0) {
                                    setResult(response, num1, num2, num3);
                                }
                            }
                        });
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void setResult(InstitutionAttribute.DoEvaluateResponse response, int num1, int num2, int num3) {
        Intent intent = new Intent();
        intent.putExtra("riskScore", num1);
        intent.putExtra("expScore", num2);
        intent.putExtra("incomeScore", num3);
        setResult(Constants.RATING, intent);
        ActivityUtil.finishActivity(baseContext);
    }
}
