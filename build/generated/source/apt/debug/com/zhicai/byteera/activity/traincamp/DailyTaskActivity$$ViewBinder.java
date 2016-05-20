// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.traincamp;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DailyTaskActivity$$ViewBinder<T extends com.zhicai.byteera.activity.traincamp.DailyTaskActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427439, "field 'headTitle'");
    target.headTitle = finder.castView(view, 2131427439, "field 'headTitle'");
    view = finder.findRequiredView(source, 2131427758, "field 'lv_daytask' and method 'onItemClick'");
    target.lv_daytask = finder.castView(view, 2131427758, "field 'lv_daytask'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClick(p0, p2);
        }
      });
    view = finder.findRequiredView(source, 2131427509, "field 'tvCoin'");
    target.tvCoin = finder.castView(view, 2131427509, "field 'tvCoin'");
    view = finder.findRequiredView(source, 2131427756, "field 'tvRanking'");
    target.tvRanking = finder.castView(view, 2131427756, "field 'tvRanking'");
    view = finder.findRequiredView(source, 2131427757, "field 'tvLogingDays'");
    target.tvLogingDays = finder.castView(view, 2131427757, "field 'tvLogingDays'");
  }

  @Override public void unbind(T target) {
    target.headTitle = null;
    target.lv_daytask = null;
    target.tvCoin = null;
    target.tvRanking = null;
    target.tvLogingDays = null;
  }
}
