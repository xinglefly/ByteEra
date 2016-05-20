// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.traincamp;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TrainCampFragment$$ViewBinder<T extends com.zhicai.byteera.activity.traincamp.TrainCampFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131428233, "field 'mWealthTask'");
    target.mWealthTask = finder.castView(view, 2131428233, "field 'mWealthTask'");
    view = finder.findRequiredView(source, 2131428234, "field 'mGussFinancial'");
    target.mGussFinancial = finder.castView(view, 2131428234, "field 'mGussFinancial'");
    view = finder.findRequiredView(source, 2131428235, "field 'mGussChange'");
    target.mGussChange = finder.castView(view, 2131428235, "field 'mGussChange'");
    view = finder.findRequiredView(source, 2131428236, "field 'mBuyMoney'");
    target.mBuyMoney = finder.castView(view, 2131428236, "field 'mBuyMoney'");
  }

  @Override public void unbind(T target) {
    target.mWealthTask = null;
    target.mGussFinancial = null;
    target.mGussChange = null;
    target.mBuyMoney = null;
  }
}
