// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.knowwealth.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class KnowWealthDetailActivity$$ViewBinder<T extends com.zhicai.byteera.activity.knowwealth.view.KnowWealthDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427573, "field 'listView' and method 'onItemLongClick'");
    target.listView = finder.castView(view, 2131427573, "field 'listView'");
    ((android.widget.AdapterView<?>) view).setOnItemLongClickListener(
      new android.widget.AdapterView.OnItemLongClickListener() {
        @Override public boolean onItemLongClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          return target.onItemLongClick(p2);
        }
      });
    view = finder.findRequiredView(source, 2131427623, "field 'loadingPage'");
    target.loadingPage = finder.castView(view, 2131427623, "field 'loadingPage'");
    view = finder.findRequiredView(source, 2131427624, "field 'bottonLine'");
    target.bottonLine = finder.castView(view, 2131427624, "field 'bottonLine'");
    view = finder.findRequiredView(source, 2131427625, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener();
        }
      });
  }

  @Override public void unbind(T target) {
    target.listView = null;
    target.loadingPage = null;
    target.bottonLine = null;
  }
}
