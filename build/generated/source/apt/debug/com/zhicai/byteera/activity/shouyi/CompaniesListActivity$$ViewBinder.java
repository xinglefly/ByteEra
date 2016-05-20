// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.shouyi;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CompaniesListActivity$$ViewBinder<T extends com.zhicai.byteera.activity.shouyi.CompaniesListActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427439, "field 'headTitle'");
    target.headTitle = finder.castView(view, 2131427439, "field 'headTitle'");
    view = finder.findRequiredView(source, 2131427573, "field 'mListView' and method 'OnItemClick'");
    target.mListView = finder.castView(view, 2131427573, "field 'mListView'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.OnItemClick(p0, p1, p2);
        }
      });
  }

  @Override public void unbind(T target) {
    target.headTitle = null;
    target.mListView = null;
  }
}
