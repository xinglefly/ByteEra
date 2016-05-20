// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.shouyi;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LiCaiShouYiFragment$$ViewBinder<T extends com.zhicai.byteera.activity.shouyi.LiCaiShouYiFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427510, "field 'buttonView'");
    target.buttonView = finder.castView(view, 2131427510, "field 'buttonView'");
  }

  @Override public void unbind(T target) {
    target.buttonView = null;
  }
}
