package com.zhicai.byteera.activity.initialize;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.MainActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/** Created by bing on 2015/4/28. */
public class FindPwdActivity2 extends BaseActivity {
    @Bind(R.id.et_pwd) EditText etPwd;
    @Bind(R.id.et_pwd2) EditText etPwd2;
    @Bind(R.id.title_bar) HeadViewMain mTitleBar;
    private String phone;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_find_pwd2);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        phone = getIntent().getStringExtra("phone");
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

    @OnClick({R.id.tv_ok})
    public void onclickView(View view) {
        switch (view.getId()) {
            case R.id.tv_ok:
                checkPwd();
                break;
        }
    }

    private void checkPwd() {
        if (TextUtils.isEmpty(etPwd.getText().toString())) {
            ToastUtil.showToastText("请输入密码");
        } else if (TextUtils.isEmpty(etPwd2.getText().toString())) {
            ToastUtil.showToastText("请再次输入密码");
        } else if (!etPwd.getText().toString().equals(etPwd2.getText().toString())) {
            ToastUtil.showToastText("两次输入的密码不匹配,请重新输入");
        } else {
            changePwd();
        }
    }

    private void changePwd() {
        UserAttribute.SetPasswordReq req = UserAttribute.SetPasswordReq.newBuilder().setNewPass(etPwd.getText().toString().trim())
                .setMobilePhone(phone).build();
        TiangongClient.instance().send("chronos", "set_password", req.toByteArray(), new BaseHandlerClass() {
            @Override public void onSuccess(byte[] buffer) {
                try {
                    UserAttribute.SetPasswordResponse response = UserAttribute.SetPasswordResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        ToastUtil.showToastText( "重设密码成功!");
                        Intent intent = new Intent(baseContext, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        ActivityUtil.startActivity(baseContext, intent);
                    } else {
                        ToastUtil.showToastText(response.getErrorDescription());  //打印错误信息
                    }

                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
