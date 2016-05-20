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
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.dynamic.InstitutionAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.LoadMoreListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;


@SuppressWarnings("unused")
public class AddtionFocusOrganizationFragment extends BaseFragment {
    private static final String TAG = AddtionFocusOrganizationFragment.class.getSimpleName();

    @Bind(R.id.load_more_list_view) LoadMoreListView mListView;
    @Bind(R.id.tv_p2p) TextView tvP2P;
    @Bind(R.id.tv_bank) TextView tvBank;
    @Bind(R.id.tv_zhongchou) TextView tvZhongchou;
    private AddtionOrganizationAdapter mAdapter;
    private MyBroadCastReceiver receiver;
    private List<UserInfoEntity> userInfoEntities;
    private ImageView ivadd;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        receiver = new MyBroadCastReceiver();
        IntentFilter filter = new IntentFilter(Constants.ORGANIZATIONACTION);
        getActivity().registerReceiver(receiver, filter);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.addtioninstiation_list_view, container, false);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initView();
        setAdapter();
    }


    private void buttonReset() {
        tvP2P.setSelected(false);
        tvZhongchou.setSelected(false);
        tvBank.setSelected(false);
    }

    private void initView() {
        tvP2P.setSelected(true);
        userId = MyApp.getInstance().getUserId();
        getDataOrganization(1);
    }

    private void setAdapter() {
        mAdapter = new AddtionOrganizationAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setLoadMoreDataListener(new LoadMoreListView.LoadMoreDataListener() {
            @Override
            public void loadMore() {

            }
        });
    }

    @OnItemClick(R.id.load_more_list_view)
    void onItemCilck(View view,int position){
        ivadd = ViewHolder.get(view, R.id.iv_add);
        UserInfoEntity userInfoEntity = userInfoEntities.get(position);
        Intent intent = new Intent(getActivity(), OrganizationHomeActivity.class);
        intent.putExtra("other_user_id", userInfoEntity.getUserId());
        intent.putExtra("position", position);
        ActivityUtil.startActivity(getActivity(), intent);

    }

    @OnClick({R.id.tv_p2p, R.id.tv_zhongchou, R.id.tv_bank})
    public void onClick(View view) {
        buttonReset();
        switch (view.getId()) {
            case R.id.tv_p2p:
                tvP2P.setSelected(true);
                getDataOrganization(1);
                break;
            case R.id.tv_zhongchou:
                tvZhongchou.setSelected(true);
                getDataOrganization(2);
                break;
            case R.id.tv_bank:
                tvBank.setSelected(true);
                getDataOrganization(3);
                break;
        }
    }

    /**机构列表**/
    public void getDataOrganization(int instituteType) {
        InstitutionAttribute.InstitutionGetByTypeReq sec = null;
        String userId = PreferenceUtils.getInstance(getActivity()).readUserInfo().getUser_id();
        switch(instituteType){
            case 1://p2p
                if (TextUtils.isEmpty(userId)){
                    sec = InstitutionAttribute.InstitutionGetByTypeReq.newBuilder().setInstituteType(Common.InstituteType.P2P).setIsafter(false).build();
                }else{
                    sec = InstitutionAttribute.InstitutionGetByTypeReq.newBuilder().setInstituteType(Common.InstituteType.P2P).setUserId(userId).setIsafter(false).build();
                }
                break;
            case 2://zhongchou
                if (TextUtils.isEmpty(userId)){
                    sec = InstitutionAttribute.InstitutionGetByTypeReq.newBuilder().setInstituteType(Common.InstituteType.Zhongchou).setIsafter(false).build();
                }else{
                    sec = InstitutionAttribute.InstitutionGetByTypeReq.newBuilder().setInstituteType(Common.InstituteType.Zhongchou).setUserId(userId).setIsafter(false).build();
                }
                break;
            case 3://直销银行
                if (TextUtils.isEmpty(userId)){
                    sec = InstitutionAttribute.InstitutionGetByTypeReq.newBuilder().setInstituteType(Common.InstituteType.Zxyh).setIsafter(false).build();
                }else{
                    sec = InstitutionAttribute.InstitutionGetByTypeReq.newBuilder().setInstituteType(Common.InstituteType.Zxyh).setUserId(userId).setIsafter(false).build();
                }
                break;
        }

        TiangongClient.instance().send("chronos", "institution_get_by_type", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final InstitutionAttribute.InstitutionGetByTypeResponse response = InstitutionAttribute.InstitutionGetByTypeResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getErrorno() == 0) {
                                userInfoEntities = ModelParseUtil.organizationParse(response.getInstituteUserWithRelList());
                                mAdapter.setData(userInfoEntities);
//                                mAdapter.refresh(userInfoEntities);
                                mListView.loadComplete();
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void updateView(int position,boolean isFocus){
        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int lastVisiblePosition = mListView.getLastVisiblePosition();
        if(position>=firstVisiblePosition && position<=lastVisiblePosition){
            View view = mListView.getChildAt(position - firstVisiblePosition);
            ivadd = ViewHolder.get(view, R.id.iv_add);
            if (isFocus) {
                ToastUtil.showToastText("关注成功");
                ivadd.setImageResource(R.drawable.attentioned);
            } else {
                ToastUtil.showToastText("取消关注");
                ivadd.setImageResource(R.drawable.attention);
            }
        }
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
            updateView(intent.getIntExtra("position",0), intent.getBooleanExtra("isFocus", false));
        }
    }
}
