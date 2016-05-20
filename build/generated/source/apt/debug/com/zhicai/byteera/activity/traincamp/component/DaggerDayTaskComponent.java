package com.zhicai.byteera.activity.traincamp.component;

import com.zhicai.byteera.AppComponent;
import com.zhicai.byteera.activity.traincamp.DailyTaskActivity;
import com.zhicai.byteera.activity.traincamp.DailyTaskActivity_MembersInjector;
import com.zhicai.byteera.activity.traincamp.adapter.DayTaskAdapter;
import com.zhicai.byteera.activity.traincamp.module.DayTaskModule;
import com.zhicai.byteera.activity.traincamp.module.DayTaskModule_ProvideTaskAdapterFactory;
import com.zhicai.byteera.activity.traincamp.module.DayTaskModule_ProvideTaskPresenterFactory;
import com.zhicai.byteera.activity.traincamp.presenter.DayTaskPresenter;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerDayTaskComponent implements DayTaskComponent {
  private Provider<DayTaskPresenter> provideTaskPresenterProvider;
  private Provider<DayTaskAdapter> provideTaskAdapterProvider;
  private MembersInjector<DailyTaskActivity> dailyTaskActivityMembersInjector;

  private DaggerDayTaskComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  private void initialize(final Builder builder) {  
    this.provideTaskPresenterProvider = ScopedProvider.create(DayTaskModule_ProvideTaskPresenterFactory.create(builder.dayTaskModule));
    this.provideTaskAdapterProvider = ScopedProvider.create(DayTaskModule_ProvideTaskAdapterFactory.create(builder.dayTaskModule));
    this.dailyTaskActivityMembersInjector = DailyTaskActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), provideTaskPresenterProvider, provideTaskAdapterProvider);
  }

  @Override
  public DailyTaskActivity injectActivity(DailyTaskActivity activity) {  
    dailyTaskActivityMembersInjector.injectMembers(activity);
    return activity;
  }

  public static final class Builder {
    private DayTaskModule dayTaskModule;
    private AppComponent appComponent;
  
    private Builder() {  
    }
  
    public DayTaskComponent build() {  
      if (dayTaskModule == null) {
        throw new IllegalStateException("dayTaskModule must be set");
      }
      if (appComponent == null) {
        throw new IllegalStateException("appComponent must be set");
      }
      return new DaggerDayTaskComponent(this);
    }
  
    public Builder dayTaskModule(DayTaskModule dayTaskModule) {  
      if (dayTaskModule == null) {
        throw new NullPointerException("dayTaskModule");
      }
      this.dayTaskModule = dayTaskModule;
      return this;
    }
  
    public Builder appComponent(AppComponent appComponent) {  
      if (appComponent == null) {
        throw new NullPointerException("appComponent");
      }
      this.appComponent = appComponent;
      return this;
    }
  }
}

