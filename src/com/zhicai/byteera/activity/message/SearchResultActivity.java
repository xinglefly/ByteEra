package com.zhicai.byteera.activity.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.bean.ZCUser;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Bind;

public class SearchResultActivity extends BaseActivity {

	@Bind(R.id.image_back) ImageView back;
	@Bind(R.id.edit_content) EditText content;
	@Bind(R.id.list_result) ListView listResult;
	@Bind(R.id.text_search_line) TextView searchLine;
	private ListResultAdapter adapter;
	private LayoutInflater inflater;
	private List<ZCUser> contactList;
	private List<ZCUser> contactResult;
	ListResultFilter contactFilter;
	private LinearLayout footSearch;
	private TextView searchCondition;
	private CharSequence condition;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_search_result);
		ButterKnife.bind(this);
	}

	@Override
	protected void initData() {
		inflater = LayoutInflater.from(this);

		View footView = inflater.inflate(R.layout.footview_search, null);
		footSearch = (LinearLayout) footView.findViewById(R.id.linear_footview);
		searchCondition = (TextView) footView.findViewById(R.id.text_search_condition);
		listResult.addFooterView(footView, null, false);
		footSearch.setVisibility(View.GONE);

		contactList = new ArrayList<ZCUser>();
		Map<String, ZCUser> userMap = MyApp.getInstance().getContactList();
		Iterator<Entry<String, ZCUser>> iterator = userMap.entrySet().iterator();

		while (iterator.hasNext()) {
			Entry<String, ZCUser> entry = iterator.next();
			contactList.add(entry.getValue());
		}

		contactResult = new ArrayList<ZCUser>();

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		listResult.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent();
				intent.setClass(SearchResultActivity.this, ChatActivity.class);
				intent.putExtra("userId", contactResult.get(arg2).getUsername());
				startActivity(intent);
				finish();
			}
		});

		content.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				footSearch.setVisibility(View.VISIBLE);
				condition = s;
				if (s.toString().trim().length() == 0) {
					footSearch.setVisibility(View.GONE);
					if (adapter != null) {
						contactResult.clear();
						adapter.notifyDataSetChanged();
					}
				} else {
					getContactFilter().filter(s);
					ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#55a8ec"));
					String content = "Search“" + s + "”from other";
					SpannableStringBuilder builder = new SpannableStringBuilder(content);
					builder.setSpan(span, content.indexOf(s.toString()), content.indexOf(s.toString()) + s.length(),
							Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
					searchCondition.setText(builder);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}

	@Override
	protected void updateUI() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void processLogic() {
		// TODO Auto-generated method stub

	}

	private class ListResultAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return contactResult.size();
		}

		@Override
		public Object getItem(int position) {
			return contactResult.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.listview_result_item, null);
				holder.avatar = (ImageView) convertView.findViewById(R.id.image_avatar);
				holder.nickname = (TextView) convertView.findViewById(R.id.text_nickname);
				holder.header = (TextView) convertView.findViewById(R.id.text_header);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (position >= 1) {
				holder.header.setText("In Contact");
				holder.header.setVisibility(View.GONE);

			} else {
				holder.header.setVisibility(View.VISIBLE);
			}
			int chageTextColor = 0;
			ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#55a8ec"));
			SpannableStringBuilder builder = new SpannableStringBuilder(contactResult.get(position).getNick());
			if (Character.isLowerCase(condition.charAt(0))) {
				chageTextColor = contactResult.get(position).getNick().toLowerCase().indexOf(condition.toString());
			} else {
				chageTextColor = contactResult.get(position).getNick().toUpperCase().indexOf(condition.toString());

			}

			if (chageTextColor != -1) {
				builder.setSpan(span, chageTextColor, chageTextColor + condition.length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				holder.nickname.setText(builder);
			} else {
				holder.nickname.setText(contactResult.get(position).getNick());
			}

			return convertView;
		}
	}

	private class ViewHolder {
		private ImageView avatar;
		private TextView nickname;
		private TextView header;
	}

	protected ListResultFilter getContactFilter() {
		if (contactFilter == null) {
			contactFilter = new ListResultFilter();
		}
		return contactFilter;

	}

	private class ListResultFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();

			if (contactList == null) {
				contactList = new ArrayList<ZCUser>();
			}
			String searchValue = constraint.toString().toLowerCase();
			final int count = contactList.size();
			final ArrayList<ZCUser> newValues = new ArrayList<ZCUser>();

			for (int i = 0; i < count; i++) {
				final ZCUser value = contactList.get(i);
				// 按昵称搜索
				if (!TextUtils.isEmpty(value.getNick())) {
					String valueText = value.getNick();
					if (valueText.charAt(0) >= 0x0391 && valueText.charAt(0) <= 0xFFE5) { // 中文字符
						if (value.getNick().startsWith(searchValue) || value.getNick().contains(searchValue)) {
							newValues.add(value);
						}
					} else {
						if (valueText.toLowerCase().startsWith(searchValue)
								|| valueText.toLowerCase().contains(searchValue) || valueText.startsWith(searchValue)
								|| valueText.contains(searchValue)) {
							newValues.add(value);
						}
					}
				}
			}

			results.values = newValues;
			results.count = newValues.size();
			return results;

		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			adapter = new ListResultAdapter();
			if (results.values != null && results.count > 0) {
				contactResult = (List<ZCUser>) results.values;
				listResult.setAdapter(adapter);
			} else {

				contactResult.clear();
				adapter.notifyDataSetChanged();
				adapter.notifyDataSetInvalidated();
				Toast.makeText(SearchResultActivity.this, "没有找到你要的结果!", 1).show();
			}
		}
	}

}
