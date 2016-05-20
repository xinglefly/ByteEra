package com.zhicai.byteera.activity.traincamp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.MainActivity;
import com.zhicai.byteera.activity.bean.AchievementTask;
import com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord.CoinStoreActivity;
import com.zhicai.byteera.activity.event.DayTaskEvent;
import com.zhicai.byteera.activity.myhome.activity.ModifyUserInfoActivity;
import com.zhicai.byteera.activity.traincamp.adapter.AchieveAdapter;
import com.zhicai.byteera.activity.traincamp.view.AwardWebView;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.LoadingDialogShow;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.SDLog;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.service.train_camp.AchieveReward;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;


@SuppressWarnings("unused")
public class AchievementPraiseFragment extends BaseFragment {

    private static final String TAG = AchievementPraiseFragment.class.getSimpleName();

    @Bind(R.id.lv_daytask) ListView lv_daytask;
    private TextView tv_achieve_action;
    protected LoadingDialogShow dialog = null;
    private String userId = "";
    private AchieveReward.AchieveLevel task;
    private AchieveAdapter achieveAdapter;
    private List<AchievementTask> achieveLists;

    private static final int MSG_ACHIEVEDATA_SUCC = 0;
    private static final int MSG_ACHIEVEDATA_FAIL = 1;
    private static final int MSG_COIN_SUCC = 2;
    private static final int MSG_COIN_FAIL = 3;
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ACHIEVEDATA_SUCC:
                    dialog.dismiss();
                    achieveLists = (List<AchievementTask>)msg.obj;
                    achieveAdapter = new AchieveAdapter(getActivity(), achieveLists);
                    lv_daytask.setAdapter(achieveAdapter);
                    break;
                case MSG_ACHIEVEDATA_FAIL:
                    dialog.setResultStatusDrawable(true, "服务器异常");
                    break;
                case MSG_COIN_SUCC:
                    tv_achieve_action.setText("已领取");
                    tv_achieve_action.setBackgroundResource(R.drawable.textview_corner_finish);
                    achieveAdapter.notifyDataSetChanged();
                    getAchieveData();
                    sendBroadCast();
                    break;
                case MSG_COIN_FAIL:
                    dialog.dismiss();
                    tv_achieve_action.setText("领取奖励");
                    tv_achieve_action.setBackgroundResource(R.drawable.textview_corner_get);
                    break;
            }
        }
    };




    @OnItemClick(R.id.lv_daytask)
    public void itemClickListener(View view,int position){
        SDLog.d(TAG, "item position-->" + position);
        //invisible textvalue
        TextView tv_achieveid = ViewHolder.get(view, R.id.tv_achieveid);
        TextView tv_condition_level = ViewHolder.get(view, R.id.tv_condition_level);
        TextView tv_jumpurl = ViewHolder.get(view, R.id.tv_jumpurl);
        TextView tv_jumppoint = ViewHolder.get(view, R.id.tv_jumppoint);

        //visible textvalue
        TextView tv_achieve_coin = ViewHolder.get(view, R.id.tv_achieve_coin);
        tv_achieve_action = ViewHolder.get(view, R.id.tv_achieve_action);
        tv_achieve_action.setFocusable(true);
        tv_achieve_action.setFocusableInTouchMode(true);
        String tv_value = tv_achieve_action.getText().toString();
        String tv_urlvalue = tv_jumpurl.getText().toString();
        String tv_pointvalue = tv_jumppoint.getText().toString();


        final AchievementTask old_achievement = achieveLists.get(position);
        SDLog.d(TAG, "-award->" + position + tv_condition_level.getText().toString() + ",id-->" + tv_achieveid.getText().toString());

        if (tv_value.equals("领取奖励")) {
            getReward(old_achievement);
        } else if (tv_value.equals("去完成")) {
            if (!tv_urlvalue.equals("")) {
                Intent intent = new Intent(getActivity(), AwardWebView.class);
                intent.putExtra("jumpurl", tv_urlvalue);
                ActivityUtil.startActivity(getActivity(), intent);
            } else {
                //TODO 跳转
                Intent intent = new Intent(getActivity(), MainActivity.class);
                switch (Integer.parseInt(tv_pointvalue)) {
                    case 1: //我的->个人资料 My_Information
                        ActivityUtil.startActivity(getActivity(), new Intent(getActivity(), ModifyUserInfoActivity.class));
                        break;
                    case 2: // 我的->理财账本 My_Licaizhangben
                        //TODO 理财账本
                        ToastUtil.showToastText("理财账本未开放");
                        break;
                    case 3: // 我的->账户管理 My_AccountManage
                        //TODO 账户管理暂时未指定
                        ToastUtil.showToastText("账户管理暂时未开放");
                        break;
                    case 4:  // 社区->话题 Shequ_Huati
                        //TODO 社区话题
                        ToastUtil.showToastText("社区话题暂时未开放");
                        break;
                    case 5:  // 社区->动态->理财问问 Shequ_Dongtai_Licaiwenwen
                        //TODO 理财问问
                        ToastUtil.showToastText("理财问问暂时未开放");
                        break;
                    case 6: // 训练营->模拟理财 Xunlianying_Monilicai
                        //TODO 模拟理财
                        ToastUtil.showToastText("模拟理财暂时未开放");
                        break;
                    case 7: // 产品汇->我的关注 Chanpinhui_WatchedMainActivity
//                        Constants.BYTEERA_ACHIEVE_FLAG = true;
//                        intent.putExtra("startActivity", 4);
//                        ActivityUtil.startActivity(getActivity(), intent);
                        break;
                    case 8: // 聊天->邀请好友 Chat_InviteFriend
//                        Constants.BYTEERA_ACHIEVE_FLAG = true;
//                        intent.putExtra("startActivity", 3);
//                        ActivityUtil.startActivity(getActivity(), intent);
                        break;
                    case 9: // 财币任务->商城兑换 CoinTask_Exchange
                        //TODO 商城兑换
                        ActivityUtil.startActivity(getActivity(), new Intent(getActivity(), CoinStoreActivity.class));
                        break;
                }
            }
        } else {
            //TODO 待完成
        }
    }

    private void sendBroadCast() {
        EventBus.getDefault().post(new DayTaskEvent(true));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.day_task_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this,getView());
        userId = PreferenceUtils.getInstance(getActivity()).getUserId();
        dialog = new LoadingDialogShow(getActivity());
        initView();
    }

    private void initView() {
        getAchieveData();
    }


    private void getAchieveData() {
        dialog.show();
        AchieveReward.AchieveReq req;
        if (userId.equals("")){
            req = AchieveReward.AchieveReq.newBuilder().build();
        }else{
            req = AchieveReward.AchieveReq.newBuilder().setUserId(userId).build();
        }
        TiangongClient.instance().send("chronos", "get_achieve_list", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    AchieveReward.AchieveResponse res = AchieveReward.AchieveResponse.parseFrom(buffer);
                    SDLog.d(TAG,"-achieveReward->"+res.toString());
                    if (res.getErrorno() == 0) {
                        if (res.hasCoin()) {MyApp.getInstance().setCoinCnt(res.getCoin());}
                        if (res.hasConLogin()){ PreferenceUtils.getInstance(getActivity()).setLoginDays(res.getConLogin());}
                        if (res.hasRanking()){PreferenceUtils.getInstance(getActivity()).setCoinRank(res.getRanking());}

                        SDLog.d(TAG, "-achieve res->" + res.getCoin() + "," + res.getRanking() + "," + res.getConLogin());

                        //TODO 限时操作
                        SDLog.d(TAG,"achieve list-->"+res.getAchieveList().size());
                        achieveLists = new ArrayList<AchievementTask>();
                        List<AchieveReward.AchieveLevel> achieveList = res.getAchieveList();

                        for (AchieveReward.AchieveLevel task: achieveList){
                            AchievementTask achieve = new AchievementTask(task.getAchieveId(),task.getConditionLevel(), task.getTitle(),
                                    task.getRewardCoin(),task.getExtraInfo(),
                                    task.getTimeLimited(),task.getAchieveDone(),task.getLevelDone());

                            if (task.hasJumpUrl()){
                                achieve.setJump_url(task.getJumpUrl());
                                SDLog.d(TAG,"achieve jumpurl-->"+task.getJumpUrl());
                            }
                            if(task.hasJumpPoint()){
                                achieve.setjump_point(task.getJumpPoint().getNumber());
                                SDLog.d(TAG,"jumppoint-->"+task.getJumpPoint());
                            }
                            SDLog.d(TAG,"-achieve-->"+achieve.toString());
                            achieveLists.add(achieve);
                        }

                        Message msg = mHandler.obtainMessage();
                        msg.what = MSG_ACHIEVEDATA_SUCC;
                        msg.obj = achieveLists;
                        mHandler.sendMessage(msg);
                    } else {
                        mHandler.sendEmptyMessage(MSG_ACHIEVEDATA_FAIL);
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    /**
     * 领取奖励
     */
    private void getReward(final AchievementTask old_achievement) {
        if (DetermineIsLogin()) return;
        dialog.show();
        AchieveReward.GetRewardReq req = AchieveReward.GetRewardReq.newBuilder().setAchieveId(old_achievement.getAchieve_id()).setConditionLevel(old_achievement.getCondition_level()).setUserId(userId).build();
        TiangongClient.instance().send("chronos", "get_reward", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {

                    AchieveReward.GetRewardResponse res = AchieveReward.GetRewardResponse.parseFrom(buffer);
                    SDLog.d(TAG, "reward-->" + res.getErrorno() + res.getErrorDescription()+",coin-->"+res.getCoin()+","+res.getRanking());
                    if (res.getErrorno() == 0) {
                        MyApp.getInstance().setCoinCnt(res.getCoin());
                        PreferenceUtils.getInstance(getActivity()).setCoinRank(res.getRanking());
                        SDLog.d(TAG, "rewrad achieveLevel-->" + res.getNextLevel().toString());


                        SDLog.d("TAG", "-old ach-->" + old_achievement.toString());
                        AchieveReward.AchieveLevel.Builder task = AchieveReward.AchieveLevel.newBuilder();
                        task.setAchieveId(old_achievement.getAchieve_id());
                        task.setConditionLevel(old_achievement.getCondition_level());
                        task.setTitle(old_achievement.getTitle());
                        task.setRewardCoin(old_achievement.getReward_coin());
                        task.setExtraInfo(old_achievement.getExtra_info());
                        task.setAchieveDone(old_achievement.isAchieve_done());
                        task.setLevelDone(old_achievement.isLevel_done());
                        task.setTimeLimited(old_achievement.isTime_limited());
                        if(res.getNextLevel().hasJumpPoint()){
                            task.setJumpPoint(Common.JumpPoint.valueOf(old_achievement.getjump_point()));
                        }
                        task.build();
                        SDLog.d("TAG", "-old-->" + task.toString());

                        task = task.build().toBuilder().mergeFrom(res.getNextLevel());
                        SDLog.d("TAG","-old new-->"+task.getConditionLevel());
                        Message msg = mHandler.obtainMessage();
                        msg.what = MSG_COIN_SUCC;
                        msg.obj = task;
                        mHandler.sendMessage(msg);
                    } else {
                        mHandler.sendEmptyMessage(MSG_COIN_FAIL);
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
