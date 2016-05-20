// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.myhome.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyCaiBiActivity$$ViewBinder<T extends com.zhicai.byteera.activity.myhome.activity.MyCaiBiActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131428005, "field 'rb_income'");
    target.rb_income = finder.castView(view, 2131428005, "field 'rb_income'");
    view = finder.findRequiredView(source, 2131428006, "field 'rb_outcome'");
    target.rb_outcome = finder.castView(view, 2131428006, "field 'rb_outcome'");
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
  }

  @Override public void unbind(T target) {
    target.rb_income = null;
    target.rb_outcome = null;
    target.mHeadView = null;
  }
}
