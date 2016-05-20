package com.zhicai.byteera;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.EMMessage.Type;
import com.easemob.chat.OnMessageNotifyListener;
import com.easemob.chat.OnNotificationClickListener;
import com.zhicai.byteera.activity.message.ChatActivity;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.LogUtil;
import com.zhicai.byteera.commonutil.SDLog;
import com.zhicai.byteera.commonutil.Utils;

import java.util.Iterator;
import java.util.List;

public class HXSDKHelper {

	private static final String TAG = "HXSDKHelper";

	//application context
	protected Context appContext = null;
	//MyConnectionListener
	protected EMConnectionListener connectionListener = null;
	//init flag: test if the sdk has been inited before, we don't need to init again
	private boolean sdkInited = false;
	//the global HXSDKHelper instance
	private static HXSDKHelper sdkHelper = null;


	/**
	 * get global instance
	 * @return
	 */
	public static HXSDKHelper getInstance() {
		if (sdkHelper == null) {
			sdkHelper = new HXSDKHelper();
		}
		return sdkHelper;
	}


	public synchronized boolean onInit(Context context) {
		appContext = context;

		if (sdkInited) {
			return true;
		}

		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid);

		Log.d(TAG, "process app name : " + processAppName);

		// 如果app启用了远程的service，此application:onCreate会被调用2次
		// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
		// 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process
		// name就立即返回
		if (processAppName == null || !processAppName.equalsIgnoreCase("com.zhicai.byteera")) {
			Log.e(TAG, "enter the service process!");

			// 则此application::onCreate 是被service 调用的，直接返回
			return false;
		}

		// 初始化环信SDK,一定要先调用init()
		EMChat.getInstance().init(context);

		EMChat.getInstance().setDebugMode(true);

		SDLog.d(TAG, "initialize EMChat SDK");
		initHXOptions();
		initListener();
		sdkInited = true;
		return true;
	}


	/**
	 * please make sure you have to get EMChatOptions by following method and
	 * set related options EMChatOptions options =
	 * EMChatManager.getInstance().getChatOptions();
	 */
	protected void initHXOptions() {
		SDLog.d(TAG, "init HuanXin Options");

		// 获取到EMChatOptions对象
		EMChatOptions options = EMChatManager.getInstance().getChatOptions();
		// 默认添加好友时，是不需要验证的，改成需要验证
		options.setAcceptInvitationAlways(false);
		// 默认环信是不维护好友关系列表的，如果app依赖环信的好友关系，把这个属性设置为true
		options.setUseRoster(true);
		// 设置收到消息是否有新消息通知(声音和震动提示)，默认为true
		options.setNotifyBySoundAndVibrate(true);
		// 设置收到消息是否有声音提示，默认为true
		options.setNoticeBySound(true);
		// 设置收到消息是否震动 默认为true
		options.setNoticedByVibrate(true);
		// 设置语音消息播放是否设置为扬声器播放 默认为true
		options.setUseSpeaker(true);
		// 设置是否需要已读回执
		options.setRequireAck(true);
		// 设置是否需要已送达回执
		// options.setRequireDeliveryAck(true);

		// 设置notification消息点击时，跳转的intent为自定义的intent
		options.setOnNotificationClickListener(new OnNotificationClickListener() {
			@Override
			public Intent onNotificationClick(EMMessage message) {
				Intent intent = new Intent(appContext, ChatActivity.class);
				ChatType chatType = message.getChatType();
				if (chatType == ChatType.Chat) { // 单聊信息
					intent.putExtra("userId", message.getFrom());
					intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
				} else { // 群聊信息
							// message.getTo()为群聊id
					intent.putExtra("groupId", message.getTo());
					intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
				}
				return intent;
			}
		});
		options.setNotifyText(new OnMessageNotifyListener() {
			@Override
			public String onNewMessageNotify(EMMessage message) {
				// 设置状态栏的消息提示，可以根据message的类型做相应提示
				String ticker = Utils.getMessageDigest(message, appContext);
				if (message.getType() == Type.TXT)
					ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
				return message.getFrom() + ": " + ticker;
			}

			@Override
			public String onLatestMessageNotify(EMMessage message, int fromUsersNum, int messageNum) {
				return null;
				// return fromUsersNum + "个基友，发来了" + messageNum + "条消息";
			}

			@Override
			public String onSetNotificationTitle(EMMessage message) {
				// 修改标题,这里使用默认
				return null;
			}

			@Override
			public int onSetSmallIcon(EMMessage message) {
				// 设置小图标
				return 0;
			}
		});
	}

	/**
	 * logout HuanXin SDK
	 */
	public void logout(final EMCallBack callback) {
		EMChatManager.getInstance().logout(new EMCallBack() {

			@Override
			public void onSuccess() {
				if (callback != null) {
					callback.onSuccess();
				}
			}
			@Override
			public void onError(int code, String message) {
			}

			@Override
			public void onProgress(int progress, String status) {
				if (callback != null) {
					callback.onProgress(progress, status);
				}
			}
		});
	}

	/**
	 * 检查是否已经登录过
	 * @return
	 */
	public boolean isLogined() {
		return EMChat.getInstance().isLoggedIn();
	}

	/**
	 * init HuanXin listeners
	 */
	protected void initListener() {
		SDLog.d(TAG, "init listener");
		// create the global connection listener
		EMChatManager.getInstance().addConnectionListener(new EMConnectionListener() {
			@Override public void onDisconnected(int error) {
				if (error == EMError.USER_REMOVED) {
					onCurrentAccountRemoved();
				} else if (error == EMError.CONNECTION_CONFLICT) {
					onConnectionConflict();
				} else {

				}
			}

			@Override
			public void onConnected() {

			}
		});
	}

	/**
	 * the developer can override this function to handle user is removed error
	 */
	private void onCurrentAccountRemoved() {
		Intent intent = new Intent();
		intent.setAction("logout");
		intent.putExtra(Constants.ACCOUNT_REMOVED,true);
		appContext.sendBroadcast(intent);
	}

	/** handle the connection connected */
	private void onConnectionConflict() {

		Intent intent = new Intent();
		intent.setAction("logout");
		intent.putExtra(Constants.ACCOUNT_CONFLICT,true);
		appContext.sendBroadcast(intent);
	}

	/**
	 * check the application process name if process name is not qualified, then
	 * we think it is a service process and we will not init SDK
	 * @param pID
	 * @return
	 */
	private String getAppName(int pID) {
		String processName = null;
		ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = appContext.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
			try {
				if (info.pid == pID) {
					CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
					LogUtil.d("Id:  %s,   ProcessName: %s , Label:%s", info.pid ,info.processName, c.toString());
					processName = c.toString();
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
				 LogUtil.d("Error>> : "+ e.toString());
			}
		}
		return processName;
	}

}
