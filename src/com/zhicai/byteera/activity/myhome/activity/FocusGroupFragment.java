package com.zhicai.byteera.activity.myhome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.community.dynamic.activity.GroupHomeActivity;
import com.zhicai.byteera.activity.myhome.adapter.JoinGroupListAdapter;
import com.zhicai.byteera.activity.community.group.GroupEntity;
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

/**
 * Created by lieeber on 15/8/22.
 */
public class FocusGroupFragment extends BaseFragment {

    @Bind(R.id.load_more_list_view) LoadMoreListView mListView;
    private JoinGroupListAdapter mAdapter;
    private List<GroupEntity> groupList;

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
        mAdapter = new JoinGroupListAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        getJoinGroupList();
    }

    @OnItemClick(R.id.load_more_list_view)
    public void onItemClick(int position){
        GroupEntity groupEntity = groupList.get(position);
        Intent intent = new Intent(getActivity(), GroupHomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("group", groupEntity);
        intent.putExtras(bundle);
        intent.putExtra("position_des",true);
        ActivityUtil.startActivity(getActivity(), intent);
    }


    /**获取已加入理财圈小组的列表**/
    public void getJoinGroupList() {
        UserAttribute.GetUserWatchInfoReq sec = UserAttribute.GetUserWatchInfoReq.newBuilder().setUserId(MyApp.getInstance().getUserId()).build();
        TiangongClient.instance().send("chronos", "get_user_watch_groups", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final UserAttribute.GetUserWatchGroupsResponse response = UserAttribute.GetUserWatchGroupsResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getErrorno() == 0) {
                                groupList = ModelParseUtil.JoinGroupListParse(response.getGroupsList());
                                mAdapter.updateListView(groupList);
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
