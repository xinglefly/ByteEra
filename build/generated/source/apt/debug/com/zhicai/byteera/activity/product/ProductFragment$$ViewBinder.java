// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.product;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ProductFragment$$ViewBinder<T extends com.zhicai.byteera.activity.product.ProductFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131428103, "field 'mPager' and method 'onPageSelected'");
    target.mPager = finder.castView(view, 2131428103, "field 'mPager'");
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
    view = finder.findRequiredView(source, 2131428102, "field 'qiehuan' and method 'clickListener'");
    target.qiehuan = finder.castView(view, 2131428102, "field 'qiehuan'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener();
        }
      });
    view = finder.findRequiredView(source, 2131427653, "field 'topHead'");
    target.topHead = view;
    view = finder.findRequiredView(source, 2131427663, "field 'mIndicator'");
    target.mIndicator = finder.castView(view, 2131427663, "field 'mIndicator'");
  }

  @Override public void unbind(T target) {
    target.mPager = null;
    target.qiehuan = null;
    target.topHead = null;
    target.mIndicator = null;
  }
}
