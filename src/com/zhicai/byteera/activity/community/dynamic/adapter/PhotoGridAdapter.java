package com.zhicai.byteera.activity.community.dynamic.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.Bimp;
import com.zhicai.byteera.commonutil.Constants;

/**
 * Created by bing on 2015/5/3.
 */
public class PhotoGridAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private int selectedPosition = -1;
    private boolean shape;

    public boolean isShape() {
        return shape;
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    public PhotoGridAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);

    }

    public void update() {
        Intent intent = new Intent();
        intent.setAction("update_photo");
        mContext.sendBroadcast(intent);
    }

    public int getCount() {
        if (Bimp.tempSelectBitmap.size() == Constants.PHOTO_NUM) {
            return Constants.PHOTO_NUM;
        }
        return (Bimp.tempSelectBitmap.size() + 1);
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == Bimp.tempSelectBitmap.size()) {
            holder.image.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_addpic_unfocused));
            if (position == Constants.PHOTO_NUM) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }


}
