// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CoinStoreActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord.CoinStoreActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427510, "field 'buttonView'");
    target.buttonView = finder.castView(view, 2131427510, "field 'buttonView'");
    view = finder.findRequiredView(source, 2131427439, "field 'mTitle'");
    target.mTitle = finder.castView(view, 2131427439, "field 'mTitle'");
    view = finder.findRequiredView(source, 2131427509, "field 'tv_coin'");
    target.tv_coin = finder.castView(view, 2131427509, "field 'tv_coin'");
  }

  @Override public void unbind(T target) {
    target.buttonView = null;
    target.mTitle = null;
    target.tv_coin = null;
  }
}
