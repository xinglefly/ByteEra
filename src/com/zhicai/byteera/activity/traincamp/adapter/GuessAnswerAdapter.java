package com.zhicai.byteera.activity.traincamp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.List;

@SuppressWarnings("unused")
public class GuessAnswerAdapter extends BaseAdapter {
	protected Context context;
	protected List<String > list = null;
	private boolean flag;

	public GuessAnswerAdapter(Context context, List<String> list, boolean flag) {
		this.list = list;
		this.context = context;
		this.flag = flag;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView==null){convertView = LayoutInflater.from(context).inflate(R.layout.activity_guess_answer_item,parent,false);}

		TextView tv_answer_value = ViewHolder.get(convertView, R.id.tv_answer_value);

		String str = list.get(position);

		if (PreferenceUtils.getInstance(context).isGuessPicFlag()==true){
			tv_answer_value.setTextColor(Color.parseColor("#ff0000"));
		}

		tv_answer_value.setText(str);

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
