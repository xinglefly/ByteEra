// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.zhicai.byteera.activity.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131428213, "field 'tabIncome' and method 'clickListener'");
    target.tabIncome = finder.castView(view, 2131428213, "field 'tabIncome'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428214, "field 'tabProduct' and method 'clickListener'");
    target.tabProduct = finder.castView(view, 2131428214, "field 'tabProduct'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428215, "field 'tabFind' and method 'clickListener'");
    target.tabFind = finder.castView(view, 2131428215, "field 'tabFind'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428216, "field 'tabMine' and method 'clickListener'");
    target.tabMine = finder.castView(view, 2131428216, "field 'tabMine'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427638, "field 'mllButton'");
    target.mllButton = finder.castView(view, 2131427638, "field 'mllButton'");
    view = finder.findRequiredView(source, 2131428137, "field 'unreadLabel'");
    target.unreadLabel = finder.castView(view, 2131428137, "field 'unreadLabel'");
  }

  @Override public void unbind(T target) {
    target.tabIncome = null;
    target.tabProduct = null;
    target.tabFind = null;
    target.tabMine = null;
    target.mllButton = null;
    target.unreadLabel = null;
  }
}
