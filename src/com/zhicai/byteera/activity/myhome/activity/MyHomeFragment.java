package com.zhicai.byteera.activity.myhome.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord.CoinStoreActivity;
import com.zhicai.byteera.activity.community.dynamic.view.MyHomeHeadView;
import com.zhicai.byteera.activity.community.dynamic.view.MyHomeItemView;
import com.zhicai.byteera.activity.community.dynamic.view.UserHomeTitleView;
import com.zhicai.byteera.activity.event.DayTaskEvent;
import com.zhicai.byteera.activity.initialize.SettingActivity;
import com.zhicai.byteera.activity.traincamp.DailyTaskActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/** Created by bing on 2015/5/3. */
@SuppressWarnings("unused")
public class MyHomeFragment extends BaseFragment {
    @Bind(R.id.title) UserHomeTitleView mTitleView;
    @Bind(R.id.head_view) MyHomeHeadView mHeadView;
    @Bind(R.id.my_friends) MyHomeItemView mFriends;
    @Bind(R.id.my_dontai) MyHomeItemView mDongtai;
    @Bind(R.id.my_soucang) MyHomeItemView mSouCang;
    @Bind(R.id.my_coinshop) MyHomeItemView mCoinShop;
    @Bind(R.id.my_cointask) MyHomeItemView mCoinTask;
    @Bind(R.id.my_invitecode) MyHomeItemView mInviteCode;
    String invitationCode;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_home_activity, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        processLogic();
    }



    protected void processLogic() {
        mTitleView.getRightImg().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.startActivity(getActivity(), new Intent(getActivity(), SettingActivity.class));
            }
        });

        mHeadView.setIconClickListener(new MyHomeHeadView.IconClickListener() {
            @Override
            public void iconClick() {
                if (!DetermineIsLogin())
                    ActivityUtil.startActivity(getActivity(), new Intent(getActivity(), ModifyUserInfoActivity.class));
            }
        });
    }

    private void intentToTixianActivity() {
        Intent intent = new Intent(getActivity(), MyTixianActivity.class);
        ActivityUtil.startActivity(getActivity(), intent);
    }


    @OnClick({R.id.ll_ziliao, R.id.ll_fensi,R.id.ll_guanzhu, R.id.my_soucang, R.id.my_dontai,R.id.my_friends,R.id.my_coinshop,R.id.my_cointask,R.id.my_invitecode})
    public void clickListener(View view) {
        if (DetermineIsLogin()) return;
        switch (view.getId()) {
            case R.id.ll_ziliao:
                ActivityUtil.startActivity(getActivity(), new Intent(getActivity(), ModifyUserInfoActivity.class));
                break;
            case R.id.ll_fensi:
                ActivityUtil.startActivity(getActivity(), new Intent(getActivity(), MyFansActivity.class));
                break;
            case R.id.ll_guanzhu:
                ActivityUtil.startActivity(getActivity(), new Intent(getActivity(), MyFocusActivity.class));
                break;
            case R.id.my_soucang:
                ActivityUtil.startActivity(getActivity(), new Intent(getActivity(), MyShoucangActivity.class));
                break;
            case R.id.my_dontai:
                ActivityUtil.startActivity(getActivity(), new Intent(getActivity(), MyDynamicActivity.class));
                break;
            case R.id.my_friends:
                ActivityUtil.startActivity(getActivity(), new Intent(getActivity(), MyFriendsActivity.class));
                break;
            case R.id.my_coinshop:
                ActivityUtil.startActivity(getActivity(),new Intent(getActivity(), CoinStoreActivity.class));
                break;
            case R.id.my_cointask:
                ActivityUtil.startActivity(getActivity(),new Intent(getActivity(), DailyTaskActivity.class));
                break;
            case R.id.my_invitecode:
                copyInvitationCode();
                break;
        }
    }

    private void copyInvitationCode() {
        if (!TextUtils.isEmpty(invitationCode)){
            ToastUtil.showToastText("复制成功!");
            ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, invitationCode);
            clipboardManager.setPrimaryClip(clipData);
        }
    }


    @Override public void onResume() {
        super.onResume();
        ZCUser userInfo = PreferenceUtils.getInstance(getActivity()).readUserInfo();
        if (!TextUtils.isEmpty(userInfo.getUser_id())) {
            mHeadView.setName(userInfo.getNickname());
            mHeadView.setIcon(userInfo.getHeader());
            mFriends.setContent(String.valueOf(userInfo.getFansCount()));
            mSouCang.setContent(String.valueOf(userInfo.getCollectCnt()));
            mDongtai.setContent(String.valueOf(userInfo.getDongtaiCnt()));
            mFriends.setContent(String.valueOf(userInfo.getFriendCnt()));
            updateUI();
            invitationCode = userInfo.getInvitationCode();
            mInviteCode.setContent(invitationCode);
        } else {
            mHeadView.setName("登录/注册");
        }
    }

    public void onEventMainThread(DayTaskEvent event) {
        Log.d("MyHomeFragment", "接收onEventMainThread信息 DayTaskEvent-->" + event.isRefresh());
        updateUI();
    }

    void updateUI() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCoinShop.setContent(MyApp.getInstance().getCoinCnt() + "");
                mCoinTask.setContent(MyApp.getInstance().getCoinCnt() + "");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }


}
