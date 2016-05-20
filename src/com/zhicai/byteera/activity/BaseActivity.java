package com.zhicai.byteera.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.LoadingDialogShow;
import com.zhicai.byteera.commonutil.PreferenceUtils;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

@SuppressWarnings("unused")
public abstract class BaseActivity extends BaseFragmentActivity {
    protected BaseActivity baseContext = BaseActivity.this;
    protected LoadingDialogShow dialog = null;
    protected DbUtils db;
    protected String userId = "";

    /** *********************环信************************* */
    protected NotificationManager notificationManager;
    public ZCUser zcUser;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        db = DbUtils.create(baseContext, Constants.BYTEERA_DB);
        dialog = new LoadingDialogShow(baseContext);
        zcUser = PreferenceUtils.getInstance(baseContext).readUserInfo();
        userId = zcUser.getUser_id();
        initView();

    }

    /** 初始化组件 */
    private void initView() {
        loadViewLayout();
        initData();
        updateUI();
        processLogic();
    }

    protected abstract void loadViewLayout();

    protected abstract void initData();

    protected abstract void updateUI();

    protected abstract void processLogic();


    public boolean isStartLoginActivity() {
        zcUser = PreferenceUtils.getInstance(this).readUserInfo();
        userId = zcUser.getUser_id();
        if (TextUtils.isEmpty(userId)) {
            ActivityUtil.startActivity(baseContext, new Intent(baseContext, LoginActivity.class));
            return true;
        } else {
            return false;
        }
    }

    public final boolean DetermineIsLogin(){
        if (TextUtils.isEmpty(MyApp.getInstance().getUserId())) {
            ActivityUtil.startActivity(baseContext, new Intent(baseContext, LoginActivity.class));
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

}
