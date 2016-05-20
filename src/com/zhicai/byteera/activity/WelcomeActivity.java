package com.zhicai.byteera.activity;

import android.content.Intent;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.initialize.GuideActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;

public class WelcomeActivity extends BaseActivity {

    @Override protected void loadViewLayout() {
        setContentView(R.layout.activity_welcome);
    }

    @Override protected void initData() {
    }


    @Override protected void updateUI() {
    }

    @Override protected void processLogic() {
        MyApp.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PreferenceUtils.getInstance(MyApp.getInstance()).isStartGuide()) {
                    goHome();
                } else {
                    goGuide();
                }
            }
        },1550);
    }

    private void goGuide() {
        ActivityUtil.startActivity(baseContext, new Intent(baseContext, GuideActivity.class));
        ActivityUtil.finishActivity(this);
    }

    private void goHome() {
        ActivityUtil.startActivity(this, new Intent(this, MainActivity.class));
        ActivityUtil.finishActivity(this);
    }
}
