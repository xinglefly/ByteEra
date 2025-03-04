package com.zhicai.byteera.activity.myhome.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.UIUtils;

/**
 * Created by bing on 2015/5/7.
 */
public class ChangeNickNameDialog extends AlertDialog {
    private Context mContext;
    private EditText etNickName;

    public ChangeNickNameDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ChangeNickNameDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public interface OnOkClickListener {
        void onOk();
    }

    private OnOkClickListener onOkClickListener;

    public void setOnOkClickListener(OnOkClickListener listener) {
        this.onOkClickListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_nickname_dialog);
        WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
        attributes.width = UIUtils.dip2px(mContext, 300);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        this.getWindow().setAttributes(attributes);

        etNickName = (EditText) findViewById(R.id.et_nickname);

        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
        findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOkClickListener.onOk();
                dismiss();
            }
        });
    }

    public String getNickName() {
        return etNickName.getText().toString();
    }

    public void setNickName(String name) {
        if(etNickName!=null){
            etNickName.setText(name);
        }
    }
}
