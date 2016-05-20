package com.zhicai.byteera.activity.traincamp.presenter;

import com.zhicai.byteera.activity.bean.DayTask;

import java.util.List;


public interface DayTaskView {

    void setDialyList(List<DayTask> taskList);

    void getDailyTask(DayTask dayTask);
}
