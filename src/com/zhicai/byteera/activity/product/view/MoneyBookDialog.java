package com.zhicai.byteera.activity.product.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.zhicai.byteera.R;

/**
 * Created by bing on 2015/4/30.
 */
public class MoneyBookDialog extends AlertDialog implements View.OnClickListener {
    private Context mContext;

    protected MoneyBookDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    protected MoneyBookDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_access_dialog);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_ok).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
               // ToastUtil.showToastText("取消");
                this.dismiss();
                break;
            case R.id.tv_ok:
                //ToastUtil.showToastText("确定");
                this.dismiss();
                break;
        }
    }
}
