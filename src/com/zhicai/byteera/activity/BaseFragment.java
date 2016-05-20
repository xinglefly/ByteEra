package com.zhicai.byteera.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.lidroid.xutils.DbUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.LoadingDialogShow;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.UIUtils;

/** Created by bing on 2015/5/23. */
@SuppressWarnings("unused")
public abstract class BaseFragment extends Fragment {
    public boolean isConnectionNet;
    protected String userId;
    protected DbUtils db;
    protected LoadingDialogShow dialog;
    public ZCUser zcUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnectionNet = UIUtils.checkNetworkState();
        MobclickAgent.openActivityDurationTrack(false);
        db = DbUtils.create(getActivity(), Constants.BYTEERA_DB);
        dialog = new LoadingDialogShow(getActivity());
    }

    public final boolean DetermineIsLogin(){
        if (TextUtils.isEmpty(MyApp.getInstance().getUserId())) {
            ActivityUtil.startActivity(getActivity(), new Intent(getActivity(), LoginActivity.class));
            return true;
        }
        return false;
    }

    @Override public void onResume() {
        super.onResume();
        userId = PreferenceUtils.getInstance(getActivity()).getUserId();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}
