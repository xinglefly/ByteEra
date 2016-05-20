package com.zhicai.byteera.activity.message;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;

import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.activity.message.adapter.ContactAdapter;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.SDLog;
import com.zhicai.byteera.widget.Sidebar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GroupPickContactsActivity extends BaseActivity {
	private ListView listView;
	/** 是否为一个新建的群组 */
	protected boolean isCreatingNewGroup;

	private PickContactAdapter contactAdapter;
	/** group中一开始就有的成员 */
	private List<String> exitingMembers;

	private ProgressDialog progressDialog;

	@Override
	protected void loadViewLayout() {

		setContentView(R.layout.activity_group_pick_contacts);

		String groupId = getIntent().getStringExtra("groupId");
		if (groupId == null) {// 创建群组
			isCreatingNewGroup = true;
		} else {
			// 获取此群组的成员列表
			EMGroup group = EMGroupManager.getInstance().getGroup(groupId);
			exitingMembers = group.getMembers();
		}
		if (exitingMembers == null)
			exitingMembers = new ArrayList<String>();
		// 获取好友列表
		final List<ZCUser> alluserList = new ArrayList<ZCUser>();
		for (ZCUser user : MyApp.getInstance().getContactList().values()) {
			if (!user.getUsername().equals(Constants.NEW_FRIENDS_USERNAME) & !user.getUsername().equals(Constants.GROUP_USERNAME))
				alluserList.add(user);
		}
		// 对list进行排序
		Collections.sort(alluserList, new Comparator<ZCUser>() {
			@Override
			public int compare(ZCUser lhs, ZCUser rhs) {
				return (lhs.getUsername().compareTo(rhs.getUsername()));

			}
		});

		listView = (ListView) findViewById(R.id.list);
		contactAdapter = new PickContactAdapter(this, R.layout.row_contact_with_checkbox, alluserList);
		listView.setAdapter(contactAdapter);
		((Sidebar) findViewById(R.id.sidebar)).setListView(listView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
				checkBox.toggle();
			}
		});

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

	/**
	 * 确认选择的members
	 * @param v
	 */
	public void save(View v) {

		progressDialog = ProgressDialog.show(GroupPickContactsActivity.this, "waiting...", "creating group...");
		progressDialog.setCanceledOnTouchOutside(false);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					final EMGroup group = EMGroupManager.getInstance().createPrivateGroup("群聊", "", getToBeAddMembers().toArray(new String[0]), true);
					runOnUiThread(new Runnable() {
						public void run() {
							progressDialog.dismiss();
							ActivityUtil.startActivity(GroupPickContactsActivity.this,new Intent(GroupPickContactsActivity.this, ChatActivity.class).putExtra(
									"groupId", group.getGroupId()).putExtra("chatType", ChatActivity.CHATTYPE_GROUP));
							finish();
						}
					});
				} catch (final EaseMobException e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						public void run() {
							progressDialog.dismiss();
							Toast.makeText(GroupPickContactsActivity.this, "fail to create group" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
						}
					});
				}
			}
		}).start();
	}

	/**
	 * 获取要被添加的成员
	 * @return
	 */
	private List<String> getToBeAddMembers() {
		List<String> members = new ArrayList<String>();
		int length = contactAdapter.isCheckedArray.length;
		for (int i = 0; i < length; i++) {
			String username = contactAdapter.getItem(i).getUsername();
			if (contactAdapter.isCheckedArray[i] && !exitingMembers.contains(username)) {
				members.add(username);
			}
		}
		return members;
	}

	/**
	 * adapter
	 */
	private class PickContactAdapter extends ContactAdapter {

		private boolean[] isCheckedArray;

		public PickContactAdapter(Context context, int resource, List<ZCUser> users) {
			super(context, resource, users);
			isCheckedArray = new boolean[users.size()];
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			final String username = getItem(position).getUsername();

			// 选择框checkbox
			final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
			if (exitingMembers != null && exitingMembers.contains(username)) {
				checkBox.setButtonDrawable(null);
			} else {
				checkBox.setButtonDrawable(R.drawable.checkbox_bg_selector);
			}

			if (checkBox != null) {
				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// 群组中原来的成员一直设为选中状态
						if (exitingMembers.contains(username)) {
							isChecked = true;
							checkBox.setChecked(true);
						}
						isCheckedArray[position] = isChecked;
					}
				});
				// 群组中原来的成员一直设为选中状态
				if (exitingMembers.contains(username)) {
					checkBox.setChecked(true);
					isCheckedArray[position] = true;
				} else {
					checkBox.setChecked(isCheckedArray[position]);
				}
			}
			return view;
		}
	}

	public void back(View view) {
		finish();
	}

}
