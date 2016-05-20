// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.initialize;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AboutUSActivity$$ViewBinder<T extends com.zhicai.byteera.activity.initialize.AboutUSActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427439, "field 'headView'");
    target.headView = finder.castView(view, 2131427439, "field 'headView'");
    view = finder.findRequiredView(source, 2131427442, "field 'tvVersion'");
    target.tvVersion = finder.castView(view, 2131427442, "field 'tvVersion'");
  }

  @Override public void unbind(T target) {
    target.headView = null;
    target.tvVersion = null;
  }
}
