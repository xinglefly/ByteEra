package com.zhicai.byteera.activity;

import android.content.Intent;
import android.view.View;
import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lieeber on 15/7/31.
 */
public class SelectAddressActivity extends BaseActivity {
    @Override protected void loadViewLayout() {
        setContentView(R.layout.select_addresss_activity);
    }

    @Override protected void initData() {

    }

    @Override protected void updateUI() {

    }

    @Override protected void processLogic() {

    }

    public void product(View view) {
        TiangongClient.instance().init("gate-prd.zijieshidai.com", 12002);
        PreferenceUtils.getInstance(this).saveAddress("gate-prd.zijieshidai.com");
        ActivityUtil.startActivity(baseContext, new Intent(baseContext, WelcomeActivity.class));
        finish();
    }

    public void test(View view) {
        TiangongClient.instance().init("dev.zijieshidai.com", 9998);
        PreferenceUtils.getInstance(this).saveAddress("dev.zijieshidai.com");
        ActivityUtil.startActivity(baseContext, new Intent(baseContext, WelcomeActivity.class));
        finish();
    }


}
