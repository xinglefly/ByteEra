package com.zhicai.byteera.commonutil;

import android.content.Context;
import android.view.View;

import me.drakeet.materialdialog.MaterialDialog;

/** Created by bing on 2015/4/14. */
public class DialogUtil {
    private static boolean isShow;
    public interface DialogPositiveButtonClickListener {
        void positiveClick();
    }

    public static void showDialog(Context context, int title, int message, int positiveBtn, final DialogPositiveButtonClickListener listener) {
        if (!isShow) {
            final MaterialDialog materialDialog;
            try {
                materialDialog = new MaterialDialog(context);
                if(title != -1){
                    materialDialog.setTitle(title);
                }

                materialDialog.setMessage(message);
                materialDialog.setPositiveButton(positiveBtn, new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        materialDialog.dismiss();
                        isShow = false;
                        if(listener != null){
                            listener.positiveClick();
                        }

                    }
                });
                materialDialog.show();
                isShow = true;
            } catch (Exception e) {
                SDLog.e("DialogError:", "---------color userRemovedBuilder error" + e.getMessage());
            }
        }
    }
}
