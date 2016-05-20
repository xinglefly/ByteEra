package com.zhicai.byteera.activity.message.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.easemob.util.EMLog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<ZCUser>  implements SectionIndexer{
	private static final String TAG = ContactAdapter.class.getSimpleName();

	List<String> list;
	List<ZCUser> userList;
	List<ZCUser> copyUserList;
	private LayoutInflater layoutInflater;
	private SparseIntArray positionOfSection;
	private SparseIntArray sectionOfPosition;
	private int res;
	private MyFilter myFilter;
	private boolean notiyfyByFilter;

	public ContactAdapter(Context context, int resource, List<ZCUser> objects) {
		super(context, resource, objects);
		this.res = resource;
		this.userList = objects;
		copyUserList = new ArrayList<ZCUser>();
		copyUserList.addAll(objects);
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) { convertView =layoutInflater.inflate(res, parent, false);}

		TextView tvHeader = ViewHolder.get(convertView, R.id.header);
		ImageView avatar = ViewHolder.get(convertView, R.id.avatar);
		TextView nameTextview = ViewHolder.get(convertView, R.id.name);
		TextView unreadMsgView = ViewHolder.get(convertView, R.id.unread_msg_number);

		ZCUser user = getItem(position);
		if(user == null)
			Log.d("ContactAdapter", position + "");

		//用username代替nick显示
		String username = user.getUsername();
		String header = user.getHeader();
		if (position == 0 || header != null && !header.equals(getItem(position - 1).getHeader())) {
			if ("".equals(header)) {
				tvHeader.setVisibility(View.GONE);
			} else {
				tvHeader.setVisibility(View.VISIBLE);
				tvHeader.setText(header);
			}
		} else {
			tvHeader.setVisibility(View.GONE);
		}

		//显示申请与通知item
		if(username.equals(Constants.NEW_FRIENDS_USERNAME)){
			nameTextview.setText(user.getNick());
			avatar.setImageResource(R.drawable.consult_default);
			if(user.getUnreadMsgCount() > 0){
				unreadMsgView.setVisibility(View.VISIBLE);
				unreadMsgView.setText(user.getUnreadMsgCount()+"");
			}else{
				unreadMsgView.setVisibility(View.INVISIBLE);
			}
		}else if(username.equals(Constants.GROUP_USERNAME)){
			//群聊item
			nameTextview.setText(user.getNick());
			avatar.setImageResource(R.drawable.head_group);
		}else{
			ImageLoader.getInstance().displayImage(user.getAvatar(), avatar);
			nameTextview.setText(user.getNick());
			if(unreadMsgView != null)
				unreadMsgView.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	@Override
	public ZCUser getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public int getCount() {
		return super.getCount();
	}

	public int getPositionForSection(int section) {
		return positionOfSection.get(section);
	}

	public int getSectionForPosition(int position) {
		return sectionOfPosition.get(position);
	}

	@Override
	public Object[] getSections() {
		positionOfSection = new SparseIntArray();
		sectionOfPosition = new SparseIntArray();
		int count = getCount();
		list = new ArrayList<String>();
		list.add(getContext().getString(R.string.search_header));
		positionOfSection.put(0, 0);
		sectionOfPosition.put(0, 0);
		for (int i = 1; i < count; i++) {

			String letter = getItem(i).getHeader();
			System.err.println("contactadapter getsection getHeader:" + letter + " name:" + getItem(i).getUsername());
			int section = list.size() - 1;
			if (list.get(section) != null && !list.get(section).equals(letter)) {
				list.add(letter);
				section++;
				positionOfSection.put(section, i);
			}
			sectionOfPosition.put(i, section);
		}
		return list.toArray(new String[list.size()]);
	}

	@Override
	public Filter getFilter() {
		if(myFilter==null){
			myFilter = new MyFilter(userList);
		}
		return myFilter;
	}

	private class  MyFilter extends Filter{
		List<ZCUser> mOriginalList = null;

		public MyFilter(List<ZCUser> myList) {
			this.mOriginalList = myList;
		}

		@Override
		protected synchronized FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();
			if(mOriginalList==null){
				mOriginalList = new ArrayList<ZCUser>();
			}
			EMLog.d(TAG, "contacts original size: " + mOriginalList.size());
			EMLog.d(TAG, "contacts copy size: " + copyUserList.size());

			if(prefix==null || prefix.length()==0){
				results.values = copyUserList;
				results.count = copyUserList.size();
			}else{
				String prefixString = prefix.toString();
				final int count = mOriginalList.size();
				final ArrayList<ZCUser> newValues = new ArrayList<ZCUser>();
				for(int i=0;i<count;i++){
					final ZCUser user = mOriginalList.get(i);
					String username = user.getUsername();

					if(username.startsWith(prefixString)){
						newValues.add(user);
					}
					else{
						final String[] words = username.split(" ");
						final int wordCount = words.length;

						// Start at index 0, in case valueText starts with space(s)
						for (int k = 0; k < wordCount; k++) {
							if (words[k].startsWith(prefixString)) {
								newValues.add(user);
								break;
							}
						}
					}
				}
				results.values=newValues;
				results.count=newValues.size();
			}
			EMLog.d(TAG, "contacts filter results size: " + results.count);
			return results;
		}

		@Override
		protected synchronized void publishResults(CharSequence constraint,
												   FilterResults results) {
			userList.clear();
			userList.addAll((List<ZCUser>)results.values);
			EMLog.d(TAG, "publish contacts filter results size: " + results.count);
			if (results.count > 0) {
				notiyfyByFilter = true;
				notifyDataSetChanged();
				notiyfyByFilter = false;
			} else {
				notifyDataSetInvalidated();
			}
		}
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		if(!notiyfyByFilter){
			copyUserList.clear();
			copyUserList.addAll(userList);
		}
	}
	public void updateListView(List<ZCUser> list) {
		this.userList = list;
		notifyDataSetChanged();
	}

}
