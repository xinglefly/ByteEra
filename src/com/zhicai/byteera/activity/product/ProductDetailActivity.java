package com.zhicai.byteera.activity.product;

import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.MainActivity;
import com.zhicai.byteera.activity.event.JpushEvent;
import com.zhicai.byteera.activity.product.entity.ProductInfo;
import com.zhicai.byteera.activity.product.view.CircleProgressBar;
import com.zhicai.byteera.activity.product.view.IncomeCalCulateDialog;
import com.zhicai.byteera.commonutil.ActivityController;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.service.product.FinancingProduct;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


@SuppressWarnings("unused")
public class ProductDetailActivity extends BaseActivity {

    @Bind(R.id.head_title) HeadViewMain headTitle;
    @Bind(R.id.tv_protitle) TextView tvProTitle;
    @Bind(R.id.tv_income) TextView tvIncome;
    @Bind(R.id.tv_income_day) TextView tvIncomeDay;
    @Bind(R.id.tv_invest_day) TextView tvInvestDay;
    @Bind(R.id.tv_fixedincome) TextView tvFixedIncome;
    @Bind(R.id.circle_progress) CircleProgressBar circleProgress;
    @Bind(R.id.tv_surplus) TextView tvSurplus;
    @Bind(R.id.tv_raisemoney) TextView tvRaisemoney;
    @Bind(R.id.tv_repayment) TextView tvRepayment;
    @Bind(R.id.tv_cost) TextView tvCost;
    @Bind(R.id.tv_publish_day) TextView tvPublishDay;
    @Bind(R.id.tv_end_day) TextView tvEndDay;
    private ProductInfo productInfo;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.income_access_activity);
        ButterKnife.bind(this);
    }

    @Override protected void initData() {
        getPositionData(getIntent().getExtras().getString("productId"));
    }

    private void setControlValue() {
        headTitle.setTitleName("" + productInfo.getCompany_name());
        tvProTitle.setText("" + productInfo.getTitle());
        tvIncome.setText(setTextStyle(String.format("%.01f", productInfo.getIncome() * 100)+"%"));
        tvIncomeDay.setText(setTextStyle(String.format("%.01f", productInfo.getIncome() * 10000 / 360)+"元"));
        tvInvestDay.setText(setTextStyle(productInfo.getLimit() +"天"));
        tvFixedIncome.setText(setTextStyle(String.format("%.01f", productInfo.getIncome() * 10000 / 360 * 29) + "元"));
        circleProgress.setProgressNotInUiThread((int) (productInfo.getProgress() * 100));
        float money = (float)productInfo.getAmount()*productInfo.getProgress();
        tvSurplus.setText("未售"+String.format("%.02f", ((float)productInfo.getAmount() - money)/10000)+"万元");
        tvRaisemoney.setText("募集金额："+productInfo.getAmount());
        tvRepayment.setText("还款方式："+productInfo.getRepayment_type());
        tvCost.setText("费用："+productInfo.getFee());
        tvPublishDay.setText("发布时间："+productInfo.getPublish_date());
        tvEndDay.setText("结息日期："+productInfo.getEnd_date());
    }

    public Spannable setTextStyle(String str){
        Spannable span = new SpannableString(str);
        span.setSpan(new AbsoluteSizeSpan(28), str.length()-1, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }


    @Override protected void updateUI() {

    }


    @Override protected void processLogic() {
        headTitle.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                returnFragment();
            }
        });
    }

    private void returnFragment() {
        if (getIntent().getExtras().getBoolean("notifaction")){
            ActivityController.finishAllActivitys();
            ActivityUtil.startActivity(baseContext, new Intent(baseContext, MainActivity.class));
            EventBus.getDefault().post(new JpushEvent(1));
        }else ActivityUtil.finishActivity(baseContext);

    }

    @OnClick({R.id.jump_product,R.id.jump_organziation,R.id.jump_risk,R.id.tv_measure,R.id.tv_invest})
    void onClick(View view){
        switch (view.getId()){
            case R.id.jump_product:
                ActivityUtil.startActivity(this, new Intent(this, JumpWebActivity.class).putExtra("name", "详情").putExtra("url", productInfo.getDetail_url()));
                break;
            case R.id.jump_organziation:
                ActivityUtil.startActivity(this,new Intent(this,JumpWebActivity.class).putExtra("name","机构").putExtra("url",productInfo.getCompany_url()));
                break;
            case R.id.jump_risk:
                ActivityUtil.startActivity(this,new Intent(this,JumpWebActivity.class).putExtra("name","风险控制").putExtra("url",productInfo.getRisk_info_url()));
                break;
            case R.id.tv_measure:
                IncomeCalCulateDialog calCulateDialog = new IncomeCalCulateDialog(baseContext,productInfo);
                calCulateDialog.setCancelable(false);
                calCulateDialog.show();
                break;
            case R.id.tv_invest:
                ActivityUtil.startActivity(this,new Intent(this,JumpWebActivity.class).putExtra("name",productInfo.getCompany_name()).putExtra("url", productInfo.getOrigin_url()));
                break;
        }
    }


    public void getPositionData(final String productId) {
        FinancingProduct.GetProductInfoReq sec = FinancingProduct.GetProductInfoReq.newBuilder().setProductId(productId).build();
        TiangongClient.instance().send("chronos", "get_product_info", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final FinancingProduct.GetProductInfoResponse response = FinancingProduct.GetProductInfoResponse.parseFrom(buffer);
                    Log.d("productDetail","res-->"+response.toString());
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            if (response.getErrorno()==0){
                                productInfo = ModelParseUtil.getProductDetailParse(response.getProductInfo());
                                Log.d("productDetail","info-->"+productInfo.toString());
                                setControlValue();
                            }else Log.d("productDetail","err-->"+response.getErrorDescription());

                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
