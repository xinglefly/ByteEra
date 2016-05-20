/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhicai.byteera.activity.message;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.activity.message.adapter.ContactAdapter;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.widget.Sidebar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import butterknife.ButterKnife;
import butterknife.Bind;

@SuppressWarnings("unused")
public class PickContactNoCheckboxActivity extends BaseActivity {

	@Bind(R.id.list) ListView listView;
	@Bind(R.id.sidebar) Sidebar sidebar;

	protected ContactAdapter contactAdapter;
	private List<ZCUser> contactList;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_pick_contact_no_checkbox);
		ButterKnife.bind(this);

		sidebar.setListView(listView);
		contactList = new ArrayList<ZCUser>();

		getContactList();

		contactAdapter = new ContactAdapter(this, R.layout.row_contact, contactList);
		listView.setAdapter(contactAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onListItemClick(position);
			}
		});
	}

	protected void onListItemClick(int position) {
		setResult(RESULT_OK, new Intent().putExtra("userCard", (Serializable) contactAdapter.getItem(position)));
		finish();
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

	public void back(View view) {
		finish();
	}

	private void getContactList() {
		contactList.clear();
		Map<String, ZCUser> users = MyApp.getInstance().getContactList();
		Iterator<Entry<String, ZCUser>> iterator = users.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, ZCUser> entry = iterator.next();
			if (!entry.getKey().equals(Constants.NEW_FRIENDS_USERNAME) && !entry.getKey().equals(Constants.GROUP_USERNAME))
				contactList.add(entry.getValue());
		}
		Collections.sort(contactList, new Comparator<ZCUser>() {

			@Override
			public int compare(ZCUser lhs, ZCUser rhs) {
				return lhs.getUsername().compareTo(rhs.getUsername());
			}
		});
	}

}
