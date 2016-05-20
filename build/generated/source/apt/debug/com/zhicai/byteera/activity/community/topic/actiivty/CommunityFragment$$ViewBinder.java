// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.topic.actiivty;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CommunityFragment$$ViewBinder<T extends com.zhicai.byteera.activity.community.topic.actiivty.CommunityFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427735, "field 'img_contact' and method 'clickListener'");
    target.img_contact = finder.castView(view, 2131427735, "field 'img_contact'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427731, "field 'line'");
    target.line = view;
    view = finder.findRequiredView(source, 2131427732, "field 'rlTitle'");
    target.rlTitle = finder.castView(view, 2131427732, "field 'rlTitle'");
    view = finder.findRequiredView(source, 2131427653, "field 'topHead'");
    target.topHead = view;
    view = finder.findRequiredView(source, 2131427736, "field 'img_cursor'");
    target.img_cursor = finder.castView(view, 2131427736, "field 'img_cursor'");
    view = finder.findRequiredView(source, 2131427737, "field 'vp_knowPager', method 'pageSelected', and method 'pageScrolled'");
    target.vp_knowPager = finder.castView(view, 2131427737, "field 'vp_knowPager'");
    ((android.support.v4.view.ViewPager) view).setOnPageChangeListener(
      new android.support.v4.view.ViewPager.OnPageChangeListener() {
        @Override public void onPageSelected(
          int p0
        ) {
          target.pageSelected(p0);
        }
        @Override public void onPageScrolled(
          int p0,
          float p1,
          int p2
        ) {
          target.pageScrolled(p0, p1, p2);
        }
        @Override public void onPageScrollStateChanged(
          int p0
        ) {
          
        }
      });
    view = finder.findRequiredView(source, 2131427733, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427734, "method 'clickListener'");
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
    target.img_contact = null;
    target.line = null;
    target.rlTitle = null;
    target.topHead = null;
    target.img_cursor = null;
    target.vp_knowPager = null;
  }
}
