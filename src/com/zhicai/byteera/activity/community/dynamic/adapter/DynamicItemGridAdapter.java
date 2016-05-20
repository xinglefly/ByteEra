package com.zhicai.byteera.activity.community.dynamic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.entity.ImgEntity;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bing on 2015/6/4.
 */
public class DynamicItemGridAdapter extends BaseAdapter {
    private List<ImgEntity> imgList;
    private Context mContext;
    private int numColumns;


    public DynamicItemGridAdapter(Context context) {
       this.imgList = new ArrayList<>();
        mContext = context;

    }

    public void setData(List<ImgEntity> list, int numColumns) {
        this.imgList = list;
        this.numColumns = numColumns;
    }

    @Override public int getItemViewType(int position) {
        return AdapterView.ITEM_VIEW_TYPE_IGNORE;
    }

    @Override public int getCount() {
        return imgList.size();
    }

    @Override public Object getItem(int arg0) {
        return imgList.get(arg0);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.img_view, parent, false);
        }
        ImageView img = ViewHolder.get(convertView, R.id.img_content);
        ImageLoader.getInstance().displayImage(imgList.get(position).getImgUrl(), img);
        if (numColumns == 1) {
            convertView.setLayoutParams(new GridView.LayoutParams(UIUtils.dip2px(mContext, 150), UIUtils.dip2px(mContext, 150)));
        } else if (numColumns <= 3) {
            convertView.setLayoutParams(new GridView.LayoutParams((UIUtils.getScreenWidth(mContext) - UIUtils.dip2px(mContext, 60)) / numColumns, (UIUtils.getScreenWidth(mContext) - UIUtils.dip2px
                    (mContext, 60)) / numColumns));
        }
        return convertView;
    }
}
