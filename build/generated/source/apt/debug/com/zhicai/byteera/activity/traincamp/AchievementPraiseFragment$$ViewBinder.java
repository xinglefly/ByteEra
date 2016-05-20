// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.traincamp;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AchievementPraiseFragment$$ViewBinder<T extends com.zhicai.byteera.activity.traincamp.AchievementPraiseFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427758, "field 'lv_daytask' and method 'itemClickListener'");
    target.lv_daytask = finder.castView(view, 2131427758, "field 'lv_daytask'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.itemClickListener(p1, p2);
        }
      });
  }

  @Override public void unbind(T target) {
    target.lv_daytask = null;
  }
}
