// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GroupHomeActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.GroupHomeActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427573, "field 'mListView'");
    target.mListView = finder.castView(view, 2131427573, "field 'mListView'");
    view = finder.findRequiredView(source, 2131427574, "field 'mtitleView'");
    target.mtitleView = finder.castView(view, 2131427574, "field 'mtitleView'");
    view = finder.findRequiredView(source, 2131427672, "field 'mAvatar'");
    target.mAvatar = finder.castView(view, 2131427672, "field 'mAvatar'");
    view = finder.findRequiredView(source, 2131427511, "field 'tvDes'");
    target.tvDes = finder.castView(view, 2131427511, "field 'tvDes'");
    view = finder.findRequiredView(source, 2131427823, "field 'tvFansNum' and method 'clickListener'");
    target.tvFansNum = finder.castView(view, 2131427823, "field 'tvFansNum'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427825, "field 'tvFocus' and method 'clickListener'");
    target.tvFocus = finder.castView(view, 2131427825, "field 'tvFocus'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427826, "field 'ivRight' and method 'clickListener'");
    target.ivRight = finder.castView(view, 2131427826, "field 'ivRight'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427575, "field 'rlDianPin' and method 'clickListener'");
    target.rlDianPin = finder.castView(view, 2131427575, "field 'rlDianPin'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427572, "field 'scrollGroup'");
    target.scrollGroup = finder.castView(view, 2131427572, "field 'scrollGroup'");
  }

  @Override public void unbind(T target) {
    target.mListView = null;
    target.mtitleView = null;
    target.mAvatar = null;
    target.tvDes = null;
    target.tvFansNum = null;
    target.tvFocus = null;
    target.ivRight = null;
    target.rlDianPin = null;
    target.scrollGroup = null;
  }
}
