package com.zhicai.byteera.activity.community.topic.actiivty;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.community.topic.adapter.SelectInstitutionAdapter;
import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.community.topic.entity.InstitutionUserEntity;
import com.zhicai.byteera.activity.community.topic.presenter.InstitutionListPre;
import com.zhicai.byteera.activity.community.topic.viewinterface.InstitutionListIV;

import java.util.List;

/** Created by bing on 2015/6/24. */
public class InsAllFragment extends BaseFragment implements InstitutionListIV {
    private static final String TAG = InsAllFragment.class.getSimpleName();

    private InstitutionListPre institutionListPre;
    private SelectInstitutionAdapter mAdapter;
    List<FinancingCompanyEntity> companyLists;


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView mListView = (ListView) inflater.inflate(R.layout.list_view, container, false);
        institutionListPre = new InstitutionListPre(this);
        initListView(mListView);
        return mListView;
    }



    private void initListView(ListView mListView) {
        mListView.setDivider(null);
        mAdapter = new SelectInstitutionAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        institutionListPre.getData1(getArguments().getInt("company_type"));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //返回并把选中的机构带回到上一个界面
                institutionListPre.finish(position);
            }
        });
    }

    @Override public Activity getContext() {
        return getActivity();
    }

    @Override
    public void setData(List<InstitutionUserEntity> institutionUserEntities) {
//        mAdapter.setData(institutionUserEntities);
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setData1(List<FinancingCompanyEntity> companyEntities) {
        mAdapter.setData(companyEntities);
        mAdapter.notifyDataSetChanged();
    }


}
