package com.zhicai.byteera.activity.traincamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.bean.AchievementTask;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.List;

@SuppressWarnings("unused")
public class AchieveAdapter extends BaseAdapter {
	private static final String TAG = AchieveAdapter.class.getSimpleName();

	protected Context mContext;
	protected List<AchievementTask> achieveLists = null;
	private TextView tv_achieve_action;

	public AchieveAdapter(Context context, List<AchievementTask> achieveLists) {
		this.achieveLists = achieveLists;
		this.mContext = context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.achieve_item, parent, false);
		}
		TextView tv_achieve_day = ViewHolder.get(convertView,R.id.tv_achieve_day);
		TextView tv_achieve_coin = ViewHolder.get(convertView,R.id.tv_achieve_coin);
		TextView tv_achieve_explain = ViewHolder.get(convertView,R.id.tv_achieve_explain);
		tv_achieve_action = ViewHolder.get(convertView,R.id.tv_achieve_action);

		//invisible textView
		TextView tv_achieveid = ViewHolder.get(convertView, R.id.tv_achieveid);
		TextView tv_condition_level = ViewHolder.get(convertView, R.id.tv_condition_level);
		TextView tv_jumpurl = ViewHolder.get(convertView, R.id.tv_jumpurl);
		TextView tv_jumppoint = ViewHolder.get(convertView, R.id.tv_jumppoint);

		//getlist position
		AchievementTask task = achieveLists.get(position);
		tv_achieve_day.setText(task.getTitle());
		tv_achieve_coin.setText(task.getReward_coin() + "");
		tv_achieve_explain.setText(task.getExtra_info());

		tv_achieveid.setText(task.getAchieve_id());
		tv_condition_level.setText(task.getCondition_level()+"");
		tv_jumpurl.setText(task.getJump_url());
		tv_jumppoint.setText(task.getjump_point() + "");

		//TODO 业务逻辑判断
			if(task.isAchieve_done()){
				//已完成
				setTextReceived();
			}else{
				if(task.isLevel_done()){
					//领取
					setTextGet();
				}else if(task.getJump_url()!=null || task.getjump_point()!=0){
					//去完成
					setTextGoFinish();
				}else{
					//待完成
					setTextUnDone();
				}
			}

		return convertView;
	}

	/**
	 * 待完成
	 */
	private void setTextUnDone() {
		tv_achieve_action.setText("待完成");
//		tv_achieve_action.setBackgroundColor(Color.parseColor("#D53D3D"));
        tv_achieve_action.setBackgroundResource(R.drawable.textview_corner);
	}

	/**
	 * 去完成
	 */
	private void setTextGoFinish() {
		tv_achieve_action.setText("去完成");
        tv_achieve_action.setBackgroundResource(R.drawable.textview_corner);
	}

	/**
	 * 领取奖励
	 */
	private void setTextGet() {
		tv_achieve_action.setText("领取奖励");
        tv_achieve_action.setBackgroundResource(R.drawable.textview_corner_get);
	}


	/**
	 * 已领取
	 */
	private void setTextReceived() {
		tv_achieve_action.setText("已领取");
        tv_achieve_action.setBackgroundResource(R.drawable.textview_corner_finish);
	}



	@Override
	public int getItemViewType(int position) {
		return AdapterView.ITEM_VIEW_TYPE_IGNORE;
	}

	@Override
	public int getCount() {
		return achieveLists.size();
	}

	@Override
	public Object getItem(int arg0) {
		return achieveLists.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean areAllItemsEnabled() {   //表示adapter下所有item是否可以点击
		return true;
	}


}
