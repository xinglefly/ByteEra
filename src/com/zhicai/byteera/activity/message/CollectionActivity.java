package com.zhicai.byteera.activity.message;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Bind;

public class CollectionActivity extends BaseActivity {

	@Bind(R.id.img_back) ImageView back;
	@Bind(R.id.tv_titlename) TextView titleName;
	@Bind(R.id.img_search) ImageView search;
	@Bind(R.id.img_update_bt) ImageView plus;
	@Bind(R.id.listview) ListView listView;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_collection);
		ButterKnife.bind(this);
	}

	@Override
	protected void initData() {
		back.setVisibility(View.VISIBLE);
		search.setVisibility(View.VISIBLE);
		plus.setVisibility(View.GONE);

		titleName.setText("发送收藏内容");

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void updateUI() {
		// TODO Auto-generated method stub
		CollectionAdapter adapter = new CollectionAdapter();
//		listView.setAdapter(adapter);
	}

	@Override
	protected void processLogic() {
		// TODO Auto-generated method stub

	}

	private class CollectionAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}

	}

}
