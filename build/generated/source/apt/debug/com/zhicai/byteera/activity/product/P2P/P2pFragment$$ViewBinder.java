// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.product.P2P;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class P2pFragment$$ViewBinder<T extends com.zhicai.byteera.activity.product.P2P.P2pFragment> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2131428106, "field 'mDrawerRight'");
    target.mDrawerRight = finder.castView(view, 2131428106, "field 'mDrawerRight'");
    view = finder.findRequiredView(source, 2131428107, "field 'allView'");
    target.allView = finder.castView(view, 2131428107, "field 'allView'");
    view = finder.findRequiredView(source, 2131428105, "field 'companyView'");
    target.companyView = finder.castView(view, 2131428105, "field 'companyView'");
  }

  @Override public void unbind(T target) {
    target.mListView = null;
    target.mSwipeLayout = null;
    target.mDrawerRight = null;
    target.allView = null;
    target.companyView = null;
  }
}
