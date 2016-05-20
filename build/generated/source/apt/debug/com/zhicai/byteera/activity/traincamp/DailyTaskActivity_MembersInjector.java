package com.zhicai.byteera.activity.traincamp;

import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.traincamp.adapter.DayTaskAdapter;
import com.zhicai.byteera.activity.traincamp.presenter.DayTaskPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DailyTaskActivity_MembersInjector implements MembersInjector<DailyTaskActivity> {
  private final MembersInjector<BaseActivity> supertypeInjector;
  private final Provider<DayTaskPresenter> presenterProvider;
  private final Provider<DayTaskAdapter> dayTaskAdapterProvider;

  public DailyTaskActivity_MembersInjector(MembersInjector<BaseActivity> supertypeInjector, Provider<DayTaskPresenter> presenterProvider, Provider<DayTaskAdapter> dayTaskAdapterProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert presenterProvider != null;
    this.presenterProvider = presenterProvider;
    assert dayTaskAdapterProvider != null;
    this.dayTaskAdapterProvider = dayTaskAdapterProvider;
  }

  @Override
  public void injectMembers(DailyTaskActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.presenter = presenterProvider.get();
    instance.dayTaskAdapter = dayTaskAdapterProvider.get();
  }

  public static MembersInjector<DailyTaskActivity> create(MembersInjector<BaseActivity> supertypeInjector, Provider<DayTaskPresenter> presenterProvider, Provider<DayTaskAdapter> dayTaskAdapterProvider) {  
      return new DailyTaskActivity_MembersInjector(supertypeInjector, presenterProvider, dayTaskAdapterProvider);
  }
}

