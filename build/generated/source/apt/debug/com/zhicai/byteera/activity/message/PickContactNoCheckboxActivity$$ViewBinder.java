// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PickContactNoCheckboxActivity$$ViewBinder<T extends com.zhicai.byteera.activity.message.PickContactNoCheckboxActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427504, "field 'listView'");
    target.listView = finder.castView(view, 2131427504, "field 'listView'");
    view = finder.findRequiredView(source, 2131427577, "field 'sidebar'");
    target.sidebar = finder.castView(view, 2131427577, "field 'sidebar'");
  }

  @Override public void unbind(T target) {
    target.listView = null;
    target.sidebar = null;
  }
}
