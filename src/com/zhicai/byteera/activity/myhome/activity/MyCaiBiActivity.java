package com.zhicai.byteera.activity.myhome.activity;


import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.HeadViewMain.LeftImgClickListner;
import com.zhicai.byteera.widget.RoundProgressBar;

import butterknife.ButterKnife;
import butterknife.Bind;


/**
 * Created by bing on 2015/5/15.
 */
public class MyCaiBiActivity extends BaseActivity {

    private int progress = 0;
    @Bind(R.id.rb_income) RoundProgressBar rb_income;
    @Bind(R.id.rb_outcome) RoundProgressBar rb_outcome;
    @Bind(R.id.head_view) HeadViewMain mHeadView;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.my_caibi_activity);
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
        mHeadView.setLeftImgClickListener(new LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }
}
