// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.knowwealth.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WebViewActivity$$ViewBinder<T extends com.zhicai.byteera.activity.knowwealth.view.WebViewActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427925, "field 'mWebView'");
    target.mWebView = finder.castView(view, 2131427925, "field 'mWebView'");
  }

  @Override public void unbind(T target) {
    target.mWebView = null;
  }
}
