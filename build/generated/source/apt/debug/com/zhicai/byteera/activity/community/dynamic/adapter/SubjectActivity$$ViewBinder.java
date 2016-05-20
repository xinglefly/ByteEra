// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SubjectActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.adapter.SubjectActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131427671, "field 'moreListView' and method 'onItemClickListener'");
    target.moreListView = finder.castView(view, 2131427671, "field 'moreListView'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClickListener(p2);
        }
      });
    view = finder.findRequiredView(source, 2131427800, "field 'mSwipeLayout'");
    target.mSwipeLayout = finder.castView(view, 2131427800, "field 'mSwipeLayout'");
  }

  @Override public void unbind(T target) {
    target.mHeadView = null;
    target.moreListView = null;
    target.mSwipeLayout = null;
  }
}
