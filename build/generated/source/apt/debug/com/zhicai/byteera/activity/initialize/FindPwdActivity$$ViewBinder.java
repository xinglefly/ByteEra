// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.initialize;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FindPwdActivity$$ViewBinder<T extends com.zhicai.byteera.activity.initialize.FindPwdActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427552, "field 'etUserName'");
    target.etUserName = finder.castView(view, 2131427552, "field 'etUserName'");
    view = finder.findRequiredView(source, 2131427554, "field 'etVerifyNum'");
    target.etVerifyNum = finder.castView(view, 2131427554, "field 'etVerifyNum'");
    view = finder.findRequiredView(source, 2131427555, "field 'getVerify' and method 'onclickView'");
    target.getVerify = finder.castView(view, 2131427555, "field 'getVerify'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onclickView(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427551, "field 'mTitleBar'");
    target.mTitleBar = finder.castView(view, 2131427551, "field 'mTitleBar'");
    view = finder.findRequiredView(source, 2131427556, "method 'onclickView'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onclickView(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.etUserName = null;
    target.etVerifyNum = null;
    target.getVerify = null;
    target.mTitleBar = null;
  }
}
