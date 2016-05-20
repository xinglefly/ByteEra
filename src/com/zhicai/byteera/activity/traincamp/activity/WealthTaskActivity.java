package com.zhicai.byteera.activity.traincamp.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord.CoinStoreActivity;
import com.zhicai.byteera.activity.event.DayTaskEvent;
import com.zhicai.byteera.activity.traincamp.AchievementPraiseFragment;
import com.zhicai.byteera.activity.traincamp.view.ButtonView;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

@SuppressWarnings("unused")
public class WealthTaskActivity extends BaseActivity {

    @Bind(R.id.button_view) ButtonView buttonView;
    @Bind(R.id.head_title) HeadViewMain mTitle;
    @Bind(R.id.tv_coincount) TextView tv_coincount;
    @Bind(R.id.tv_ranking) TextView tv_ranking;
    @Bind(R.id.tv_logindays) TextView tv_logindays;

//    private DayTaskFragment mTab01;
    private AchievementPraiseFragment mTab02;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_wealth_task);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }


    @Override
    protected void initData() {
        setSelect(0);
    }

    @Override
    protected void updateUI() {
        tv_coincount.setText(MyApp.getInstance().getCoinCnt()+"");
        tv_ranking.setText(PreferenceUtils.getInstance(baseContext).getCoinRank()+"");
        tv_logindays.setText(PreferenceUtils.getInstance(baseContext).getLoginDays() + "");
    }

    @Override
    protected void processLogic() {
        buttonView.setButtonViewCheckedListener(new ButtonView.ButtonViewCheckedListener() {
            @Override
            public void checkedDayTask() {
                setSelect(0);
            }

            @Override
            public void checkedAchievementPrise() {
                setSelect(1);
            }
        });

        mTitle.setRightTextClickListener(new HeadViewMain.RightTextClickListener() {
            @Override
            public void onRightTextClick() {
                ActivityUtil.startActivity(baseContext, new Intent(baseContext, CoinStoreActivity.class));
            }
        });

        mTitle.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                WealthTaskActivity.this.finish();
            }
        });
    }


    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (i) {
            case 0:
//                if (mTab01 == null) {
////                    mTab01 = new DayTaskFragment();
//                }
//                transaction.replace(R.id.rl_container, mTab01);
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new AchievementPraiseFragment();
                }
                transaction.replace(R.id.rl_container, mTab02);
                break;
        }
        transaction.commit();
    }


    public void onEventMainThread(DayTaskEvent event) {
        Log.d("WealthTaskActivity", "接收onEventMainThread信息 DayTaskEvent-->" + event.isRefresh());
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



}
