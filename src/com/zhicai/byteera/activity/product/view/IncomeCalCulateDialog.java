package com.zhicai.byteera.activity.product.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.product.entity.ProductInfo;
import com.zhicai.byteera.commonutil.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

@SuppressWarnings("unused")
public class IncomeCalCulateDialog extends AlertDialog {

    @Bind(R.id.et_money_val) EditText etMoneyVal;
    @Bind(R.id.tv_limit_val) TextView tvLimitVal;
    @Bind(R.id.tv_expect_val) TextView tvExpectVal;
    @Bind(R.id.tv_current_val) TextView tvCurrentVal;
    @Bind(R.id.tv_yuebao_val) TextView tvYueBao;
    @Bind(R.id.tv_deferral_val) TextView tvDeferralVal;
    private ProductInfo productInfo;

    public IncomeCalCulateDialog(Context context,ProductInfo productInfo) {
        super(context);
        this.productInfo = productInfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_calculate_dialog);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        tvLimitVal.setText(productInfo.getLimit() + "天");
    }


    @OnTextChanged(value = R.id.et_money_val,callback=OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChange(){
        String etMoney = etMoneyVal.getText().toString().equals("") ? "0" : etMoneyVal.getText().toString();
        Long money = Long.decode(etMoney);
        if (etMoney.length()<15) {
            tvExpectVal.setText(String.format("%.02f", (money * productInfo.getIncome() * productInfo.getLimit() / 360))+"元");
            tvCurrentVal.setText(String.format("%.02f", (money * productInfo.getCurrent_deposit() * productInfo.getLimit() / 360))+"元");
            tvYueBao.setText(String.format("%.02f", (money * productInfo.getYuebao() * productInfo.getLimit() / 360))+"元");
            tvDeferralVal.setText(String.format("%.02f", (money * productInfo.getFixed_deposit() * productInfo.getLimit() / 360))+"元");
        } else{
            ToastUtil.showLongToastText("请输入15位以内数");
            etMoneyVal.setText(10000+"");
        }
    }


    @OnClick(R.id.img_back)
    void onClick(){dismiss();}

}
