// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.group;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SelelctGroupActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.group.SelelctGroupActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131428186, "field 'firstGridView'");
    target.firstGridView = finder.castView(view, 2131428186, "field 'firstGridView'");
    view = finder.findRequiredView(source, 2131428187, "field 'secGridView'");
    target.secGridView = finder.castView(view, 2131428187, "field 'secGridView'");
    view = finder.findRequiredView(source, 2131428188, "field 'thirdGridView'");
    target.thirdGridView = finder.castView(view, 2131428188, "field 'thirdGridView'");
  }

  @Override public void unbind(T target) {
    target.firstGridView = null;
    target.secGridView = null;
    target.thirdGridView = null;
  }
}
