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

import com.lidroid.xutils.BitmapUtils;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.bean.GuessEntity;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.List;

@SuppressWarnings("unused")
public class GuessListAdapter extends BaseAdapter{

	public static final String TAG = GuessListAdapter.class.getSimpleName();

	protected Context mContext;
	protected List<GuessEntity> list = null;
	protected BitmapUtils bitmapUtils;


	public GuessListAdapter(Context mContext, List<GuessEntity> list) {
		this.list = list;
		this.mContext = mContext;
		bitmapUtils = new BitmapUtils(mContext);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.guesspic_item, parent, false);
		}

		RelativeLayout rel_guesspic = ViewHolder.get(convertView, R.id.rel_guesspic);
		ImageView img_num = ViewHolder.get(convertView, R.id.img_num);
		TextView tv_levelname = ViewHolder.get(convertView,R.id.tv_levelname);
		TextView tv_guessid = ViewHolder.get(convertView,R.id.tv_guessid);
		TextView tv_guessurl = ViewHolder.get(convertView,R.id.tv_guessurl);

		GuessEntity guessEntity = list.get(position);
		tv_levelname.setText(guessEntity.gettv_explain());
		bitmapUtils.display(rel_guesspic, guessEntity.getBackgroundimg());
		bitmapUtils.display(img_num, guessEntity.getcontent());
		tv_guessurl.setText(guessEntity.getDownloadurl());

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
