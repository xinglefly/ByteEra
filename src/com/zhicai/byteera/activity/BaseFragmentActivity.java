package com.zhicai.byteera.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;
import com.zhicai.byteera.commonutil.ActivityController;
import com.zhicai.byteera.commonutil.ActivityUtil;

public class BaseFragmentActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityController.add(this);
    }
    

    @Override public void onBackPressed() {
        super.onBackPressed();
        ActivityUtil.finishActivity(this);
    }

    @Override protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        ActivityController.remove(this);
    }
}
