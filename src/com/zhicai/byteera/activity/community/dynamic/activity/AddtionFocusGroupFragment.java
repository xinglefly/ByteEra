package com.zhicai.byteera.activity.community.dynamic.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.community.dynamic.adapter.AddtionPersonAdapter;
import com.zhicai.byteera.activity.community.group.GroupEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.service.dynamic.DynamicGroupV2;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.LoadMoreListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/** Created by lieeber on 15/8/26. */
public class AddtionFocusGroupFragment extends BaseFragment {
    private static final String TAG = AddtionFocusGroupFragment.class.getSimpleName();

    @Bind(R.id.load_more_list_view) LoadMoreListView mListView;
    private AddtionPersonAdapter mAdapter;
    private List<GroupEntity> groupList;
    private MyBroadCastReceiver receiver = null;
    private ImageView ivadd;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        receiver = new MyBroadCastReceiver();
        IntentFilter filter = new IntentFilter(Constants.GROUPACTION);
        getActivity().registerReceiver(receiver, filter);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.load_more_listview, container, false);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initView();
    }


    private void initView() {
        mAdapter = new AddtionPersonAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        getLicaiquan_grouplist();
    }


    @OnItemClick(R.id.load_more_list_view)
    public void onItemClick(int position){
        GroupEntity groupEntity = groupList.get(position);
        Intent intent = new Intent(getActivity(), GroupHomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("group", groupEntity);
        intent.putExtras(bundle);
        ActivityUtil.startActivity(getActivity(), intent);
    }


    /**获取所有理财圈小组的列表, 包含是否加入的标识**/
    public void getLicaiquan_grouplist() {
        DynamicGroupV2.LicaiquanGroupGetAllReq sec = null;
        if (TextUtils.isEmpty(MyApp.getInstance().getUserId())){
            sec = DynamicGroupV2.LicaiquanGroupGetAllReq.newBuilder().build();
        }else{
            sec = DynamicGroupV2.LicaiquanGroupGetAllReq.newBuilder().setUserId(MyApp.getInstance().getUserId()).build();
        }
        TiangongClient.instance().send("chronos", "licaiquan_group_get_all", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final DynamicGroupV2.LicaiquanGroupGetAllResponse response = DynamicGroupV2.LicaiquanGroupGetAllResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getErrorno() == 0) {
                                groupList = ModelParseUtil.GroupEntityParse(response.getLicaiquanGroupsWithMemList());
                                mAdapter.setData(groupList);
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(null!=receiver)
            getActivity().unregisterReceiver(receiver);
    }

    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getLicaiquan_grouplist();
        }
    }
}
