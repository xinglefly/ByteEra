package com.zhicai.byteera.activity.myhome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.community.dynamic.activity.OrganizationHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.UserHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.community.topic.entity.InstitutionUserEntity;
import com.zhicai.byteera.activity.myhome.adapter.JoinOrganizationListAdapter;
import com.zhicai.byteera.activity.myhome.adapter.MyFriendAdapter;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.LoadMoreListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/** Created by lieeber on 15/8/22. */
public class FocusOrganizationFragment extends BaseFragment {

    @Bind(R.id.load_more_list_view) LoadMoreListView mListView;
    private List<InstitutionUserEntity> institutionUserEntities;
    private JoinOrganizationListAdapter mAdapter;


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.load_more_listview, container, false);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initView();
    }

    private void initView() {
        userId = PreferenceUtils.getInstance(getActivity()).getUserId();
        mAdapter = new JoinOrganizationListAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        getJoinGroupList();
    }

    @OnItemClick(R.id.load_more_list_view)
    public void onItemCilck(int position){ //跳转机构主页
        InstitutionUserEntity institutionEntity = institutionUserEntities.get(position);
        Intent intent = new Intent(getActivity(), OrganizationHomeActivity.class);
        intent.putExtra("other_user_id", institutionEntity.getUserId());
        intent.putExtra("position", position);
        ActivityUtil.startActivity(getActivity(), intent);

    }


    /**获取已加入理财机构的列表**/
    public void getJoinGroupList() {
        UserAttribute.GetUserWatchInfoReq sec = UserAttribute.GetUserWatchInfoReq.newBuilder().setUserId(MyApp.getInstance().getUserId()).build();
        TiangongClient.instance().send("chronos", "get_user_watch_institutes", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final UserAttribute.GetUserWatchInstitutesResponse response = UserAttribute.GetUserWatchInstitutesResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getErrorno() == 0) {
                                institutionUserEntities = ModelParseUtil.InstitutionUserEntityParse(response.getInstitutesList());
                                mAdapter.updateListView(institutionUserEntities);
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
