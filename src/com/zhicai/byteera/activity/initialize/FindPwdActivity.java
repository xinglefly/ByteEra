package com.zhicai.byteera.activity.initialize;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/** Created by bing on 2015/4/28. */
public class FindPwdActivity extends BaseActivity {
    @Bind(R.id.et_username) EditText etUserName;
    @Bind(R.id.et_verifynum) EditText etVerifyNum;
    @Bind(R.id.get_verify) TextView getVerify;
    @Bind(R.id.title_bar) HeadViewMain mTitleBar;
    private MyTimeCount myTimeCount;
    private String phone;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);
    }

    private class MyTimeCount extends CountDownTimer {
        public MyTimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String str = String.format("%d秒", millisUntilFinished / 1000);
            getVerify.setText(str);
        }

        @Override
        public void onFinish() {
            getVerify.setText("获取验证码");
            getVerify.setEnabled(true);
        }
    }

    @Override
    protected void initData() {
        EventHandler eh = new EventHandler() {
            @Override public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        afterSubmit(result, data);
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
        myTimeCount = new MyTimeCount(60000, 1000);
    }

    /** 提交验证码成功后的执行事件 */
    private void afterSubmit(final int result, final Object phone) {
        MyApp.getMainThreadHandler().post(new Runnable() {
            public void run() {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    Intent intent = new Intent(baseContext, FindPwdActivity2.class);
                    intent.putExtra("phone", FindPwdActivity.this.phone);
                    ActivityUtil.startActivity(baseContext, intent);
                } else {
                    ((Throwable) phone).printStackTrace();
                    // 验证码不正确
                   ToastUtil.showToastText("验证码不正确，请重新输入！");
                }
            }
        });
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        mTitleBar.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }

    @OnClick({R.id.tv_next, R.id.get_verify})
    public void onclickView(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                SMSSDK.submitVerificationCode("86", etUserName.getText().toString().trim(), etVerifyNum.getText().toString().trim());
                break;
            case R.id.get_verify:   //获取验证码
                phone = etUserName.getText().toString().trim();
                GetVerifyTask getVerifyTask = new GetVerifyTask();
                getVerifyTask.execute();
                break;
        }
    }

    private class GetVerifyTask extends AsyncTask<Void, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myTimeCount.start();
            getVerify.setEnabled(false);
            etVerifyNum.setText("");
        }

        @Override
        protected Object doInBackground(Void... params) {
           ToastUtil.showToastText("短信验证码，已发送");
            SMSSDK.getVerificationCode("86", phone, new OnSendMessageHandler() {

                @Override public boolean onSendMessage(String s, String s1) {
                    return false;
                }
            });
            return null;
        }
    }
}
