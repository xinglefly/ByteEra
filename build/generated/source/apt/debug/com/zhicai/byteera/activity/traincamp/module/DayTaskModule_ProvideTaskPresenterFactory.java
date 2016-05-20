package com.zhicai.byteera.activity.traincamp.module;

import com.zhicai.byteera.activity.traincamp.presenter.DayTaskPresenter;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DayTaskModule_ProvideTaskPresenterFactory implements Factory<DayTaskPresenter> {
  private final DayTaskModule module;

  public DayTaskModule_ProvideTaskPresenterFactory(DayTaskModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public DayTaskPresenter get() {  
    DayTaskPresenter provided = module.provideTaskPresenter();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<DayTaskPresenter> create(DayTaskModule module) {  
    return new DayTaskModule_ProvideTaskPresenterFactory(module);
  }
}

