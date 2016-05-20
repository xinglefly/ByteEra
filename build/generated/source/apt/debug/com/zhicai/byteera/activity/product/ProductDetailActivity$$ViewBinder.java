// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.product;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ProductDetailActivity$$ViewBinder<T extends com.zhicai.byteera.activity.product.ProductDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427439, "field 'headTitle'");
    target.headTitle = finder.castView(view, 2131427439, "field 'headTitle'");
    view = finder.findRequiredView(source, 2131427902, "field 'tvProTitle'");
    target.tvProTitle = finder.castView(view, 2131427902, "field 'tvProTitle'");
    view = finder.findRequiredView(source, 2131427696, "field 'tvIncome'");
    target.tvIncome = finder.castView(view, 2131427696, "field 'tvIncome'");
    view = finder.findRequiredView(source, 2131427904, "field 'tvIncomeDay'");
    target.tvIncomeDay = finder.castView(view, 2131427904, "field 'tvIncomeDay'");
    view = finder.findRequiredView(source, 2131427905, "field 'tvInvestDay'");
    target.tvInvestDay = finder.castView(view, 2131427905, "field 'tvInvestDay'");
    view = finder.findRequiredView(source, 2131427906, "field 'tvFixedIncome'");
    target.tvFixedIncome = finder.castView(view, 2131427906, "field 'tvFixedIncome'");
    view = finder.findRequiredView(source, 2131427907, "field 'circleProgress'");
    target.circleProgress = finder.castView(view, 2131427907, "field 'circleProgress'");
    view = finder.findRequiredView(source, 2131427908, "field 'tvSurplus'");
    target.tvSurplus = finder.castView(view, 2131427908, "field 'tvSurplus'");
    view = finder.findRequiredView(source, 2131427883, "field 'tvRaisemoney'");
    target.tvRaisemoney = finder.castView(view, 2131427883, "field 'tvRaisemoney'");
    view = finder.findRequiredView(source, 2131427884, "field 'tvRepayment'");
    target.tvRepayment = finder.castView(view, 2131427884, "field 'tvRepayment'");
    view = finder.findRequiredView(source, 2131427885, "field 'tvCost'");
    target.tvCost = finder.castView(view, 2131427885, "field 'tvCost'");
    view = finder.findRequiredView(source, 2131427886, "field 'tvPublishDay'");
    target.tvPublishDay = finder.castView(view, 2131427886, "field 'tvPublishDay'");
    view = finder.findRequiredView(source, 2131427887, "field 'tvEndDay'");
    target.tvEndDay = finder.castView(view, 2131427887, "field 'tvEndDay'");
    view = finder.findRequiredView(source, 2131427853, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427854, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427855, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427856, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427857, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.headTitle = null;
    target.tvProTitle = null;
    target.tvIncome = null;
    target.tvIncomeDay = null;
    target.tvInvestDay = null;
    target.tvFixedIncome = null;
    target.circleProgress = null;
    target.tvSurplus = null;
    target.tvRaisemoney = null;
    target.tvRepayment = null;
    target.tvCost = null;
    target.tvPublishDay = null;
    target.tvEndDay = null;
  }
}
