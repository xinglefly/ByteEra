
package com.zhicai.byteera.activity.message;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.easemob.chat.EMMessage;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Bind;

public class ContextMenuActivity extends BaseActivity {

	@Bind(R.id.tv_copy) TextView copyTextView;
	@Bind(R.id.copy_line) View copyLineView;
	@Bind(R.id.tv_delete) TextView deleteTextView;
	@Bind(R.id.delete_line) View deleteLineView;
	@Bind(R.id.tv_forward) TextView forwardTextView;
	@Bind(R.id.forward_line) View forwardLineView;
	private int position;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_context_menu);
		ButterKnife.bind(this);

		int type = getIntent().getIntExtra("type", -1);
		if (type == EMMessage.Type.TXT.ordinal()) {

		} else if (type == EMMessage.Type.LOCATION.ordinal()) {
			copyTextView.setVisibility(View.GONE);
			copyLineView.setVisibility(View.GONE);
			forwardTextView.setVisibility(View.GONE);
			forwardLineView.setVisibility(View.GONE);
		} else if (type == EMMessage.Type.IMAGE.ordinal()) {
			copyTextView.setVisibility(View.GONE);
			copyLineView.setVisibility(View.GONE);

		} else if (type == EMMessage.Type.VOICE.ordinal()) {
			copyTextView.setVisibility(View.GONE);
			copyLineView.setVisibility(View.GONE);
			forwardTextView.setVisibility(View.GONE);
			forwardLineView.setVisibility(View.GONE);

			deleteTextView.setText(getResources().getString(R.string.delete_voice));

		} else if (type == EMMessage.Type.VIDEO.ordinal()) {
			copyTextView.setVisibility(View.GONE);
			copyLineView.setVisibility(View.GONE);
			forwardTextView.setVisibility(View.GONE);
			forwardLineView.setVisibility(View.GONE);

			deleteTextView.setText(getResources().getString(R.string.delete_video));
		}

		position = getIntent().getIntExtra("position", -1);

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

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void copy(View view) {
		setResult(ChatActivity.RESULT_CODE_COPY, new Intent().putExtra("position", position));
		finish();
	}

	public void delete(View view) {
		setResult(ChatActivity.RESULT_CODE_DELETE, new Intent().putExtra("position", position));
		finish();
	}

	public void forward(View view) {
		setResult(ChatActivity.RESULT_CODE_FORWARD, new Intent().putExtra("position", position));
		finish();
	}

	public void open(View v) {
		setResult(ChatActivity.RESULT_CODE_OPEN, new Intent().putExtra("position", position));
		finish();
	}

	public void download(View v) {
		setResult(ChatActivity.RESULT_CODE_DOWNLOAD, new Intent().putExtra("position", position));
		finish();
	}

	public void toCloud(View v) {
		setResult(ChatActivity.RESULT_CODE_TO_CLOUD, new Intent().putExtra("position", position));
		finish();
	}

}
