package com.zhicai.byteera.commonutil;

import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhicai.byteera.R;

public class LoadingDialogShow {
	Context context;
	Dialog dialog;
	TextView tv;
	ImageView ivResult;
	ProgressBar pb;

	public LoadingDialogShow(Context context){
		this.context = context;
		dialog = new Dialog(context);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(true);
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View view=View.inflate(context, R.layout.loading_dialog, null);
		tv=(TextView) view.findViewById(R.id.dialog_submit_text);
		ivResult=(ImageView) view.findViewById(R.id.dialog_iv);
		pb=(ProgressBar) view.findViewById(R.id.dialog_pb);
		dialog.setContentView(view);
	}

	public void setMessage(String hint){
		tv.setVisibility(View.VISIBLE);
		tv.setText(hint);
	}

	/**
	 * resultStatus	true:成功	false:失败
	 * 1秒后dismiss
	 */
	public void setResultStatusDrawable(boolean resultStatus,String hint) {
		tv.setText(hint);
		if(resultStatus){
		ivResult.setBackgroundResource(R.drawable.progerss_ok);
		}else{
			tv.setTextColor(context.getResources().getColor(R.color.orange));
			ivResult.setBackgroundResource(R.drawable.progerss_error);	
		}
		ivResult.setVisibility(View.VISIBLE);
		pb.setVisibility(View.GONE);
		new Thread(new Runnable() {
			@Override public void run() {
				SystemClock.sleep(1000);
				dialog.dismiss();
			}
		}).start();
	}
	public void show(){
		if(dialog.isShowing()) {
			dialog.dismiss();
		}
		tv.setTextColor(context.getResources().getColor(R.color.babybull));
		pb.setVisibility(View.VISIBLE);
		ivResult.setVisibility(View.GONE);
		dialog.show();
	}
	public void dismiss(){
		dialog.dismiss();
	}

	public boolean isShowing() {
		if(dialog.isShowing()){
			return true;
		}else{
			return false;
		}
	}
}
