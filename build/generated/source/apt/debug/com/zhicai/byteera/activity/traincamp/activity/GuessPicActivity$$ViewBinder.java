// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.traincamp.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GuessPicActivity$$ViewBinder<T extends com.zhicai.byteera.activity.traincamp.activity.GuessPicActivity> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2131427836, "field 'rel_guessfirst'");
    target.rel_guessfirst = finder.castView(view, 2131427836, "field 'rel_guessfirst'");
    view = finder.findRequiredView(source, 2131427589, "field 'rel_composite'");
    target.rel_composite = finder.castView(view, 2131427589, "field 'rel_composite'");
    view = finder.findRequiredView(source, 2131427590, "field 'tv_composite'");
    target.tv_composite = finder.castView(view, 2131427590, "field 'tv_composite'");
    view = finder.findRequiredView(source, 2131427591, "field 'gv_composite'");
    target.gv_composite = finder.castView(view, 2131427591, "field 'gv_composite'");
    view = finder.findRequiredView(source, 2131427580, "field 'rel_primary'");
    target.rel_primary = finder.castView(view, 2131427580, "field 'rel_primary'");
    view = finder.findRequiredView(source, 2131427581, "field 'tv_primary'");
    target.tv_primary = finder.castView(view, 2131427581, "field 'tv_primary'");
    view = finder.findRequiredView(source, 2131427582, "field 'gv_primary'");
    target.gv_primary = finder.castView(view, 2131427582, "field 'gv_primary'");
    view = finder.findRequiredView(source, 2131427583, "field 'rel_middle'");
    target.rel_middle = finder.castView(view, 2131427583, "field 'rel_middle'");
    view = finder.findRequiredView(source, 2131427584, "field 'tv_middle'");
    target.tv_middle = finder.castView(view, 2131427584, "field 'tv_middle'");
    view = finder.findRequiredView(source, 2131427585, "field 'gv_middle'");
    target.gv_middle = finder.castView(view, 2131427585, "field 'gv_middle'");
    view = finder.findRequiredView(source, 2131427586, "field 'rel_high'");
    target.rel_high = finder.castView(view, 2131427586, "field 'rel_high'");
    view = finder.findRequiredView(source, 2131427587, "field 'tv_high'");
    target.tv_high = finder.castView(view, 2131427587, "field 'tv_high'");
    view = finder.findRequiredView(source, 2131427588, "field 'gv_high'");
    target.gv_high = finder.castView(view, 2131427588, "field 'gv_high'");
  }

  @Override public void unbind(T target) {
    target.back = null;
    target.question = null;
    target.rel_guessfirst = null;
    target.rel_composite = null;
    target.tv_composite = null;
    target.gv_composite = null;
    target.rel_primary = null;
    target.tv_primary = null;
    target.gv_primary = null;
    target.rel_middle = null;
    target.tv_middle = null;
    target.gv_middle = null;
    target.rel_high = null;
    target.tv_high = null;
    target.gv_high = null;
  }
}
