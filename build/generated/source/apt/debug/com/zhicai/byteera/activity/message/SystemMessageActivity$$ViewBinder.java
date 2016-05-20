// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SystemMessageActivity$$ViewBinder<T extends com.zhicai.byteera.activity.message.SystemMessageActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427655, "field 'back'");
    target.back = finder.castView(view, 2131427655, "field 'back'");
    view = finder.findRequiredView(source, 2131427835, "field 'titleName'");
    target.titleName = finder.castView(view, 2131427835, "field 'titleName'");
    view = finder.findRequiredView(source, 2131427843, "field 'settings'");
    target.settings = finder.castView(view, 2131427843, "field 'settings'");
    view = finder.findRequiredView(source, 2131427652, "field 'listView'");
    target.listView = finder.castView(view, 2131427652, "field 'listView'");
  }

  @Override public void unbind(T target) {
    target.back = null;
    target.titleName = null;
    target.settings = null;
    target.listView = null;
  }
}
