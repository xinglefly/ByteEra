package com.zhicai.byteera.activity.message;

import android.content.Intent;
import android.os.Bundle;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.commonutil.ActivityUtil;


public class ForwardMessageActivity extends PickContactNoCheckboxActivity {
	private ZCUser selectUser;
	private String forward_msg_id;

	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		forward_msg_id = getIntent().getStringExtra("forward_msg_id");
	}
	


	@Override
	protected void onListItemClick(int position) {
//		if (position != 0) {
			selectUser = contactAdapter.getItem(position);
			Intent intent = new Intent(ForwardMessageActivity.this, AlertDialogActivity.class);
			intent.putExtra("cancel", true);
			intent.putExtra("titleIsCancel", true);
			intent.putExtra("msg", getString(R.string.confirm_forward_to, selectUser.getUsername()));
			startActivityForResult(intent, 1);
//		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			try {
				ChatActivity.activityInstance.finish();
			} catch (Exception e) {
			}
			Intent intent = new Intent(this, ChatActivity.class);
			if (selectUser == null)
				return;
			// it is single chat
			intent.putExtra("userId", selectUser.getUsername());
			intent.putExtra("forward_msg_id", forward_msg_id);
			ActivityUtil.startActivity(this,intent);
			finish();

		}

		super.onActivityResult(requestCode, resultCode, data);
	}
}
