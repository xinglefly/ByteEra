// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AddtionFocusOrganizationFragment$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.AddtionFocusOrganizationFragment> implements ViewBinder<T> {
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
          target.onItemCilck(p1, p2);
        }
      });
    view = finder.findRequiredView(source, 2131427668, "field 'tvP2P' and method 'onClick'");
    target.tvP2P = finder.castView(view, 2131427668, "field 'tvP2P'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427670, "field 'tvBank' and method 'onClick'");
    target.tvBank = finder.castView(view, 2131427670, "field 'tvBank'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427669, "field 'tvZhongchou' and method 'onClick'");
    target.tvZhongchou = finder.castView(view, 2131427669, "field 'tvZhongchou'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mListView = null;
    target.tvP2P = null;
    target.tvBank = null;
    target.tvZhongchou = null;
  }
}
