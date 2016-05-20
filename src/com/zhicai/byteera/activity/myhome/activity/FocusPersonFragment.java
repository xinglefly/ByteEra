package com.zhicai.byteera.activity.myhome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lidroid.xutils.ViewUtils;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.community.dynamic.activity.GroupHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.OrganizationHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.UserHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.activity.community.group.GroupEntity;
import com.zhicai.byteera.activity.myhome.adapter.JoinMineListAdapter;
import com.zhicai.byteera.activity.myhome.adapter.MyFriendAdapter;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.LoadMoreListView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/** Created by lieeber on 15/8/22. */
public class FocusPersonFragment extends BaseFragment {

    @Bind(R.id.load_more_list_view) LoadMoreListView mListView;
    private List<UserInfoEntity> userInfoEntities;
    private JoinMineListAdapter mAdapter;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.load_more_listview, container, false);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this,getView());
        initView();
    }

    private void initView() {
        userId = PreferenceUtils.getInstance(getActivity()).getUserId();
        mAdapter = new JoinMineListAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        getJoinPersonList();
    }

    @OnItemClick(R.id.load_more_list_view)
    public void onItemClick(int position){
        UserInfoEntity userInfoEntity = userInfoEntities.get(position);
        Intent intent = new Intent(getActivity(), UserHomeActivity.class);
        intent.putExtra("other_user_id", userInfoEntity.getUserId());
        ActivityUtil.startActivity(getActivity(), intent);
    }


    public void getJoinPersonList() {
        UserAttribute.GetUserWatchInfoReq sec = UserAttribute.GetUserWatchInfoReq.newBuilder().setUserId(userId).build();
        TiangongClient.instance().send("chronos", "get_user_watch_users", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final UserAttribute.GetUserWatchUsersResponse response = UserAttribute.GetUserWatchUsersResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getErrorno() == 0) {
                                userInfoEntities = ModelParseUtil.UserInfoEntityParse(response.getUsersList());
                                mAdapter.updateListView(userInfoEntities);
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
