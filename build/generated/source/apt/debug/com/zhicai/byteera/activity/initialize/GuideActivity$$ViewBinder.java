// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.initialize;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GuideActivity$$ViewBinder<T extends com.zhicai.byteera.activity.initialize.GuideActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427617, "field 'viewpager' and method 'onPageSelected'");
    target.viewpager = finder.castView(view, 2131427617, "field 'viewpager'");
    ((android.support.v4.view.ViewPager) view).setOnPageChangeListener(
      new android.support.v4.view.ViewPager.OnPageChangeListener() {
        @Override public void onPageSelected(
          int p0
        ) {
          target.onPageSelected(p0);
        }
        @Override public void onPageScrolled(
          int p0,
          float p1,
          int p2
        ) {
          
        }
        @Override public void onPageScrollStateChanged(
          int p0
        ) {
          
        }
      });
    view = finder.findRequiredView(source, 2131427622, "field 'tvJump' and method 'onClick'");
    target.tvJump = finder.castView(view, 2131427622, "field 'tvJump'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick();
        }
      });
    target.dots = Finder.arrayOf(
        finder.<android.widget.ImageView>findRequiredView(source, 2131427619, "field 'dots'"),
        finder.<android.widget.ImageView>findRequiredView(source, 2131427620, "field 'dots'"),
        finder.<android.widget.ImageView>findRequiredView(source, 2131427621, "field 'dots'")
    );
  }

  @Override public void unbind(T target) {
    target.viewpager = null;
    target.tvJump = null;
    target.dots = null;
  }
}
