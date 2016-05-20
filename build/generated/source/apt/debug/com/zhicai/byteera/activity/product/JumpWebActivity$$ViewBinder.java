// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.product;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class JumpWebActivity$$ViewBinder<T extends com.zhicai.byteera.activity.product.JumpWebActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427439, "field 'mHeadTitle'");
    target.mHeadTitle = finder.castView(view, 2131427439, "field 'mHeadTitle'");
    view = finder.findRequiredView(source, 2131427925, "field 'mWebView'");
    target.mWebView = finder.castView(view, 2131427925, "field 'mWebView'");
  }

  @Override public void unbind(T target) {
    target.mHeadTitle = null;
    target.mWebView = null;
  }
}
