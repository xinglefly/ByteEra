package com.zhicai.byteera.activity.initialize;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.MainActivity;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.LoadingDialogShow;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.register.Register;
import com.zhicai.byteera.service.register.Register.LoginRequest;
import com.zhicai.byteera.service.register.Register.LoginResponse;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.et_verifynum) EditText etVerifyNum;
    @Bind(R.id.et_phone) EditText etPhone;
    @Bind(R.id.et_password) EditText etPassword;
    @Bind(R.id.tv_proto) TextView tvProto;
    @Bind(R.id.tv_sendVerify) TextView tvSendVerify;
    @Bind(R.id.tv_registerrr) TextView tvRegister;
    @Bind(R.id.et_recommend) EditText etRecommend;    //推荐码

    private MyTimeCount myTimeCount;
    private LoadingDialogShow dialog;

    //用户注册成功后并没有返回用户的信息，需要再次请求登录接口来获取用户的信息
    private void saveUserInfo() {
        LoginRequest login = LoginRequest.newBuilder().setMobilePhone(etPhone.getText().toString()).
                setPassword(etPassword.getText().toString()).build();
        TiangongClient.instance().send("chronos", "login", login.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    LoginResponse response = LoginResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        getSex(response.getSex().getNumber());
                        //保存用户信息
                        ZCUser user = new ZCUser(response);
                        PreferenceUtils.getInstance(baseContext).setUserInfo(user);
                        PreferenceUtils.getInstance(baseContext).setUserName(response.getNickname().equals("") ? response.getMobilePhone() : response.getNickname());
                        PreferenceUtils.getInstance(baseContext).setUserId(response.getUserId());
                        PreferenceUtils.getInstance(baseContext).setMobilePhone(response.getMobilePhone());
                        PreferenceUtils.getInstance(baseContext).setPwd(etPassword.getText().toString());
                        ActivityUtil.startActivity(baseContext, new Intent(baseContext, MainActivity.class));
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getSex(int number) {
        if (number == 1) {
            return "男";
        } else {
            return "女";
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        tvProto.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        initTvRegister();
    }

    private void initTvRegister() {
        tvRegister.setEnabled(false);
        tvRegister.setBackgroundResource(R.drawable.register_selector2);
        tvRegister.setTextColor(Color.parseColor("#33000000"));
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
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功,关闭倒计时
//                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
//                            @Override
//                            public void run() {
//                                myTimeCount.cancel();
//                                etVerifyNum.setHint("验证码");
//                            }
//                        });
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
        myTimeCount = new MyTimeCount(30000, 1000);
    }

    private void getData() {
        Register.RegisterReq user = Register.RegisterReq.newBuilder().setMobilePhone(etPhone.getText().toString().trim())   //手机号码
                .setPassword(etPassword.getText().toString().trim())                    //密码
                .setMobileValidateCode(etVerifyNum.getText().toString().trim())         //验证码
                .setInvitationCode(etRecommend.getText().toString().trim()).build();    //邀请码
        byte[] request = user.toByteArray();
        TiangongClient.instance().send("chronos", "register", request, new BaseHandlerClass() {
            @Override public void onSuccess(byte[] buffer) {
                try {
                    if (dialog != null) {
                        runOnUiThread(new Runnable() {
                            @Override public void run() {
                                dialog.dismiss();
                            }
                        });
                    }
                    Common.CommonResponse commonResponse = Common.CommonResponse.parseFrom(buffer);

                    int errocode = commonResponse.getErrorno();
                    if (errocode == 0) {
                        ToastUtil.showToastText("注册成功");
                        saveUserInfo();
                    } else if (errocode == 1) {
                        ToastUtil.showToastText("注册失败");
                    } else {
                        ToastUtil.showToastText("用户已存在");
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnTextChanged(value=R.id.et_password,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onAfterPasswordTextChanged(){
        checkInput();
    }

    @OnTextChanged(value=R.id.et_phone,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onAfterPhoneTextChanged(){
        checkInput();
    }

    @OnTextChanged(value=R.id.et_verifynum,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onAfterVerifynumTextChanged(){
        checkInput();
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }

    private void checkInput() {
        String phoneNum = etPhone.getText().toString();
        String verifyNum = etVerifyNum.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(verifyNum) || TextUtils.isEmpty(password)) {
            initTvRegister();
        } else {
            tvRegister.setEnabled(true);
            tvRegister.setBackgroundResource(R.drawable.register_selector);
            tvRegister.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @OnClick({R.id.tv_registerrr, R.id.tv_proto, R.id.tv_sendVerify})
    public void onclickView(View view) {
        switch (view.getId()) {
            case R.id.tv_registerrr:
                checkVerify();
                break;
            case R.id.tv_proto:
                //跳转到查看用户协议界面
                ActivityUtil.startActivity(this, new Intent(this, UserProtocolActivity.class));
                break;
            case R.id.tv_sendVerify:
                sendVerify();
                break;
            default:
                break;
        }

    }

    private void sendVerify() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showToastText("请填写您的手机号码！");
        } else if (phone.length() != 11) {
            ToastUtil.showToastText("您填写的手机号码格式不对，请重新输入！");
        } else {
            GetVerifyTask getVerifyTask = new GetVerifyTask();
            getVerifyTask.execute();
        }
    }

    private class GetVerifyTask extends AsyncTask<Void, Void, Object> {
        private String phonenum;

        @Override protected void onPreExecute() {
            super.onPreExecute();
            myTimeCount.start();
            tvSendVerify.setEnabled(false);
            etVerifyNum.setText("");
            phonenum = etPhone.getText().toString().trim();
        }

        @Override
        protected Object doInBackground(Void... params) {
            ToastUtil.showToastText("短信验证码，已发送");
            SMSSDK.getVerificationCode("86", phonenum, new OnSendMessageHandler() {
                @Override public boolean onSendMessage(String s, String s1) {
                    return false;
                }
            });
            return null;
        }
    }

    private class MyTimeCount extends CountDownTimer {
        public MyTimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvSendVerify.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            tvSendVerify.setText("获取验证码");
            tvSendVerify.setEnabled(true);
        }
    }

    /** 提交验证码成功后的执行事件 */
    private void afterSubmit(final int result, final Object phone) {
        MyApp.getMainThreadHandler().post(new Runnable() {
            public void run() {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    getData();
                } else {
                    ((Throwable) phone).printStackTrace();
                    // 验证码不正确
                    ToastUtil.showToastText("验证码不正确，请重新输入！");
                }
            }
        });
    }


    private void checkVerify() {
        String phoneNum = etPhone.getText().toString();
        String verifyNum = etVerifyNum.getText().toString();
        String password = etPassword.getText().toString();
        if (checkRegisterError(phoneNum, password, verifyNum)) return;
        register();
    }

    private void register() {
        dialog = new LoadingDialogShow(baseContext);
        dialog.setMessage("注册中....");
        dialog.show();
        ToastUtil.showToastText(etVerifyNum.getText().toString().trim());
        SMSSDK.submitVerificationCode("86", etPhone.getText().toString().trim(), etVerifyNum.getText().toString().trim());
    }

    private boolean checkRegisterError(String phone, String pwd, String verifyNum) {
        return ToastUtil.showNetConnectionError(baseContext) || ToastUtil.showPhoneError(phone) || ToastUtil.showPwdError(pwd) || ToastUtil.showVerfyNumError(verifyNum);
    }

    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        UIUtils.hideKeyboard(baseContext);
        return super.dispatchTouchEvent(ev);
    }

    /** 短信认证的代码，先保留************ ********** ********** ********** ********** ********** ********** ********** ********** ********** ********** */
//private static final String APPKEY = "53e90561f188"; // 从短信SDK应用后台注册得到的APPKEY
//private static final String APPSECRET = "9a7ba95df7a728feab61c54f62f598bf";// 从短信SDK应用后台注册得到的APPSECRET

}

