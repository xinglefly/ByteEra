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
import com.zhicai.byteera.activity.bean.DayTask;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class DayTaskAdapter extends BaseAdapter {

	protected Context mContext;
	protected List<DayTask> dayTaskList = null;
	private TextView tvAction;

	public DayTaskAdapter(Context context) {
		this.mContext = context;
		this.dayTaskList = new ArrayList();
	}

	public void setAdapter(List mList){
		this.dayTaskList = mList;
		this.notifyDataSetChanged();
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) convertView = LayoutInflater.from(mContext).inflate(R.layout.day_task_item, parent, false);
		TextView tv_day_task = ViewHolder.get(convertView,R.id.tv_day_task);
		TextView tv_coin = ViewHolder.get(convertView,R.id.tv_coin);
		tvAction = ViewHolder.get(convertView,R.id.tv_action);

		DayTask dayTask = dayTaskList.get(position);
		tv_day_task.setText(dayTask.getName());
		tv_coin.setText("" + dayTask.getCoin());

		switch (dayTask.getStatus()){
			case 1:
				taskDoing();
				break;
			case 2:
				taskDone();
				break;
			case 3:
				taskComplete();
				break;

		}
		return convertView;
	}


	private void taskDoing() {
		tvAction.setText("待完成");
		tvAction.setTextColor(Color.parseColor("#ff3874"));
		tvAction.setBackgroundResource(R.drawable.textview_corner);
	}

	private void taskDone() {
		tvAction.setText("领取奖励");
		tvAction.setTextColor(Color.parseColor("#30d1de"));
        tvAction.setBackgroundResource(R.drawable.textview_corner_get);
	}

	private void taskComplete() {
		tvAction.setText("已领取");
		tvAction.setTextColor(Color.parseColor("#d4d4d4"));
        tvAction.setBackgroundResource(R.drawable.textview_corner_finish);
	}


	@Override
	public int getItemViewType(int position) {
		return AdapterView.ITEM_VIEW_TYPE_IGNORE;
	}

	@Override
	public int getCount() {
		return dayTaskList.size();
	}

	@Override
	public Object getItem(int position) {
		return dayTaskList.get(position);
	}

	@Override
	 public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

}
