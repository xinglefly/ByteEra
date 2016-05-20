// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.product.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class IncomeCalCulateDialog$$ViewBinder<T extends com.zhicai.byteera.activity.product.view.IncomeCalCulateDialog> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427862, "field 'etMoneyVal' and method 'onTextChange'");
    target.etMoneyVal = finder.castView(view, 2131427862, "field 'etMoneyVal'");
    ((android.widget.TextView) view).addTextChangedListener(
      new android.text.TextWatcher() {
        @Override public void onTextChanged(
          java.lang.CharSequence p0,
          int p1,
          int p2,
          int p3
        ) {
          
        }
        @Override public void beforeTextChanged(
          java.lang.CharSequence p0,
          int p1,
          int p2,
          int p3
        ) {
          
        }
        @Override public void afterTextChanged(
          android.text.Editable p0
        ) {
          target.onTextChange();
        }
      });
    view = finder.findRequiredView(source, 2131427865, "field 'tvLimitVal'");
    target.tvLimitVal = finder.castView(view, 2131427865, "field 'tvLimitVal'");
    view = finder.findRequiredView(source, 2131427867, "field 'tvExpectVal'");
    target.tvExpectVal = finder.castView(view, 2131427867, "field 'tvExpectVal'");
    view = finder.findRequiredView(source, 2131427869, "field 'tvCurrentVal'");
    target.tvCurrentVal = finder.castView(view, 2131427869, "field 'tvCurrentVal'");
    view = finder.findRequiredView(source, 2131427871, "field 'tvYueBao'");
    target.tvYueBao = finder.castView(view, 2131427871, "field 'tvYueBao'");
    view = finder.findRequiredView(source, 2131427873, "field 'tvDeferralVal'");
    target.tvDeferralVal = finder.castView(view, 2131427873, "field 'tvDeferralVal'");
    view = finder.findRequiredView(source, 2131427655, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick();
        }
      });
  }

  @Override public void unbind(T target) {
    target.etMoneyVal = null;
    target.tvLimitVal = null;
    target.tvExpectVal = null;
    target.tvCurrentVal = null;
    target.tvYueBao = null;
    target.tvDeferralVal = null;
  }
}
