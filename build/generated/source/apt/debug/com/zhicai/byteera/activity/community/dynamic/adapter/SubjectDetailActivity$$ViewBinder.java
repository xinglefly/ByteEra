// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SubjectDetailActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.adapter.SubjectDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131427671, "field 'moreListView'");
    target.moreListView = finder.castView(view, 2131427671, "field 'moreListView'");
    view = finder.findRequiredView(source, 2131427800, "field 'mSwipeLayout'");
    target.mSwipeLayout = finder.castView(view, 2131427800, "field 'mSwipeLayout'");
  }

  @Override public void unbind(T target) {
    target.mHeadView = null;
    target.moreListView = null;
    target.mSwipeLayout = null;
  }
}
