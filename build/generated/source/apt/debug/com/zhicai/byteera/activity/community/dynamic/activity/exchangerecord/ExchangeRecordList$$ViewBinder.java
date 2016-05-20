// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ExchangeRecordList$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord.ExchangeRecordList> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427439, "field 'mTitle'");
    target.mTitle = finder.castView(view, 2131427439, "field 'mTitle'");
    view = finder.findRequiredView(source, 2131427513, "field 'mListView'");
    target.mListView = finder.castView(view, 2131427513, "field 'mListView'");
  }

  @Override public void unbind(T target) {
    target.mTitle = null;
    target.mListView = null;
  }
}
