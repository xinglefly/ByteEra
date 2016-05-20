// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.shouyi;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BidFragment$$ViewBinder<T extends com.zhicai.byteera.activity.shouyi.BidFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427687, "field 'tvOpen' and method 'clickListener'");
    target.tvOpen = finder.castView(view, 2131427687, "field 'tvOpen'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427691, "field 'switchOpen'");
    target.switchOpen = finder.castView(view, 2131427691, "field 'switchOpen'");
    view = finder.findRequiredView(source, 2131427690, "field 'tvSwitchShow'");
    target.tvSwitchShow = finder.castView(view, 2131427690, "field 'tvSwitchShow'");
    view = finder.findRequiredView(source, 2131427573, "field 'mListView' and method 'onItemClick'");
    target.mListView = finder.castView(view, 2131427573, "field 'mListView'");
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
    view = finder.findRequiredView(source, 2131427692, "field 'rl_remind_openbg'");
    target.rl_remind_openbg = finder.castView(view, 2131427692, "field 'rl_remind_openbg'");
    view = finder.findRequiredView(source, 2131427694, "field 'rlNoData'");
    target.rlNoData = finder.castView(view, 2131427694, "field 'rlNoData'");
    view = finder.findRequiredView(source, 2131427693, "method 'clickListener'");
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
    target.tvOpen = null;
    target.switchOpen = null;
    target.tvSwitchShow = null;
    target.mListView = null;
    target.rl_remind_openbg = null;
    target.rlNoData = null;
  }
}
