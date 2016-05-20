// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.knowwealth.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PageFragment$$ViewBinder<T extends com.zhicai.byteera.activity.knowwealth.view.PageFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427573, "field 'mListView' and method 'onItemClick'");
    target.mListView = finder.castView(view, 2131427573, "field 'mListView'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClick(p0, p2);
        }
      });
    view = finder.findRequiredView(source, 2131427800, "field 'mSwipeLayout'");
    target.mSwipeLayout = finder.castView(view, 2131427800, "field 'mSwipeLayout'");
    view = finder.findRequiredView(source, 2131427623, "field 'loadingPage'");
    target.loadingPage = finder.castView(view, 2131427623, "field 'loadingPage'");
  }

  @Override public void unbind(T target) {
    target.mListView = null;
    target.mSwipeLayout = null;
    target.loadingPage = null;
  }
}
