// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.traincamp.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WealthTaskActivity$$ViewBinder<T extends com.zhicai.byteera.activity.traincamp.activity.WealthTaskActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427510, "field 'buttonView'");
    target.buttonView = finder.castView(view, 2131427510, "field 'buttonView'");
    view = finder.findRequiredView(source, 2131427439, "field 'mTitle'");
    target.mTitle = finder.castView(view, 2131427439, "field 'mTitle'");
    view = finder.findRequiredView(source, 2131427839, "field 'tv_coincount'");
    target.tv_coincount = finder.castView(view, 2131427839, "field 'tv_coincount'");
    view = finder.findRequiredView(source, 2131427756, "field 'tv_ranking'");
    target.tv_ranking = finder.castView(view, 2131427756, "field 'tv_ranking'");
    view = finder.findRequiredView(source, 2131427757, "field 'tv_logindays'");
    target.tv_logindays = finder.castView(view, 2131427757, "field 'tv_logindays'");
  }

  @Override public void unbind(T target) {
    target.buttonView = null;
    target.mTitle = null;
    target.tv_coincount = null;
    target.tv_ranking = null;
    target.tv_logindays = null;
  }
}
