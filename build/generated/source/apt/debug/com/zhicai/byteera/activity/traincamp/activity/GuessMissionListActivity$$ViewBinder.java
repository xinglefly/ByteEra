// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.traincamp.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GuessMissionListActivity$$ViewBinder<T extends com.zhicai.byteera.activity.traincamp.activity.GuessMissionListActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427655, "field 'back' and method 'clickListener'");
    target.back = finder.castView(view, 2131427655, "field 'back'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427837, "field 'question'");
    target.question = finder.castView(view, 2131427837, "field 'question'");
    view = finder.findRequiredView(source, 2131427838, "field 'rel_guesssec'");
    target.rel_guesssec = finder.castView(view, 2131427838, "field 'rel_guesssec'");
    view = finder.findRequiredView(source, 2131427839, "field 'tv_coincount'");
    target.tv_coincount = finder.castView(view, 2131427839, "field 'tv_coincount'");
    view = finder.findRequiredView(source, 2131427610, "field 'gv_guess_mission'");
    target.gv_guess_mission = finder.castView(view, 2131427610, "field 'gv_guess_mission'");
  }

  @Override public void unbind(T target) {
    target.back = null;
    target.question = null;
    target.rel_guesssec = null;
    target.tv_coincount = null;
    target.gv_guess_mission = null;
  }
}
