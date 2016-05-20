package com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord;

import android.graphics.Color;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.ExchangeEntity;
import com.zhicai.byteera.activity.event.DayTaskEvent;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.goodsexchange.Exchange;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by songpengfei on 15/9/23.
 * 兑换界面
 */
@SuppressWarnings("unused")
public class ExchangeActivity extends BaseActivity{

    private static final String TAG = ExchangeActivity.class.getSimpleName();

    @Bind(R.id.iv_change_pic) ImageView iv_change_pic;
    @Bind(R.id.iv_change_cancel) ImageView iv_change_cancel;
    @Bind(R.id.iv_change_accomplish) ImageView iv_change_accomplish;
    @Bind(R.id.ll_exchange)LinearLayout ll_exchange;

    //兑换
    @Bind(R.id.rl_exchange) RelativeLayout rl_exchange;
    @Bind(R.id.tv_change_name) TextView tv_change_name;
    @Bind(R.id.tv_change_number) TextView tv_change_number;
    @Bind(R.id.tv_change_coin) TextView tv_change_coin;
    //确认兑换
    @Bind(R.id.rl_confirm_exchange) RelativeLayout rl_confirm_exchange;
    @Bind(R.id.tv_exchange_cancle) TextView tv_exchange_cancle;
    @Bind(R.id.tv_exchange_confirm) TextView tv_exchange_confirm;
    @Bind(R.id.tv_exchange_des) TextView tv_exchange_des;
    //描述
    @Bind(R.id.ll_exchange_des)LinearLayout ll_exchange_des;
    @Bind(R.id.tv_exchange) TextView tv_exchange;
    @Bind(R.id.tv_change_description) TextView tv_change_description;

    //收货地址
    @Bind(R.id.rl_exchange_address) RelativeLayout rl_exchange_address;
    @Bind(R.id.et_add_name) EditText et_add_name;
    @Bind(R.id.et_add_phone)EditText et_add_phone;
    @Bind(R.id.et_address) EditText et_address;
    @Bind(R.id.et_zip) EditText et_zip;
    @Bind(R.id.tv_add_confirm) TextView tv_add_confirm;


    private ExchangeEntity exchange;
    private String record_id;


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_exchange);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        exchange = getIntent().getParcelableExtra("exchange");
        if (exchange!=null){
            iv_change_pic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            ImageLoader.getInstance().displayImage(exchange.getItem_image(), iv_change_pic);
            tv_change_name.setText(exchange.getItem_name());
            tv_change_number.setText("数量："+exchange.getItem_left_count());
            tv_change_coin.setText("财富值："+exchange.getItem_coin());
            tv_change_description.setText(exchange.getItem_desc());
            tv_change_description.setAutoLinkMask(Linkify.WEB_URLS);
            tv_change_description.setMovementMethod(LinkMovementMethod.getInstance());
            tv_exchange_des.setText("点击确认将扣除您"+exchange.getItem_coin()+"财富值换取一张"+exchange.getItem_name());

            rl_exchange.setVisibility(View.VISIBLE);
            ll_exchange_des.setVisibility(View.VISIBLE);
            if (exchange.getItem_left_count()==0){
                iv_change_accomplish.setVisibility(View.VISIBLE);
                tv_exchange.setClickable(false);
                tv_exchange.setTextColor(Color.parseColor("#646464"));
                tv_exchange.setText("已兑完");
                tv_exchange.setBackgroundResource(R.drawable.textview_corner_exchange_cancel);
            }
        }
    }


    @OnClick({R.id.iv_change_cancel,R.id.tv_exchange,R.id.tv_exchange_cancle,R.id.tv_exchange_confirm,R.id.tv_add_confirm})
    public void clickListener(View v){
        switch(v.getId()){
            case R.id.iv_change_cancel:
                ActivityUtil.finishActivity(baseContext);
                break;
            case R.id.tv_exchange:
                rl_exchange.setVisibility(View.GONE);
                rl_confirm_exchange.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_exchange_confirm:
                doExchange(exchange.getItem_type(), exchange.getItem_id());
                break;
            case R.id.tv_exchange_cancle:
                rl_confirm_exchange.setVisibility(View.GONE);
                rl_exchange.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_add_confirm:
                if (VerifyEditIsNull()){
                    addExchangeInfo();
                }
                break;
        }
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {

    }

    public void doExchange(final int item_type,final String item_id) {
        if (DetermineIsLogin()) return;
        dialog.show();
        MyApp.getInstance().executorService.execute(new Runnable() {
            @Override
            public void run() {
                String userId = PreferenceUtils.getInstance(baseContext).getUserId();
                Exchange.DoExchangeItemReq sec = Exchange.DoExchangeItemReq.newBuilder().setUserId(userId).setItemId(item_id).build();
                TiangongClient.instance().send("chronos", "do_exchange_item", sec.toByteArray(), new BaseHandlerClass() {
                    @Override
                    public void onSuccess(byte[] buffer) {
                        try {
                            Exchange.DoExchangeItemResponse response = Exchange.DoExchangeItemResponse.parseFrom(buffer);
//                            Log.d(TAG,"res-->"+response.toString());
                            if (response.getErrorno() == 0) {
                                if (response.hasUserCoin()){
                                    MyApp.getInstance().setCoinCnt(response.getUserCoin());
                                }
                                if (item_type==1){ //实物
                                    record_id = response.getExchangeRecord().getRecordId();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                            rl_exchange.setVisibility(View.GONE);
                                            ll_exchange_des.setVisibility(View.GONE);
                                            ll_exchange.setVisibility(View.GONE);
                                            rl_exchange_address.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }else{ //虚拟
                                    updateDialog(true, "兑换成功");
                                }
                            } else {
                                updateDialog(false, "兑换积分不足");
                            }
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                            updateDialog(false, "服务器异常!");
                        }
                    }
                });
            }
        });
    }


    public boolean VerifyEditIsNull(){
        if (TextUtils.isEmpty(et_add_name.getText())){
            ToastUtil.showToastText("收货人姓名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(et_add_phone.getText())){
            ToastUtil.showToastText("收货人联系方式不能为空");
            return false;
        }
        if (TextUtils.isEmpty(et_address.getText())){
            ToastUtil.showToastText("收货地址不能为空");
            return false;
        }
        if (TextUtils.isEmpty(et_zip.getText())){
            ToastUtil.showToastText("邮编不能为空");
            return false;
        }
        return true;
    }

    public void addExchangeInfo() {
        dialog.show();
        MyApp.getInstance().executorService.execute(new Runnable() {
            @Override
            public void run() {
                String userId = PreferenceUtils.getInstance(baseContext).getUserId();
                Exchange.AddReceiverInfoReq sec = Exchange.AddReceiverInfoReq.newBuilder().setRecordId(record_id).setReceiverName(et_add_name.getText().toString()).
                        setReceiverTel(et_add_phone.getText().toString()).setReceiverAddress(et_address.getText().toString()).setReceiverZip(et_zip.getText().toString()).build();
                TiangongClient.instance().send("chronos", "add_receiver_info", sec.toByteArray(), new BaseHandlerClass() {
                    @Override
                    public void onSuccess(byte[] buffer) {
                        try {
                            Exchange.AddReceiverInfoResponse response = Exchange.AddReceiverInfoResponse.parseFrom(buffer);
                            Log.d(TAG,"res-->"+response.toString());
                            if (response.getErrorno() == 0) {
                                updateDialog(true, "订单已提交!");
                            } else {
                                updateDialog(false, "服务器异常!");
                            }
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                            updateDialog(false, "服务器异常!");
                        }
                    }
                });
            }
        });
    }

    private void updateDialog(final boolean tag, final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dialog.setResultStatusDrawable(tag, str);
                EventBus.getDefault().post(new DayTaskEvent(true));
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}
