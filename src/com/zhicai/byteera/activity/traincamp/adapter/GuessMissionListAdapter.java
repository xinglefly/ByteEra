package com.zhicai.byteera.activity.traincamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.bean.GuessSmallMissionInfo;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.List;

@SuppressWarnings("unused")
public class GuessMissionListAdapter extends BaseAdapter{

	public static final String TAG = GuessMissionListAdapter.class.getSimpleName();

	private Context mContext;
	private List<GuessSmallMissionInfo> list = null;


	public GuessMissionListAdapter(Context context, List<GuessSmallMissionInfo> list) {
		this.list = list;
		this.mContext = context;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_guess_mission_item, parent, false);
		}

		RelativeLayout rel_unlock = ViewHolder.get(convertView, R.id.rel_unlock); //解锁
		RelativeLayout rel_lock = ViewHolder.get(convertView, R.id.rel_lock);	  //加锁
		ImageView img_locked = ViewHolder.get(convertView, R.id.img_locked);
		TextView tv_position = ViewHolder.get(convertView,R.id.tv_position);
		//TODO 记录当前的状态
		TextView tv_missionstatus = ViewHolder.get(convertView,R.id.tv_missionstatus);

		GuessSmallMissionInfo guessEntity = list.get(position);
		tv_position.setText(guessEntity.getGridviewid()+"");
		tv_missionstatus.setText(guessEntity.getMissionstatus() + "");

		//TODO 后期要加数据库对其进行判断才行
		switch(guessEntity.getMissionstatus()){
			case Constants.MISSION_LOCKED:  //0
				rel_lock.setVisibility(View.VISIBLE);
				break;
			case Constants.MISSION_PASSED:  //1
				rel_unlock.setVisibility(View.VISIBLE);
				break;
			case Constants.MISSION_FAILED:  //2
				//TODO 保存数据库
				break;
		}
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
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}

