// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class OrganizationHomeActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.OrganizationHomeActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427574, "field 'mTitle'");
    target.mTitle = finder.castView(view, 2131427574, "field 'mTitle'");
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131427573, "field 'mListView' and method 'onItemClickListener'");
    target.mListView = finder.castView(view, 2131427573, "field 'mListView'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClickListener(p0, p2);
        }
      });
    view = finder.findRequiredView(source, 2131428052, "field 'iv_organization_right'");
    target.iv_organization_right = finder.castView(view, 2131428052, "field 'iv_organization_right'");
    view = finder.findRequiredView(source, 2131428050, "field 'tvMoneyNum'");
    target.tvMoneyNum = finder.castView(view, 2131428050, "field 'tvMoneyNum'");
    view = finder.findRequiredView(source, 2131428051, "field 'tvInverstorNum'");
    target.tvInverstorNum = finder.castView(view, 2131428051, "field 'tvInverstorNum'");
    view = finder.findRequiredView(source, 2131427575, "field 'rlDianPin'");
    target.rlDianPin = finder.castView(view, 2131427575, "field 'rlDianPin'");
  }

  @Override public void unbind(T target) {
    target.mTitle = null;
    target.mHeadView = null;
    target.mListView = null;
    target.iv_organization_right = null;
    target.tvMoneyNum = null;
    target.tvInverstorNum = null;
    target.rlDianPin = null;
  }
}
