package com.zhicai.byteera.activity.traincamp.module;

import com.zhicai.byteera.activity.traincamp.adapter.DayTaskAdapter;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DayTaskModule_ProvideTaskAdapterFactory implements Factory<DayTaskAdapter> {
  private final DayTaskModule module;

  public DayTaskModule_ProvideTaskAdapterFactory(DayTaskModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public DayTaskAdapter get() {  
    DayTaskAdapter provided = module.provideTaskAdapter();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<DayTaskAdapter> create(DayTaskModule module) {  
    return new DayTaskModule_ProvideTaskAdapterFactory(module);
  }
}

