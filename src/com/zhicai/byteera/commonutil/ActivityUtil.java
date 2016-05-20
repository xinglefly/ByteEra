package com.zhicai.byteera.commonutil;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.initialize.LoginActivity;

import me.drakeet.materialdialog.MaterialDialog;

@SuppressWarnings("unused")
public final class ActivityUtil {
    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
    }

    public static void finishActivity(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
    }

    public static void startActivityForResult(Activity activity, Intent intent, int flag) {
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivityForResult(intent, flag);
        activity.overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
    }

    public static void startActivityForResult(Activity activity, Fragment fragment, Intent intent, int flag) {
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        fragment.startActivityForResult(intent, flag);
        activity.overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
    }

    public static void startToLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        startActivity(activity, intent);
    }

    public static void startLoginActivityForResult(Activity activity, int flag) {
        Intent intent = new Intent(activity, LoginActivity.class);
        startActivityForResult(activity, intent, flag);
    }

    public static void showLoginDialog(final Activity mContext, final int requestFlag) {
        final MaterialDialog materialDialog = new MaterialDialog(mContext);
        materialDialog.setTitle("登录").setMessage("您还没有登录，是否登录?").setPositiveButton("确定", new View.OnClickListener() {
            @Override public void onClick(View v) {
                startLoginActivityForResult(mContext, requestFlag);
                materialDialog.dismiss();
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override public void onClick(View v) {
                materialDialog.dismiss();
            }
        }).show();
    }

    public static void showLoginDialog(final Activity mContext) {
        final MaterialDialog materialDialog = new MaterialDialog(mContext);
        materialDialog.setTitle("登录").setMessage("您还没有登录，是否登录?").setPositiveButton("确定", new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(mContext,new Intent(mContext,LoginActivity.class));
                materialDialog.dismiss();
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override public void onClick(View v) {
                materialDialog.dismiss();
            }
        }).show();
    }

    public static void finishActivity2(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(0, R.anim.fade_out);
    }


}
