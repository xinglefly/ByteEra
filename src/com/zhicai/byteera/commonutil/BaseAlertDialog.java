package com.zhicai.byteera.commonutil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * A warpper of AlertDialog</br>
 * post a callback Interface "BaseAlertDialogCallback"
 * @author xinglefly
 *
 */
/*new BaseAlertDialog(BaseAlertDialog.BaseAlertDialogCallback.FLAG_SCAN_OTHER,
						new BaseAlertDialog.BaseAlertDialogCallback() {
							@Override
							public void positive(int flag) {
							}

							@Override
							public void negative(int flag) {

							}
						}, context,"回答正确" , "积木盒子", "下一题", "分享");*/


@SuppressWarnings("unused")
public class BaseAlertDialog {
	public BaseAlertDialog(final int flag,final BaseAlertDialogCallback callback,Context context,String title,String message,String positive,String negative){
		
		new AlertDialog.Builder(context)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton(positive, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				callback.positive(flag);
			}
		})
		.setNegativeButton(negative, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				callback.negative(flag);
			}
		})
		.create()
		.show();
		
	}


	/**
	 * 回调接口
	 * @author xinglefly
	 *
	 */
	public interface BaseAlertDialogCallback{
	    int FLAG_SCAN_ONLINE=0;
	    int FLAG_SCAN_OTHER=1;
	    int FLAG_CONNECT_OTHER=2;
	    
		void positive(int flag);
		void negative(int flag);
	}

}
