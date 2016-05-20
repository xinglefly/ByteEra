package com.zhicai.byteera.activity.community.topic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.commonutil.StringUtil;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/6/24. */
public class SelectInstitutionAdapter extends BaseAdapter implements SectionIndexer {
    private Context mContext;
    private List<FinancingCompanyEntity> mList = new ArrayList<>();

    public SelectInstitutionAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<FinancingCompanyEntity> list) {
        this.mList = list;
    }

    @Override public int getCount() {
        return mList.size();
    }

    @Override public Object getItem(int position) {
        return null;
    }

    @Override public long getItemId(int position) {
        return 0;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.institution_item, null);
        }

        TextView group_title = ViewHolder.get(convertView, R.id.group_title);
        TextView tvName = ViewHolder.get(convertView, R.id.tv_name);

        tvName.setText(mList.get(position).getCompany_name());


        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        /*if (position==getCount()){
            if(Character.isDigit(mList.get(position).getCompany_name().charAt(0))) {
                group_title.setVisibility(View.VISIBLE);
                group_title.setText("#");
            }
        }else */
        if (position == getPositionForSection(section)) {
            group_title.setVisibility(View.VISIBLE);
            group_title.setText(StringUtil.getPinYinHeadChar(mList.get(position).getCompany_name()).substring(0, 1));
        } else {
            group_title.setVisibility(View.GONE);
        }

        return convertView;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return StringUtil.getPinYinHeadChar(mList.get(position).getCompany_name()).charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            char firstChar = StringUtil.getPinYinHeadChar(mList.get(i).getCompany_name()).charAt(0);
//            if (Character.isDigit(firstChar)){
//                return getCount();
//            }
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}
