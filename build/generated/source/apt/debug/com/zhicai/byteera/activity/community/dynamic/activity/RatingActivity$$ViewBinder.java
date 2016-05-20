// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RatingActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.RatingActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131428120, "field 'mRatingBar1'");
    target.mRatingBar1 = finder.castView(view, 2131428120, "field 'mRatingBar1'");
    view = finder.findRequiredView(source, 2131428122, "field 'mRatingBar2'");
    target.mRatingBar2 = finder.castView(view, 2131428122, "field 'mRatingBar2'");
    view = finder.findRequiredView(source, 2131428124, "field 'mRatingBar3'");
    target.mRatingBar3 = finder.castView(view, 2131428124, "field 'mRatingBar3'");
    view = finder.findRequiredView(source, 2131427460, "field 'mheadView'");
    target.mheadView = finder.castView(view, 2131427460, "field 'mheadView'");
    view = finder.findRequiredView(source, 2131428125, "method 'click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.click(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mRatingBar1 = null;
    target.mRatingBar2 = null;
    target.mRatingBar3 = null;
    target.mheadView = null;
  }
}
