// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GroupHomeSecondActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.GroupHomeSecondActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427827, "field 'rlRroupSecond'");
    target.rlRroupSecond = finder.castView(view, 2131427827, "field 'rlRroupSecond'");
    view = finder.findRequiredView(source, 2131427574, "field 'mTitle'");
    target.mTitle = finder.castView(view, 2131427574, "field 'mTitle'");
    view = finder.findRequiredView(source, 2131427666, "field 'tvName'");
    target.tvName = finder.castView(view, 2131427666, "field 'tvName'");
    view = finder.findRequiredView(source, 2131427828, "field 'tvMember'");
    target.tvMember = finder.castView(view, 2131427828, "field 'tvMember'");
    view = finder.findRequiredView(source, 2131427829, "field 'tvDes'");
    target.tvDes = finder.castView(view, 2131427829, "field 'tvDes'");
  }

  @Override public void unbind(T target) {
    target.rlRroupSecond = null;
    target.mTitle = null;
    target.tvName = null;
    target.tvMember = null;
    target.tvDes = null;
  }
}
