package com.zhicai.byteera.activity.community.topic.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.UIUtils;

/** Created by bing on 2015/5/7. */
public class ExitEditDialog extends AlertDialog {
    private Context mContext;
    public ExitEditDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ExitEditDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }
    public interface OnExitClickListener{
        void exitClick();
    }
    private OnExitClickListener onExitClickListener;
    public void setOnExitClickListener(OnExitClickListener listener) {
        this.onExitClickListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_edit_dialog);
        WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
        attributes.width = UIUtils.dip2px(mContext, 300);
        this.getWindow().setAttributes(attributes);
        findViewById(R.id.tv_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.tv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(onExitClickListener !=null){
                   onExitClickListener.exitClick();
               }
            }
        });
    }
}
