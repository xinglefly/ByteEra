// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SelectAddressActivity$$ViewBinder<T extends com.zhicai.byteera.activity.message.SelectAddressActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131427573, "field 'mListView'");
    target.mListView = finder.castView(view, 2131427573, "field 'mListView'");
    view = finder.findRequiredView(source, 2131427623, "field 'mLoadingPage'");
    target.mLoadingPage = finder.castView(view, 2131427623, "field 'mLoadingPage'");
    view = finder.findRequiredView(source, 2131427477, "field 'rlBottom'");
    target.rlBottom = finder.castView(view, 2131427477, "field 'rlBottom'");
    view = finder.findRequiredView(source, 2131427723, "field 'mRightLetter'");
    target.mRightLetter = finder.castView(view, 2131427723, "field 'mRightLetter'");
    view = finder.findRequiredView(source, 2131427721, "field 'tvMidLetter'");
    target.tvMidLetter = finder.castView(view, 2131427721, "field 'tvMidLetter'");
    view = finder.findRequiredView(source, 2131427810, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427935, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428183, "method 'clickListener'");
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
    target.mHeadView = null;
    target.mListView = null;
    target.mLoadingPage = null;
    target.rlBottom = null;
    target.mRightLetter = null;
    target.tvMidLetter = null;
  }
}
