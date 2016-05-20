// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.traincamp.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AwardWebView$$ViewBinder<T extends com.zhicai.byteera.activity.traincamp.view.AwardWebView> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427676, "field 'webView'");
    target.webView = finder.castView(view, 2131427676, "field 'webView'");
  }

  @Override public void unbind(T target) {
    target.webView = null;
  }
}
