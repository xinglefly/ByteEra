package com.zhicai.byteera.activity.traincamp.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.List;

@SuppressWarnings("unused")
public class GuessOptionsAdapter extends BaseAdapter {
	private static final String TAG = GuessOptionsAdapter.class.getSimpleName();

	private Context context;
	private List<String> list = null;

	public GuessOptionsAdapter(Context context, List<String> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView==null){convertView = LayoutInflater.from(context).inflate(R.layout.activity_guess_game_item,parent,false);}

		TextView tv_item = ViewHolder.get(convertView, R.id.tv_item);

		final String options = list.get(position);

		//点击pass时过滤字
		if (options.equals(Constants.Guess_filter_1.equals("")?"":Constants.Guess_filter_1) ||
				options.equals(Constants.Guess_filter_2.equals("")?"":Constants.Guess_filter_2)||
				options.equals(Constants.Guess_filter_3.equals("")?"":Constants.Guess_filter_3)||
				options.equals(Constants.Guess_filter_4.equals("")?"":Constants.Guess_filter_4))
			tv_item.setText("");
		else
			tv_item.setText(options);

		return convertView;
	}


	@Override
	public int getItemViewType(int position) {
		return AdapterView.ITEM_VIEW_TYPE_IGNORE;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


}
