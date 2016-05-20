// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.knowwealth.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class KnowWealthDetailCommentActivity2$$ViewBinder<T extends com.zhicai.byteera.activity.knowwealth.view.KnowWealthDetailCommentActivity2> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427706, "field 'etComment'");
    target.etComment = finder.castView(view, 2131427706, "field 'etComment'");
    view = finder.findRequiredView(source, 2131427573, "field 'mListView'");
    target.mListView = finder.castView(view, 2131427573, "field 'mListView'");
    view = finder.findRequiredView(source, 2131427786, "field 'tvEmptyComment'");
    target.tvEmptyComment = finder.castView(view, 2131427786, "field 'tvEmptyComment'");
    view = finder.findRequiredView(source, 2131427934, "field 'rlComment'");
    target.rlComment = finder.castView(view, 2131427934, "field 'rlComment'");
    view = finder.findRequiredView(source, 2131427935, "field 'tvFinish'");
    target.tvFinish = finder.castView(view, 2131427935, "field 'tvFinish'");
    view = finder.findRequiredView(source, 2131427653, "field 'topHead'");
    target.topHead = view;
    view = finder.findRequiredView(source, 2131427512, "field 'rl_container'");
    target.rl_container = finder.castView(view, 2131427512, "field 'rl_container'");
    view = finder.findRequiredView(source, 2131427625, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427933, "method 'clickListener'");
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
    target.etComment = null;
    target.mListView = null;
    target.tvEmptyComment = null;
    target.rlComment = null;
    target.tvFinish = null;
    target.topHead = null;
    target.rl_container = null;
  }
}
