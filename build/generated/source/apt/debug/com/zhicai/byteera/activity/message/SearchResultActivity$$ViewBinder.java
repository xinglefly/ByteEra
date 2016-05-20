// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SearchResultActivity$$ViewBinder<T extends com.zhicai.byteera.activity.message.SearchResultActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427645, "field 'back'");
    target.back = finder.castView(view, 2131427645, "field 'back'");
    view = finder.findRequiredView(source, 2131427646, "field 'content'");
    target.content = finder.castView(view, 2131427646, "field 'content'");
    view = finder.findRequiredView(source, 2131427648, "field 'listResult'");
    target.listResult = finder.castView(view, 2131427648, "field 'listResult'");
    view = finder.findRequiredView(source, 2131427647, "field 'searchLine'");
    target.searchLine = finder.castView(view, 2131427647, "field 'searchLine'");
  }

  @Override public void unbind(T target) {
    target.back = null;
    target.content = null;
    target.listResult = null;
    target.searchLine = null;
  }
}
