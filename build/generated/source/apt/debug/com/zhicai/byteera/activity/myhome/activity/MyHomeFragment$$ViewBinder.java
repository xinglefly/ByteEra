// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.myhome.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyHomeFragment$$ViewBinder<T extends com.zhicai.byteera.activity.myhome.activity.MyHomeFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427404, "field 'mTitleView'");
    target.mTitleView = finder.castView(view, 2131427404, "field 'mTitleView'");
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131428012, "field 'mFriends' and method 'clickListener'");
    target.mFriends = finder.castView(view, 2131428012, "field 'mFriends'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428011, "field 'mDongtai' and method 'clickListener'");
    target.mDongtai = finder.castView(view, 2131428011, "field 'mDongtai'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428010, "field 'mSouCang' and method 'clickListener'");
    target.mSouCang = finder.castView(view, 2131428010, "field 'mSouCang'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428013, "field 'mCoinShop' and method 'clickListener'");
    target.mCoinShop = finder.castView(view, 2131428013, "field 'mCoinShop'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428014, "field 'mCoinTask' and method 'clickListener'");
    target.mCoinTask = finder.castView(view, 2131428014, "field 'mCoinTask'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428015, "field 'mInviteCode' and method 'clickListener'");
    target.mInviteCode = finder.castView(view, 2131428015, "field 'mInviteCode'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428016, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428017, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428018, "method 'clickListener'");
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
    target.mTitleView = null;
    target.mHeadView = null;
    target.mFriends = null;
    target.mDongtai = null;
    target.mSouCang = null;
    target.mCoinShop = null;
    target.mCoinTask = null;
    target.mInviteCode = null;
  }
}
