package com.zhicai.byteera.activity.traincamp.module;

import android.app.Activity;

import com.zhicai.byteera.ActivityScope;
import com.zhicai.byteera.activity.traincamp.DailyTaskActivity;
import com.zhicai.byteera.activity.traincamp.adapter.DayTaskAdapter;
import com.zhicai.byteera.activity.traincamp.presenter.DayTaskPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class DayTaskModule {

    DailyTaskActivity activity;

    public DayTaskModule(DailyTaskActivity activity) {
        this.activity = activity;
    }

    @Provides @ActivityScope
    Activity provideActivity(){
        return activity;
    }


    @Provides @ActivityScope
    DayTaskPresenter provideTaskPresenter(){
        return new DayTaskPresenter(activity);
    }


    @Provides @ActivityScope
    DayTaskAdapter provideTaskAdapter(){
        return new DayTaskAdapter(activity);
    }


}
