package com.zhicai.byteera.activity.message;


import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Bind;

public class AlertDialogActivity extends BaseActivity {
	@Bind(R.id.title) TextView mTextView;
	@Bind(R.id.btn_cancel) Button mButton;
	@Bind(R.id.image) ImageView imageView;
	@Bind(R.id.edit) EditText editText;

	private int position;

	@Override
	protected void loadViewLayout() {
		setFinishOnTouchOutside(false);
		setContentView(R.layout.activity_alert_dialog);
		ButterKnife.bind(this);

		// 提示内容
		String msg = getIntent().getStringExtra("msg");
		// 提示标题
		String title = getIntent().getStringExtra("title");
		position = getIntent().getIntExtra("position", -1);
		// 是否显示取消标题
		boolean isCanceTitle = getIntent().getBooleanExtra("titleIsCancel", false);
		// 是否显示取消按钮
		boolean isCanceShow = getIntent().getBooleanExtra("cancel", true);
		// 是否显示文本编辑框
		boolean isEditextShow = getIntent().getBooleanExtra("editTextShow", false);
		// 转发复制的图片的path
		String path = getIntent().getStringExtra("forwardImage");
		//
		String edit_text = getIntent().getStringExtra("edit_text");

		if (msg != null)
			((TextView) findViewById(R.id.alert_message)).setText(msg);
		if (title != null)
			mTextView.setText(title);
		if (isCanceTitle) {
			mTextView.setVisibility(View.GONE);
		}
		if (isCanceShow)
			mButton.setVisibility(View.VISIBLE);
		if (path != null) {
//			// 优先拿大图，没有去取缩略图
//			if (!new File(path).exists())
//				path = DownloadImageTask.getThumbnailImagePath(path);
//			imageView.setVisibility(View.VISIBLE);
//			((TextView) findViewById(R.id.alert_message)).setVisibility(View.GONE);
//			if (ImageCache.getInstance().get(path) != null) {
//				imageView.setImageBitmap(ImageCache.getInstance().get(path));
//			} else {
//				Bitmap bm = ImageUtils.decodeScaleImage(path, 150, 150);
//				imageView.setImageBitmap(bm);
//				ImageCache.getInstance().put(path, bm);
//			}

		}
		if (isEditextShow) {
			editText.setVisibility(View.VISIBLE);
			editText.setText(edit_text);
		}
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void updateUI() {

	}

	@Override
	protected void processLogic() {

	}

	public void ok(View view) {
		setResult(RESULT_OK,
				new Intent().putExtra("position", position).putExtra("edittext", editText.getText().toString())
		/* .putExtra("voicePath", voicePath) */);
		if (position != -1)
			ChatActivity.resendPos = position;
		finish();

	}

	public void cancel(View view) {
		finish();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		finish();
		return true;
	}

}
