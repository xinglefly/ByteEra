// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.shouyi;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class InComeConditionActivity$$ViewBinder<T extends com.zhicai.byteera.activity.shouyi.InComeConditionActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427574, "field 'mTitle'");
    target.mTitle = finder.castView(view, 2131427574, "field 'mTitle'");
    view = finder.findRequiredView(source, 2131427874, "field 'mBarCycle'");
    target.mBarCycle = finder.castView(view, 2131427874, "field 'mBarCycle'");
    view = finder.findRequiredView(source, 2131427877, "field 'mBarRate'");
    target.mBarRate = finder.castView(view, 2131427877, "field 'mBarRate'");
    view = finder.findRequiredView(source, 2131427670, "field 'tvBank' and method 'clickListener'");
    target.tvBank = finder.castView(view, 2131427670, "field 'tvBank'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427668, "field 'tvP2p' and method 'clickListener'");
    target.tvP2p = finder.castView(view, 2131427668, "field 'tvP2p'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427875, "field 'tvCnt1'");
    target.tvCnt1 = finder.castView(view, 2131427875, "field 'tvCnt1'");
    view = finder.findRequiredView(source, 2131427878, "field 'tvCnt3'");
    target.tvCnt3 = finder.castView(view, 2131427878, "field 'tvCnt3'");
    view = finder.findRequiredView(source, 2131427882, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427761, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mTitle = null;
    target.mBarCycle = null;
    target.mBarRate = null;
    target.tvBank = null;
    target.tvP2p = null;
    target.tvCnt1 = null;
    target.tvCnt3 = null;
  }
}
