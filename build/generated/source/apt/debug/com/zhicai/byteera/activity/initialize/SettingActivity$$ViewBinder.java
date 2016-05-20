// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.initialize;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SettingActivity$$ViewBinder<T extends com.zhicai.byteera.activity.initialize.SettingActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131428190, "field 'tvLoginOut'");
    target.tvLoginOut = finder.castView(view, 2131428190, "field 'tvLoginOut'");
    view = finder.findRequiredView(source, 2131427439, "field 'headView'");
    target.headView = finder.castView(view, 2131427439, "field 'headView'");
    view = finder.findRequiredView(source, 2131428189, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick();
        }
      });
  }

  @Override public void unbind(T target) {
    target.tvLoginOut = null;
    target.headView = null;
  }
}
