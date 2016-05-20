// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.initialize;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UserProtocolActivity$$ViewBinder<T extends com.zhicai.byteera.activity.initialize.UserProtocolActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131427925, "field 'mWebView'");
    target.mWebView = finder.castView(view, 2131427925, "field 'mWebView'");
    view = finder.findRequiredView(source, 2131427623, "field 'mLoadingPage'");
    target.mLoadingPage = finder.castView(view, 2131427623, "field 'mLoadingPage'");
  }

  @Override public void unbind(T target) {
    target.mHeadView = null;
    target.mWebView = null;
    target.mLoadingPage = null;
  }
}
