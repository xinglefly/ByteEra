// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.myhome.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ModifyUserInfoActivity$$ViewBinder<T extends com.zhicai.byteera.activity.myhome.activity.ModifyUserInfoActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427460, "field 'meadView'");
    target.meadView = finder.castView(view, 2131427460, "field 'meadView'");
    view = finder.findRequiredView(source, 2131427403, "field 'icon'");
    target.icon = finder.castView(view, 2131427403, "field 'icon'");
    view = finder.findRequiredView(source, 2131427987, "field 'tvNickName'");
    target.tvNickName = finder.castView(view, 2131427987, "field 'tvNickName'");
    view = finder.findRequiredView(source, 2131427990, "field 'tvSex'");
    target.tvSex = finder.castView(view, 2131427990, "field 'tvSex'");
    view = finder.findRequiredView(source, 2131427993, "field 'tvCity'");
    target.tvCity = finder.castView(view, 2131427993, "field 'tvCity'");
    view = finder.findRequiredView(source, 2131427996, "field 'tvBirthday'");
    target.tvBirthday = finder.castView(view, 2131427996, "field 'tvBirthday'");
    view = finder.findRequiredView(source, 2131427999, "field 'tvCard'");
    target.tvCard = finder.castView(view, 2131427999, "field 'tvCard'");
    view = finder.findRequiredView(source, 2131427859, "field 'll'");
    target.ll = finder.castView(view, 2131427859, "field 'll'");
    view = finder.findRequiredView(source, 2131427984, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427986, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427989, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427992, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427995, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428001, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427998, "method 'clickListener'");
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
    target.meadView = null;
    target.icon = null;
    target.tvNickName = null;
    target.tvSex = null;
    target.tvCity = null;
    target.tvBirthday = null;
    target.tvCard = null;
    target.ll = null;
  }
}
