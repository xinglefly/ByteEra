package com.zhicai.byteera.activity.traincamp.component;

import com.zhicai.byteera.ActivityScope;
import com.zhicai.byteera.AppComponent;
import com.zhicai.byteera.activity.traincamp.DailyTaskActivity;
import com.zhicai.byteera.activity.traincamp.module.DayTaskModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class,modules = DayTaskModule.class)
public interface DayTaskComponent {

    DailyTaskActivity injectActivity(DailyTaskActivity activity);

}
