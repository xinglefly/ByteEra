package com.zhicai.byteera.activity.myhome.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.UIUtils;


/** Created by bing on 2015/5/7. */
public class ShareInnerDialog extends AlertDialog {
    private Context mContext;
    private EditText etComment;
    private TextView tvShare;
    private ImageView ivQuit;


    public interface OnOkClickListener {
        void onOk(String text);
    }

    private OnOkClickListener okClickListener;

    public void setOkClickListener(OnOkClickListener listener) {
        this.okClickListener = listener;
    }

    public ShareInnerDialog(Context context) {
        this(context, 0);
    }

    public ShareInnerDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_dialog);
        WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
        attributes.width = UIUtils.dip2px(mContext, 300);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getWindow().setBackgroundDrawableResource(R.drawable.shape_bg);
        this.getWindow().setAttributes(attributes);
        etComment = (EditText) findViewById(R.id.et_comment);
        tvShare = (TextView) findViewById(R.id.tv_share);
        ivQuit = (ImageView) findViewById(R.id.iv_quit);
        ivQuit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dismiss();
            }
        });
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (okClickListener != null) {
                    okClickListener.onOk(etComment.getText().toString());
                }
            }
        });
    }
}
