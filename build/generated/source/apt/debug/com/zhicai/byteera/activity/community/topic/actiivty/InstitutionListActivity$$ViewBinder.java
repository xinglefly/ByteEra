// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.topic.actiivty;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class InstitutionListActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.topic.actiivty.InstitutionListActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427664, "field 'mViewPager'");
    target.mViewPager = finder.castView(view, 2131427664, "field 'mViewPager'");
    view = finder.findRequiredView(source, 2131427663, "field 'mIndicator'");
    target.mIndicator = finder.castView(view, 2131427663, "field 'mIndicator'");
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
  }

  @Override public void unbind(T target) {
    target.mViewPager = null;
    target.mIndicator = null;
    target.mHeadView = null;
  }
}
