package com.zhicai.byteera.activity.initialize;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.MainActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/** Created by bing on 2015/5/6. */
public class SettingActivity extends BaseActivity {
//    @Bind(R.id.sw_remind) SwitchView swRemind;//接收消息并提醒
//    @Bind(R.id.sw_vibrate) SwitchView swVivbrate;//震动
//    @Bind(R.id.sw_voice) SwitchView swVoice;//声音
//    @Bind(R.id.sw_show) SwitchView swShow;//提醒时显示消息内容
//    @Bind(R.id.sw_tuisong) SwitchView swTuisong;//推送时提醒
    @Bind(R.id.tv_login_out) TextView tvLoginOut;
    @Bind(R.id.head_title) HeadViewMain headView;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.setting_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
//        swRemind.setState(true);
//        swVivbrate.setState(false);
//        swVoice.setState(true);
//        swShow.setState(false);
//        swTuisong.setState(true);
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        tvLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                PreferenceUtils.getInstance(baseContext).clearUserInfo();
                Intent intent = new Intent(baseContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ActivityUtil.startActivity(baseContext, intent);
                ToastUtil.showToastText("退出成功");
            }
        });
        headView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }


    @OnClick(R.id.rl_about)
    void onClick(){
        ActivityUtil.startActivity(baseContext,new Intent(baseContext,AboutUSActivity.class));
    }
}
