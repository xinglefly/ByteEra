package com.zhicai.byteera.activity.shouyi;

import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.shouyi.eventbus.InComeEvents;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.HeadViewMain;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by chenzhenxing on 15/10/25.
 */
public class InComeConditionActivity extends BaseActivity {
    private static final String TAG = InComeConditionActivity.class.getSimpleName();

    @Bind(R.id.title_view) HeadViewMain mTitle;
    @Bind(R.id.bar_cycle) SeekBar mBarCycle;
    @Bind(R.id.bar_rate) SeekBar mBarRate;
    @Bind(R.id.tv_bank) TextView tvBank;
    @Bind(R.id.tv_p2p) TextView tvP2p;
    @Bind(R.id.tv_count1) TextView tvCnt1;
    @Bind(R.id.tv_count3) TextView tvCnt3;

    private int check_time;
    private int check_rate;
    private boolean isCheckBank = true;
    private boolean isCheckP2p = true;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.income_condition);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        boolean intent_bank = getIntent().getBooleanExtra("conditionBank", false);
        boolean intent_p2p = getIntent().getBooleanExtra("conditionP2p", false);
        int intent_time = getIntent().getIntExtra("conditionTime", 90);
        int intent_rate = getIntent().getIntExtra("conditionRate", 18);
        if (intent_bank){
            tvBank.setBackgroundResource(R.drawable.textview_corner_shouyi_ischeck);
            isCheckBank = true;
        }else{
            tvBank.setBackgroundResource(R.drawable.textview_corner_shouyi);
            isCheckBank = false;
        }
        if(intent_p2p){
            tvP2p.setBackgroundResource(R.drawable.textview_corner_shouyi_ischeck);
            isCheckP2p = true;
        }else{
            tvP2p.setBackgroundResource(R.drawable.textview_corner_shouyi);
            isCheckP2p = false;
        }
        mBarCycle.setProgress(intent_time == 0 ? 90 : intent_time);
        tvCnt1.setText((intent_time == 0 ? 90 : intent_time) + "天");
        mBarRate.setProgress(intent_rate == 0 ? 18 : intent_rate);
        tvCnt3.setText((intent_rate == 0 ? 18 : intent_rate) + "%");
    }



    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        mTitle.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
        mBarCycle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                check_time = seekBar.getProgress() == 0 ? 90 : seekBar.getProgress();
                tvCnt1.setText(check_time + "天");
            }
        });
        mBarRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                check_rate = seekBar.getProgress() == 0 ? 1 : seekBar.getProgress();
                tvCnt3.setText(check_rate + "%");
            }
        });
    }



    @OnClick({R.id.tv_bank,R.id.tv_p2p,R.id.tv_replace,R.id.tv_confirm})
    public void clickListener(View v){
        switch (v.getId()){
            case R.id.tv_bank:
                if (isCheckBank){
                    tvBank.setBackgroundResource(R.drawable.textview_corner_shouyi);
                    isCheckBank = false;
                }else{
                    tvBank.setBackgroundResource(R.drawable.textview_corner_shouyi_ischeck);
                    isCheckBank = true;
                }
                break;
            case R.id.tv_p2p:
                if (isCheckP2p){
                    isCheckP2p = false;
                    tvP2p.setBackgroundResource(R.drawable.textview_corner_shouyi);
                }else{
                    isCheckP2p = true;
                    tvP2p.setBackgroundResource(R.drawable.textview_corner_shouyi_ischeck);
                }
                break;
            case R.id.tv_replace:
                doReset();
                break;
            case R.id.tv_confirm:
                doConfirm();
                break;
        }
    }


    /**重置**/
    private void doReset(){
        tvBank.setBackgroundResource(R.drawable.textview_corner_shouyi);
        tvP2p.setBackgroundResource(R.drawable.textview_corner_shouyi);
        mBarCycle.setProgress(90);
        mBarRate.setProgress(18);
        tvCnt1.setText(90 + "天");
        tvCnt3.setText(18 + "%");
        EventBus.getDefault().post(new InComeEvents(90, 18, false, false,true));
        finish();
    }

    /**确认**/
    private void doConfirm() {
        EventBus.getDefault().post(new InComeEvents(mBarCycle.getProgress(),mBarRate.getProgress(),isCheckBank,isCheckP2p,true));
        finish();
    }


}
