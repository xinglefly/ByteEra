package com.zhicai.byteera.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.event.JpushEvent;
import com.zhicai.byteera.activity.product.ProductDetailActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JpushReceiver extends BroadcastReceiver {
	private static final String TAG = JpushReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			Log.d(TAG, "[JpushReceiver] 接收到推送下来的通知的ID: " + notifactionId);
			printBundle(bundle, notifactionId);
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.d(TAG, "[JpushReceiver] 用户点击打开了通知");
			int notifaction_id = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			selectIdJumpActivity(context, notifaction_id);
		}else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
			boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.w(TAG, "[JpushReceiver]" + intent.getAction() +" connected state change to "+connected);
		}
	}



	private void selectIdJumpActivity(Context context, int notifaction_id) {
		try {
            JpushProductEntity productEntity = MyApp.getInstance().db.findFirst(Selector.from(JpushProductEntity.class).where("notifaction_id", "=", notifaction_id));
            if (productEntity!=null && !TextUtils.isEmpty(productEntity.getProduct_id())){
                Intent in = new Intent(context, ProductDetailActivity.class);
                in.putExtra("productId", productEntity.getProduct_id());
				in.putExtra("notifaction",true);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(in);
                EventBus.getDefault().post(new JpushEvent(true));
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
	}


	private static String printBundle(Bundle bundle,final int notifactionId) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}
				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					JSONArray products = json.optJSONArray("products");
					final List<JpushProductEntity> jpushProductEntityList = new ArrayList();
					for (int i = 0; i < products.length(); i++) {
						JSONObject product = (JSONObject) products.get(i);
						JpushProductEntity jpushProductEntity = new JpushProductEntity(notifactionId,product.optString("product_id"), product.optString("title"), product.optString("small_image"),
								product.optLong("product_time"), product.optString("product_url"), product.optInt("limit"), (float) product.optDouble("income"));
						jpushProductEntityList.add(jpushProductEntity);
						Log.d(TAG, "jpushProductEntity-->" + jpushProductEntity.toString());
					}

					MyApp.getInstance().executorService.execute(new Runnable() {
						@Override
						public void run() {
							try {
								MyApp.getInstance().db.saveBindingIdAll(jpushProductEntityList);
								Log.d(TAG, "save jpushProduct db ok-->" + jpushProductEntityList.size());
							} catch (DbException e) {
								e.printStackTrace();
							}
						}
					});
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}
			}
		}
		return sb.toString();
	}

}
