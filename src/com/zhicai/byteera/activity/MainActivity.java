package com.zhicai.byteera.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.ActivityController;
import com.zhicai.byteera.widget.TabItemView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


@SuppressWarnings("unused")
public class MainActivity extends BaseFragmentActivity {

    @Bind(R.id.tab_income) TabItemView tabIncome;
    @Bind(R.id.tab_product) TabItemView tabProduct;
    @Bind(R.id.tab_find) TabItemView tabFind;
    @Bind(R.id.tab_mine) TabItemView tabMine;
    @Bind(R.id.ll_buttom) LinearLayout mllButton;
    @Bind(R.id.unread_msg_number) TextView unreadLabel;

    private MainFragmentFactory mainFragmentFactory;
    private int backClickTime;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainFragmentFactory = new MainFragmentFactory();
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_main_tabhost);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        setSelect(MainFragmentFactory.TAB_INCOME);
    }


    @Override protected void onResume() {
        super.onResume();
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    private void setSelect(int i) {
        mainFragmentFactory.createFragment(this, i);
        resetButton();
        switch (i) {
            case MainFragmentFactory.TAB_INCOME:
                tabIncome.select();
                break;
            case MainFragmentFactory.TAB_PRODUCT:
                tabProduct.select();
                break;
            case MainFragmentFactory.TAB_FIND:
                tabFind.select();
                break;
            case MainFragmentFactory.TAB_MINE:
                tabMine.select();
                break;
        }
    }

    private void resetButton() {
        tabIncome.unSelect();
        tabProduct.unSelect();
        tabFind.unSelect();
        tabMine.unSelect();
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        mainFragmentFactory.destroyFragment();
    }


    @OnClick({R.id.tab_income,R.id.tab_product,R.id.tab_find,R.id.tab_mine})
     public void clickListener(View v) {
        switch (v.getId()) {
            case R.id.tab_income:
                setSelect(MainFragmentFactory.TAB_INCOME);
                break;
            case R.id.tab_product:
                setSelect(MainFragmentFactory.TAB_PRODUCT);
                break;
            case R.id.tab_find:
                setSelect(MainFragmentFactory.TAB_FIND);
                break;
            case R.id.tab_mine:
                setSelect(MainFragmentFactory.TAB_MINE);
                break;
        }
    }


    @Override public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        mainFragmentFactory.attachFragment(fragment);
    }

    /** 处理返回键：只有在3秒内按返回2次，才退出应用 */
    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                if (backClickTime == 1) {
                    ActivityController.exitApp();
                    backClickTime = 0;
                } else {
                    Toast.makeText(this, R.string.back_confirm, Toast.LENGTH_SHORT).show();
                    backClickTime++;
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            backClickTime = 0;
                        }
                    }, 3000);
                }
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
