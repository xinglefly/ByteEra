// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.knowwealth.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FinancingFragment$$ViewBinder<T extends com.zhicai.byteera.activity.knowwealth.view.FinancingFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427812, "field 'mIndicator'");
    target.mIndicator = finder.castView(view, 2131427812, "field 'mIndicator'");
    view = finder.findRequiredView(source, 2131427813, "field 'mPager'");
    target.mPager = finder.castView(view, 2131427813, "field 'mPager'");
  }

  @Override public void unbind(T target) {
    target.mIndicator = null;
    target.mPager = null;
  }
}
