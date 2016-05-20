package com.zhicai.byteera.activity.shouyi.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.shouyi.entity.CompaniesIdList;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CompaniesAdapter extends BaseAdapter {
    private List<FinancingCompanyEntity> companyEntityList;
    private Activity mContext;
    private List<CompaniesIdList> companiesIdLists = null;

    public CompaniesAdapter(Activity mContext) {
        this.mContext = mContext;
        this.companyEntityList = new ArrayList<>();
    }

    public void setData(List<FinancingCompanyEntity> mList) {
        this.companyEntityList = mList;
        notifyDataSetChanged();
    }


    @Override public int getCount() {
        return companyEntityList.size();
    }

    @Override public Object getItem(int position) {
        return companyEntityList.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {convertView = LayoutInflater.from(mContext).inflate(R.layout.companies_item, null);}

        FinancingCompanyEntity companyEntity = companyEntityList.get(position);
        ImageView ivCk = ViewHolder.get(convertView, R.id.iv_ck);
        ImageView ivAvatar = ViewHolder.get(convertView, R.id.iv_avatar);
        TextView tvName = ViewHolder.get(convertView, R.id.tv_name);

        ImageLoader.getInstance().displayImage(companyEntity.getCompany_image(), ivAvatar);
        tvName.setText(companyEntity.getCompany_name());
        ivCk.setBackgroundResource(companyEntity.isChecked() ? R.drawable.company_ck : R.drawable.company_no);

        return convertView;
    }



}
