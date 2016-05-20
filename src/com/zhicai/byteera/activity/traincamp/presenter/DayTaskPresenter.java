package com.zhicai.byteera.activity.traincamp.presenter;

import android.text.TextUtils;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.bean.DayTask;
import com.zhicai.byteera.activity.traincamp.DailyTaskActivity;
import com.zhicai.byteera.commonutil.LogUtil;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.service.train_camp.AchieveReward;

import java.util.ArrayList;
import java.util.List;


public class DayTaskPresenter {

    DailyTaskActivity activity;

    public DayTaskPresenter(DailyTaskActivity activity) {
        this.activity = activity;
    }

    public void getDailyTaskList() {
        AchieveReward.GetDailyTaskListReq  req = null;
        if (!TextUtils.isEmpty(MyApp.getInstance().getUserId())) req = AchieveReward.GetDailyTaskListReq.newBuilder().setUserId(MyApp.getInstance().getUserId()).build();
        else req = AchieveReward.GetDailyTaskListReq.newBuilder().build();

        TiangongClient.instance().send("chronos", "get_daily_task_list", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final AchieveReward.GetDailyTaskListResponse res = AchieveReward.GetDailyTaskListResponse.parseFrom(buffer);
                    LogUtil.d("dailyList : %s", res.toString());
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            List<DayTask> dayTasks = new ArrayList();
                            if (res.getErrorno() == 0) {
                                for (AchieveReward.DailyTask tasks : res.getTasksList())
                                    dayTasks.add(new DayTask(tasks.getId(),tasks.getAbbr(),tasks.getName(),tasks.getCoin(),tasks.getStatus().getNumber()));
                                activity.setDialyList(dayTasks);
                                activity.getDailyTask(new DayTask(res.getUserCoin(), res.getUserRank(), res.getConLogin()));
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void taskRewardDoing(final String taskId){
        AchieveReward.SetDailyTaskRewardReq  req = AchieveReward.SetDailyTaskRewardReq.newBuilder().setUserId(MyApp.getInstance().getUserId()).setTaskId(taskId).build();

        TiangongClient.instance().send("chronos", "set_daily_task_reward", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final AchieveReward.SetDailyTaskRewardResponse res = AchieveReward.SetDailyTaskRewardResponse.parseFrom(buffer);
                    LogUtil.d("reward : %s", res.toString());
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (res.getErrorno() == 0) {
                                activity.getDailyTask(new DayTask(res.getUserCoin(), res.getUserRank(), res.getConLogin()));
                                getDailyTaskList();
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
