package com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.view.ShoppingButtonView;
import com.zhicai.byteera.activity.event.DayTaskEvent;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

@SuppressWarnings("unused")
public class CoinStoreActivity extends BaseActivity {

    @Bind(R.id.button_view) ShoppingButtonView buttonView;
    @Bind(R.id.head_title) HeadViewMain mTitle;
    @Bind(R.id.tv_coin) TextView tv_coin;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_coinstore);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }


    @Override
    protected void initData() {
        setSelect(0);
        buttonView.setButtonViewCheckedListener(new ShoppingButtonView.ButtonViewCheckedListener() {

            @Override
            public void checkedXuNi() {
                setSelect(0);
            }

            @Override
            public void checkedShiWu() {
                setSelect(1);
            }
        });
    }

    @Override
    protected void updateUI() {
        tv_coin.setText(MyApp.getInstance().getCoinCnt() + "");
    }

    @Override
    protected void processLogic() {
        mTitle.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
        mTitle.setRightTextClickListener(new HeadViewMain.RightTextClickListener() {
            @Override
            public void onRightTextClick() {
                ActivityUtil.startActivity(baseContext, new Intent(baseContext, ExchangeRecordList.class));
            }
        });
    }


    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        ShoppingFragment fragment = null;
        Bundle bundle = new Bundle();
        switch (i) {  // 兑换商品类型 1:实物 2:虚拟
            case 0:
                fragment = new ShoppingFragment();
                bundle.putInt("product_type",2);
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new ShoppingFragment();
                bundle.putInt("product_type",1);
                fragment.setArguments(bundle);
                break;
        }
        transaction.replace(R.id.rl_container, fragment);
        transaction.commit();
    }


    void refreshTextViewVal(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_coin.setText(MyApp.getInstance().getCoinCnt() + "");
            }
        });
        Constants.isUpdateShopping = true;
    }

    public void onEventMainThread(DayTaskEvent event) {
        Log.d("CoinStoreActivity", "接收onEventMainThread信息 DayTaskEvent-->" + event.isRefresh());
        refreshTextViewVal();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
