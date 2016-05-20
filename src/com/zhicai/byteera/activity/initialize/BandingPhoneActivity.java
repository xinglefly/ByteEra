package com.zhicai.byteera.activity.initialize;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.ButterKnife;
import butterknife.Bind;

/** Created by lieeber on 15/7/4. */
public class BandingPhoneActivity extends BaseActivity{
    @Bind(R.id.head_view) HeadViewMain mHeadView;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.banding_phone_activity);
        ButterKnife.bind(this);
    }

    @Override protected void initData() {

    }

    @Override protected void updateUI() {

    }

    @Override protected void processLogic() {
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }
}
