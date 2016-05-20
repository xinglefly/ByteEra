// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdditionActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.AdditionActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131427663, "field 'mIndicator'");
    target.mIndicator = finder.castView(view, 2131427663, "field 'mIndicator'");
    view = finder.findRequiredView(source, 2131427664, "field 'mViewPager'");
    target.mViewPager = finder.castView(view, 2131427664, "field 'mViewPager'");
  }

  @Override public void unbind(T target) {
    target.mHeadView = null;
    target.mIndicator = null;
    target.mViewPager = null;
  }
}
