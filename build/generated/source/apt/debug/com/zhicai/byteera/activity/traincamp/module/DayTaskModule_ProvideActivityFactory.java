package com.zhicai.byteera.activity.traincamp.module;

import android.app.Activity;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DayTaskModule_ProvideActivityFactory implements Factory<Activity> {
  private final DayTaskModule module;

  public DayTaskModule_ProvideActivityFactory(DayTaskModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public Activity get() {  
    Activity provided = module.provideActivity();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Activity> create(DayTaskModule module) {  
    return new DayTaskModule_ProvideActivityFactory(module);
  }
}

