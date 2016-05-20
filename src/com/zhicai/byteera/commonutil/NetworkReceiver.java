package com.zhicai.byteera.commonutil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.zhicai.byteera.activity.event.NetWorkEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by chenzhenxing on 15/12/23.
 */
public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();

        Log.d("NetWorkReceiver","listener mobile-->"+mobileInfo.isConnected());
        Log.d("NetWorkReceiver","listener wifi-->"+wifiInfo.isConnected());
//        Log.d("NetWorkReceiver", "listener active-->" + activeInfo.isConnected());

        if (mobileInfo.isConnected()) {
            ToastUtil.showToastText("温馨提示，手机网络会损失流量请连接附近wifi！");
            EventBus.getDefault().post(new NetWorkEvent(true));
        }

        if (wifiInfo.isConnected()) EventBus.getDefault().post(new NetWorkEvent(true));
        else ToastUtil.showToastText("网络未连接，请检查网络");
    }
}