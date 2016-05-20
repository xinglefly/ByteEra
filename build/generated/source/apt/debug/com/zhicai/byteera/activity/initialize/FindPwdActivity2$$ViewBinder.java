// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.initialize;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FindPwdActivity2$$ViewBinder<T extends com.zhicai.byteera.activity.initialize.FindPwdActivity2> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427557, "field 'etPwd'");
    target.etPwd = finder.castView(view, 2131427557, "field 'etPwd'");
    view = finder.findRequiredView(source, 2131427558, "field 'etPwd2'");
    target.etPwd2 = finder.castView(view, 2131427558, "field 'etPwd2'");
    view = finder.findRequiredView(source, 2131427551, "field 'mTitleBar'");
    target.mTitleBar = finder.castView(view, 2131427551, "field 'mTitleBar'");
    view = finder.findRequiredView(source, 2131427559, "method 'onclickView'");
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
    target.etPwd = null;
    target.etPwd2 = null;
    target.mTitleBar = null;
  }
}
