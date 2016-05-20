// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UserFansActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.UserFansActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
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
          target.onItemClickListener(p2);
        }
      });
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131428007, "field 'mRlEmpty'");
    target.mRlEmpty = finder.castView(view, 2131428007, "field 'mRlEmpty'");
  }

  @Override public void unbind(T target) {
    target.mListView = null;
    target.mHeadView = null;
    target.mRlEmpty = null;
  }
}
