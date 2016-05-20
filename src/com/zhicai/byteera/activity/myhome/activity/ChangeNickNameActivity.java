package com.zhicai.byteera.activity.myhome.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.ButterKnife;
import butterknife.Bind;

/** Created by lieeber on 15/8/7. */
public class ChangeNickNameActivity extends BaseActivity {
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.et_nickname) EditText etNickName;
    @Bind(R.id.iv_clear) ImageView iv_clear;
    private String oldName;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.change_nickname_activity);
        ButterKnife.bind(this);
    }

    @Override protected void initData() {
        oldName = getIntent().getStringExtra("name");
        etNickName.setText(oldName);
    }

    @Override protected void updateUI() {

    }

    @Override protected void processLogic() {
        mHeadView.setRightTextClickListener(new HeadViewMain.RightTextClickListener() {
            @Override public void onRightTextClick() {
                String newName = etNickName.getText().toString().trim();
                if (TextUtils.isEmpty(newName)) {
                    ToastUtil.showToastText("昵称不能为空");

                } else if (newName.equals(oldName)) {
                    ToastUtil.showToastText("您没有做任何修改");
                } else {
                    UIUtils.hideKeyboard(baseContext);
                    Intent intent = new Intent();
                    intent.putExtra("name", newName);
                    setResult(11, intent);
                    ActivityUtil.finishActivity(baseContext);
                }
            }
        });
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });

        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                etNickName.setText("");
            }
        });
    }
}
