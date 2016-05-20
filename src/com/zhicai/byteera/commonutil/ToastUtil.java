package com.zhicai.byteera.commonutil;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;

/**
 * Toast Util</br>
 * when the next toast show,if the previous</br>
 * is showing reust it,or create a new toast.
 *
 * @author xinglefly
 *
 */
public class ToastUtil {
	private static Toast toast = null;
	private static String oldmsg;
	private static long oneTime = 0;
	private static Context context = MyApp.getInstance();

	public static void showToastText(String text) {
		showToastText(text, Toast.LENGTH_SHORT);
	}

	public static void showLongToastText(String text){
		showToastText(text, Toast.LENGTH_LONG);
	}
	public static void showToastText(int resId) {
		showToastText(context.getString(resId), Toast.LENGTH_SHORT);
	}

	private static void showToastText(final String text, final int duration) {
		if (UIUtils.isRunInMainThread()) {
			showToast(text,duration);
		} else {
			post(new Runnable() {
				@Override
				public void run() {
					showToast(text,duration);
				}
			});
		}
	}
	public static boolean post(Runnable runnable) {
		return UIUtils.getHandler().post(runnable);
	}


	private static void showToast(String text, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, text, duration);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			long twoTime = System.currentTimeMillis();
			if (text.equals(oldmsg)) {
				if (twoTime - oneTime > duration) {
					toast.show();
				}
			} else {
				oldmsg = text;
				toast.setText(text);
				toast.setDuration(duration);
				toast.show();
			}
		}
	}


	public static boolean showNetConnectionError(Context context){
		if(!Utils.checkNetwork(context)){
			ToastUtil.showToastText(R.string.network_not_connect);
			return true;
		}
		return false;
	}
	public static boolean showPhoneError(String phone){
		if(TextUtils.isEmpty(phone)){
			ToastUtil.showToastText(R.string.username_empty);
			return true;
		}
		if(phone.length()!=11){
			ToastUtil.showToastText(R.string.phone_error);
			return true;
		}
		return false;
	}
	public static boolean showPwdError(String pwd){
		if(TextUtils.isEmpty(pwd)){
			ToastUtil.showToastText(R.string.pwd_empty);
			return true;
		}
		if(pwd.length()<6||pwd.length()>14){
			ToastUtil.showToastText(R.string.pwd_error);
			return true;
		}
		return false;
	}
	public static boolean showVerfyNumError(String verfyNum){
		if(TextUtils.isEmpty(verfyNum)){
			ToastUtil.showToastText(R.string.verfynum_empty);
			return true;
		}
		if(verfyNum.length()!=4){
			ToastUtil.showToastText(R.string.verfynum_error);
			return true;
		}
		return false;
	}
}
