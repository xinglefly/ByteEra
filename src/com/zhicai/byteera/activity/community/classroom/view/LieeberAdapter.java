package com.zhicai.byteera.activity.community.classroom.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhicai.byteera.activity.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/4/16. */
public abstract class
        LieeberAdapter<Data> extends BaseAdapter  {
    protected List<Data> mList = new ArrayList<>();

    public List<BaseHolder> mDisplayedHoders;//用于记录所有显示的holder
    protected BaseFragment mFragment;

    protected Context mContext;

    public LieeberAdapter(Context mContext, BaseFragment mFragment) {
        this(mContext, mFragment, new ArrayList<Data>());
    }
    public LieeberAdapter(Context context) {
        this(context, null, new ArrayList<Data>());
    }

    public LieeberAdapter(Context mContext, BaseFragment mFragment, List<Data> mList) {
        this.mFragment = mFragment;
        this.mList.addAll(mList);
        this.mContext = mContext;
    }

    public void addAllItem(List<Data> mList) {
        this.mList.addAll(mList);
    }

    public void removeAllViews() {
        this.mList.clear();
    }

    public void addItem(Data data) {
        this.mList.add(data);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Data getItem(int position) {
        return mList.get(position);
    }

    public Data getLastItem() {
        if (mList.size() > 0) {
            return mList.get(mList.size() - 1);
        } else {
            return null;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder<Data> holder;
        if (convertView != null && convertView.getTag() instanceof BaseHolder) {
            holder = (BaseHolder<Data>) convertView.getTag();
        } else {
            holder = checkType(position);
        }
        holder.setPosition(position);
        holder.setData(mList.get(position));
        getHolder(holder, position);
        return holder.getRootView();
    }

    public BaseHolder<Data> getHolder(BaseHolder<Data> holder, int position) {
        return holder;
    }

    protected abstract BaseHolder checkType(int position);

    @Override
    public int getViewTypeCount() {
        return getItemTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemType(position);
    }

    protected abstract int getItemType(int position);


    public abstract int getItemTypeCount();


    public void removeItem(Data data) {
        if (mList.contains(data)) {
            mList.remove(data);
        }
    }

    public void freshItem(List<Data> list) {
        mList.clear();
        mList.addAll(list);
    }


    public void removeItemAtPosition(int position) {
        mList.remove(position);
    }
}
