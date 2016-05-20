// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.myhome.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FocusOrganizationFragment$$ViewBinder<T extends com.zhicai.byteera.activity.myhome.activity.FocusOrganizationFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427671, "field 'mListView' and method 'onItemCilck'");
    target.mListView = finder.castView(view, 2131427671, "field 'mListView'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemCilck(p2);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mListView = null;
  }
}
