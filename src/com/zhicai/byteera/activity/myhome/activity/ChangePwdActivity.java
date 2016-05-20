package com.zhicai.byteera.activity.myhome.activity;

import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.DialogUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by bing on 2015/5/8.
 */
public class ChangePwdActivity extends BaseActivity {

    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.et_old_pwd) EditText etOldPwd;
    @Bind(R.id.et_new_pwd) EditText etNewPwd;
    @Bind(R.id.tb_old_pwd) ToggleButton tbOldPwd;
    @Bind(R.id.tb_new_pwd) ToggleButton tbNewPwd;

    private Handler mHandler = new Handler();

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.change_pws_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        mHeadView.setRightTextClickListener(new HeadViewMain.RightTextClickListener() {
            @Override public void onRightTextClick() {
                if (TextUtils.isEmpty(etOldPwd.getText().toString()) || etOldPwd.getText().toString().length() < 6) {
                    DialogUtil.showDialog(baseContext, -1, R.string.mobile_input_original_need, R.string.ok,null);

                } else if (TextUtils.isEmpty(etNewPwd.getText().toString()) || etNewPwd.getText().toString().length() < 6) {
                    DialogUtil.showDialog(baseContext, -1, R.string.mobile_input_new_need, R.string.ok, null);
                } else {
                    changPwd();
                }
            }
        });
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
        tbOldPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //显示密码
                    etOldPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //隐藏密码
                    etOldPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        tbNewPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //显示密码
                    etNewPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //隐藏密码
                    etNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    /**
     * 修改密码
     */
    private void changPwd() {
        String userId = PreferenceUtils.getInstance(this).readUserInfo().getUser_id();
        UserAttribute.ChangePasswordReq req = UserAttribute.ChangePasswordReq.newBuilder().setUserId(userId).setOldPass(etOldPwd.getText().toString()).setNewPass(etNewPwd.getText().toString()).build();
        TiangongClient.instance().send("chronos", "change_password", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    UserAttribute.ChangePasswordResponse page = UserAttribute.ChangePasswordResponse.parseFrom(buffer);
                    if (page.getErrorno() == 0) {
                        mHandler.postAtFrontOfQueue(new Runnable() {
                            public void run() {
                                ActivityUtil.finishActivity(baseContext);
                                ToastUtil.showToastText( "修改成功");
                            }
                        });
                    } else {
                        mHandler.postAtFrontOfQueue(new Runnable() {
                            public void run() {
                                ToastUtil.showToastText( getResources().getString(R.string.mobile_input_original_error));
                            }
                        });
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
