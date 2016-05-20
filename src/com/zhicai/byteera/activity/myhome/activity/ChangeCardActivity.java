package com.zhicai.byteera.activity.myhome.activity;

import android.content.Intent;
import android.widget.EditText;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.ButterKnife;
import butterknife.Bind;


/**
 * Created by bing on 2015/5/9.
 */
public class ChangeCardActivity extends BaseActivity {
    @Bind(R.id.et_card) EditText etCard;
    @Bind(R.id.head_view) HeadViewMain mHeadView;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.change_card_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        String card = getIntent().getStringExtra("card");
        etCard.setText(card);
    }
    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        mHeadView.setRightTextClickListener(new HeadViewMain.RightTextClickListener() {
            @Override
            public void onRightTextClick() {
                ToastUtil.showToastText("修改成功");
                Intent intent = new Intent();
                intent.putExtra("card", etCard.getText().toString());
                setResult(RESULT_OK, intent);
                ActivityUtil.finishActivity(baseContext);
            }
        });
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }
}
